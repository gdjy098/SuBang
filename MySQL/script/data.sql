insert into worker_t values(null,1,'李华','15500000001','第一个取衣员');
insert into city_t values(null,'沈阳');
insert into district_t values (null,'和平区',1);
insert into region_t values(null,'新世界花园',1,1);
insert into laundry_t values(null,'洗衣店1号','15500000002','辽宁省沈阳市和平区文化路3号巷11号','第一个洗衣店');
insert into user_t values(null,1,'o6_bmjrPTlm6_2sgVt7hMZOPfL2M',null,'小强',null,'15500000003',0,'image/photo/o6_bmjrPTlm6_2sgVt7hMZOPfL2M.jpg',1,null,null,null,null);
insert into addr_t values(null,1,'小明','15500000004','辽宁省沈阳市和平区文化路3号巷11号',1,1);
update user_t set addrid=1 where id=1;
insert into order_t values(null,'1506240010002',0,0,null,curdate(),9,'这是第一个订单',1,1,1,1);
insert into history_t values(null,0,now(),1);
insert into admin_t values(null,'admin','admin');
