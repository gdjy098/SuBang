package com.subang.controller.back;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.subang.bean.OrderDetail;
import com.subang.bean.PageState;
import com.subang.bean.SearchArg;
import com.subang.controller.BaseController;
import com.subang.domain.History;
import com.subang.domain.Laundry;
import com.subang.domain.Order;
import com.subang.exception.BackException;
import com.subang.util.Common;
import com.subang.util.WebConst;

@Controller
@RequestMapping("/back/order")
public class OrderController extends BaseController {

	private static final String INDEX_PAGE = WebConst.BACK_PREFIX+"/order/index";
	private static final String KEY_DATA = "orderDetails";

	@RequestMapping("/index")
	public ModelAndView index(HttpSession session, @RequestParam("type") int type) {
		ModelAndView view = new ModelAndView();

		if (type == WebConst.INDEX_BREAK) {
			savePageState(session, new SearchArg(WebConst.SEARCH_NULL, null));
		}
		PageState pageState = getPageState(session);

		List<OrderDetail> orderDetails = backUserService.searchOrder(pageState.getSearchArg());
		view.addObject(KEY_DATA, orderDetails);
		view.addObject(KEY_ERR_MSG, session.getAttribute(KEY_ERR_MSG));
		session.removeAttribute(KEY_ERR_MSG);
		view.addObject(KEY_INFO_MSG, session.getAttribute(KEY_INFO_MSG));
		session.removeAttribute(KEY_INFO_MSG);
		view.setViewName(INDEX_PAGE);
		return view;
	}

	@RequestMapping("/list")
	public ModelAndView list(HttpSession session) {
		ModelAndView view = new ModelAndView();
		SearchArg searchArg = new SearchArg(WebConst.SEARCH_ALL, null);
		savePageState(session, searchArg);
		List<OrderDetail> orderDetails = backUserService.searchOrder(searchArg);
		view.addObject(KEY_DATA, orderDetails);
		view.setViewName(INDEX_PAGE);
		return view;
	}

	@RequestMapping("/search")
	public ModelAndView search(HttpSession session, SearchArg searchArg) {
		ModelAndView view = new ModelAndView();
		savePageState(session, searchArg);
		List<OrderDetail> orderDetails = backUserService.searchOrder(searchArg);
		view.addObject(KEY_DATA, orderDetails);
		view.setViewName(INDEX_PAGE);
		return view;
	}

	@RequestMapping("/delete")
	public ModelAndView delete(HttpSession session, @RequestParam("orderids") String orderids) {
		ModelAndView view = new ModelAndView();
		boolean isException = false;
		try {
			backUserService.deleteOrders(Common.getIds(orderids));
		} catch (BackException e) {
			session.setAttribute(KEY_INFO_MSG, "删除失败。" + e.getMessage());
			isException = true;
		}
		if (!isException) {
			session.setAttribute(KEY_INFO_MSG, "删除成功。");
		}
		view.setViewName("redirect:"+ WebConst.BACK_PREFIX+"/order/index.html?type=1");
		return view;
	}

	@RequestMapping("/finish")
	public ModelAndView finish(HttpSession session, @RequestParam("orderids") String orderids) {
		ModelAndView view = new ModelAndView();
		boolean isException = false;
		try {
			backUserService.finishOrders(Common.getIds(orderids));
		} catch (BackException e) {
			session.setAttribute(KEY_INFO_MSG, "结束订单失败。" + e.getMessage());
			isException = true;
		}
		if (!isException) {
			session.setAttribute(KEY_INFO_MSG, "结束订单成功。");
		}
		view.setViewName("redirect:"+ WebConst.BACK_PREFIX+"/order/index.html?type=1");
		return view;
	}

	@RequestMapping("/cancel")
	public ModelAndView cancel(HttpSession session, @RequestParam("orderids") String orderids) {
		ModelAndView view = new ModelAndView();
		boolean isException = false;
		try {
			backUserService.cancelOrders(Common.getIds(orderids));
		} catch (BackException e) {
			session.setAttribute(KEY_INFO_MSG, "取消订单失败。" + e.getMessage());
			isException = true;
		}
		if (!isException) {
			session.setAttribute(KEY_INFO_MSG, "取消订单成功。");
		}
		view.setViewName("redirect:"+ WebConst.BACK_PREFIX+"/order/index.html?type=1");
		return view;
	}

	@RequestMapping("/showmodify")
	public ModelAndView showModify(@RequestParam("orderid") Integer orderid) {
		ModelAndView view = new ModelAndView();
		Order order = backUserService.getOrder(orderid);
		SearchArg searchArg = new SearchArg(WebConst.SEARCH_ALL, null);
		List<Laundry> laundrys = backAdminService.searchLaundry(searchArg);
		view.addObject("laundrys", laundrys);
		view.addObject("order", order);
		view.setViewName(WebConst.BACK_PREFIX+"/order/modify");
		return view;
	}

	@RequestMapping("/modify")
	public ModelAndView modify(@Valid Order order, BindingResult result) {
		ModelAndView view = new ModelAndView();
		if(order.getId()==null){
			view.addObject(KEY_INFO_MSG, "修改失败。发生错误。");
			view.addObject("order", order);
		}else if (!result.hasErrors()) {
			boolean isException = false;
			try {
				backUserService.modifyOrder(order);
			} catch (BackException e) {
				view.addObject(KEY_INFO_MSG, "修改失败。" + e.getMessage());
				view.addObject("order", order);
				isException = true;
			}
			if (!isException) {
				view.addObject(KEY_INFO_MSG, "修改成功。");
			}
		}
		SearchArg searchArg = new SearchArg(WebConst.SEARCH_ALL, null);
		List<Laundry> laundrys = backAdminService.searchLaundry(searchArg);
		view.addObject("laundrys", laundrys);
		view.setViewName(WebConst.BACK_PREFIX+"/order/modify");
		return view;
	}

	@RequestMapping("/history")
	public ModelAndView listHistory(Integer orderid) {
		ModelAndView view = new ModelAndView();
		List<History> historys = backUserService.listHistoryByOrderid(orderid);
		view.addObject("historys", historys);
		Order order = backUserService.getOrder(orderid);
		String desMsg = "订单号：" + order.getOrderno() + "。此订单的操作历史如下：";
		view.addObject(KEY_DES_MSG, desMsg);
		view.setViewName(WebConst.BACK_PREFIX+"/order/history");
		return view;
	}
}