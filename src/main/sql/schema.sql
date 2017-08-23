-- 数据库初始换脚本

-- 创建数据库
CREATE DATABASE seckill;

-- 使用数据库
use seckill;
-- 创建秒杀库存表
CREATE TABLE seckill(
`seckillId` bigint(20) NOT NULL AUTO_INCREMENT comment '商品库存',
`name` varchar(120) NOT NULL  comment '商品名称',
`number` int NOT NULL comment '库存数量',
  `create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP comment '创建时间',
`start_time` TIMESTAMP NOT NULL comment '秒杀开始时间',
`end_time` TIMESTAMP NOT NULL comment '秒杀结束时间',
PRIMARY KEY (seckillId),
key idx_start_time(start_time),
key idx_end_time(end_time),
key idx_create_time(create_time)
)ENGINE=InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8 comment='秒杀库存系统';

-- 初始化数据
insert INTO seckill(name,number,start_time,end_time)
VALUES ('1000元秒杀iPhone7',100,'2015-11-01 00:00:00','2015-11-02 00:00:00'),
     ('500元秒杀iPhone7',200,'2015-11-01 00:00:00','2015-11-02 00:00:00'),
    ('300元秒杀小米6',300,'2015-11-01 00:00:00','2015-11-02 00:00:00'),
    ('2元秒杀小米',400,'2015-11-01 00:00:00','2015-11-02 00:00:00');


-- 秒杀成功明细表
-- 用户登录认证相关信息(简化为手机号)

CREATE TABLE success_killed(
  `seckill_id` BIGINT NOT NULL COMMENT '秒杀商品ID',
  `user_phone` BIGINT NOT NULL COMMENT '用户手机号',
  `state` TINYINT NOT NULL DEFAULT -1 COMMENT '状态标识:-1:无效 0:成功 1:已付款 2:已发货',
  `create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP  COMMENT '创建时间',
  PRIMARY KEY(seckill_id,user_phone),/*联合主键*/
  KEY idx_create_time(create_time)
)ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='秒杀成功明细表';


-- 链接数据台

