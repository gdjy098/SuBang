drop view if exists area_v;
create view  area_v 
as ( select city_t.id `cityid`, city_t.name `cityname`, district_t.id `districtid`, district_t.name `districtname`, region_t.id `regionid`, region_t.name `regionname`, region_t.workerid `workerid` 
from city_t, district_t, region_t 
where city_t.id=district_t.cityid and district_t.id=region_t.districtid 
);

drop view if exists addrdetail_v;
create view addrdetail_v 
as ( select addr_t.*, area_v.cityname, area_v.districtname, area_v.regionname 
from addr_t, area_v
where addr_t.regionid=area_v.regionid
);

drop view if exists orderdetail_v;
create view orderdetail_v 
as ( select order_t.*, category_t.name `categoryname`, user_t.cellnum, payment_t.type `pay_type`, payment_t.money_ticket,
 addrdetail_v.name `addrname`, addrdetail_v.cellnum `addrcellnum`, addrdetail_v.cityname, addrdetail_v.districtname, addrdetail_v.regionname, addrdetail_v.detail `addrdetail`,  
 worker_t.name `workername`, worker_t.cellnum `workercellnum` 
from order_t, user_t, addrdetail_v, worker_t, category_t, payment_t 
where order_t.userid=user_t.id and order_t.addrid=addrdetail_v.id and order_t.workerid=worker_t.id and order_t.categoryid=category_t.id and payment_t.orderid=order_t.id
);

drop view if exists ticketdetail_v;
create view ticketdetail_v
as ( select ticket_t.id, category_t.name `categoryname`, ticket_type_t.name, ticket_type_t.icon, ticket_type_t.money, ticket_type_t.score, ticket_t.deadline, ticket_t.userid, ticket_t.ticket_typeid
from ticket_t,ticket_type_t,category_t 
where ticket_t.ticket_typeid=ticket_type_t.id and  ticket_type_t.categoryid=category_t.id
);


