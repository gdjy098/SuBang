package com.subang.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.subang.bean.OrderDetail;
import com.subang.domain.Addr;
import com.subang.domain.City;
import com.subang.domain.District;
import com.subang.domain.History;
import com.subang.domain.History.Operation;
import com.subang.domain.Info;
import com.subang.domain.Order;
import com.subang.domain.Order.State;
import com.subang.domain.Region;
import com.subang.domain.User;
import com.subang.domain.Worker;
import com.subang.util.Common;
import com.subang.util.SmsUtil;
import com.subang.util.SmsUtil.SmsType;
import com.subang.util.StratUtil;
import com.subang.util.WebConst;

@Service
public class FrontUserService extends CommUserService {

	/**
	 * 与用户相关的操作
	 */
	public User getUserByOpenid(String openid) {
		return userDao.findByOpenid(openid);
	}

	public void addUser(User userFront) {
		User userBack = getUserByOpenid(userFront.getOpenid());
		if (userBack == null) {
			userDao.save(userFront);
		} else {
			userBack.setValid(true);
			userBack.setNickname(userFront.getNickname());
			userBack.setSex(userFront.getSex());
			userBack.setCountry(userFront.getCountry());
			userBack.setProvince(userFront.getProvince());
			userBack.setCity(userFront.getCity());
			userDao.update(userBack);
		}
	}

	/**
	 * 与用户地址相关的操作
	 */
	public void addAddr(Addr addr) {
		addr.setValid(true);
		addrDao.save(addr);
		User user = userDao.get(addr.getUserid());
		if (user.getAddrid() == null) {
			user.setAddrid(addr.getId());
			userDao.update(user);
		}
	}

	public void deleteAddr(Integer addrid) {
		Addr addr = addrDao.get(addrid);
		User user = userDao.get(addr.getUserid());
		if (orderDao.findNumByAddrid(addrid) == 0) {
			addrDao.delete(addrid);
		} else {
			addr.setValid(false);
			addrDao.update(addr);
		}
		if (user.getAddrid() == addrid) {
			List<Addr> addrs = addrDao.findByUserid(user.getId());
			if (!addrs.isEmpty()) {
				user.setAddrid(addrs.get(0).getId());
				userDao.update(user);
			}
		}
	}

	/**
	 * 与订单相关的操作
	 */
	public List<Order> searchOrderByUseridAndState(Integer userid, int stateType) {
		List<Order> orders = new ArrayList<Order>();
		switch (stateType) {
		case WebConst.ORDER_STATE_UNDONE:
			orders.addAll(orderDao.findByUseridAndState(userid, State.accepted));
			orders.addAll(orderDao.findByUseridAndState(userid, State.fetched));
			break;
		case WebConst.ORDER_STATE_DONE:
			orders.addAll(orderDao.findByUseridAndState(userid, State.finished));
			orders.addAll(orderDao.findByUseridAndState(userid, State.canceled));
			break;
		}
		return orders;
	}

	public void addOrder(Order order) {
		order.setState(State.accepted);

		Addr addr = addrDao.get(order.getAddrid());
		Region region = regionDao.get(addr.getRegionid());
		Integer workerid = region.getWorkerid();
		if (workerid == null) {
			List<Worker> coreWorkers = workerDao.findByCore();
			workerid = coreWorkers.get(Common.random.nextInt(coreWorkers.size())).getId();
		}
		order.setOrderno(StratUtil.getOrderno(workerid));
		order.setWorkerid(workerid);	
		orderDao.save(order);

		order = orderDao.getByOrderno(order.getOrderno());

		History history = new History();
		history.setOperation(Operation.accept);
		history.setOrderid(order.getId());
		historyDao.save(history);

		User user = userDao.get(order.getUserid());
		user.setAddrid(order.getAddrid());
		userDao.update(user);

		Worker worker = workerDao.get(order.getWorkerid());
		OrderDetail orderDetail = orderDao.getOrderDetail(order.getId());
		SmsUtil.send(worker.getCellnum(), SmsType.accept, SmsUtil.toWorkerContent(orderDetail));
	}

	public boolean fetchOrder(Integer orderid) {
		Order order = orderDao.get(orderid);
		if (order.getStateEnum() == State.accepted) {
			order.setState(State.fetched);
			orderDao.update(order);

			History history = new History();
			history.setOperation(Operation.fetch);
			history.setOrderid(order.getId());
			historyDao.save(history);
			return true;
		}
		return false;
	}

	/**
	 * 与区域相关的操作
	 */
	public List<City> listCity() {
		return cityDao.findAllValid();
	}

	public List<District> listDistrictByCityid(Integer cityid) {
		return districtDao.findValidByCityid(cityid);
	}

	public List<Region> listRegionByDistrictid(Integer districtid) {
		return regionDao.findByDistrictid(districtid);
	}

	/**
	 * 与产品运营相关的操作
	 */
	public Info getInfo() {
		return infoDao.findALL().get(0);
	}
}
