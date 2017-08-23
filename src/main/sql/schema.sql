-- ���ݿ��ʼ���ű�

-- �������ݿ�
CREATE DATABASE seckill;

-- ʹ�����ݿ�
use seckill;
-- ������ɱ����
CREATE TABLE seckill(
`seckillId` bigint(20) NOT NULL AUTO_INCREMENT comment '��Ʒ���',
`name` varchar(120) NOT NULL  comment '��Ʒ����',
`number` int NOT NULL comment '�������',
  `create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP comment '����ʱ��',
`start_time` TIMESTAMP NOT NULL comment '��ɱ��ʼʱ��',
`end_time` TIMESTAMP NOT NULL comment '��ɱ����ʱ��',
PRIMARY KEY (seckillId),
key idx_start_time(start_time),
key idx_end_time(end_time),
key idx_create_time(create_time)
)ENGINE=InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8 comment='��ɱ���ϵͳ';

-- ��ʼ������
insert INTO seckill(name,number,start_time,end_time)
VALUES ('1000Ԫ��ɱiPhone7',100,'2015-11-01 00:00:00','2015-11-02 00:00:00'),
     ('500Ԫ��ɱiPhone7',200,'2015-11-01 00:00:00','2015-11-02 00:00:00'),
    ('300Ԫ��ɱС��6',300,'2015-11-01 00:00:00','2015-11-02 00:00:00'),
    ('2Ԫ��ɱС��',400,'2015-11-01 00:00:00','2015-11-02 00:00:00');


-- ��ɱ�ɹ���ϸ��
-- �û���¼��֤�����Ϣ(��Ϊ�ֻ���)

CREATE TABLE success_killed(
  `seckill_id` BIGINT NOT NULL COMMENT '��ɱ��ƷID',
  `user_phone` BIGINT NOT NULL COMMENT '�û��ֻ���',
  `state` TINYINT NOT NULL DEFAULT -1 COMMENT '״̬��ʶ:-1:��Ч 0:�ɹ� 1:�Ѹ��� 2:�ѷ���',
  `create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP  COMMENT '����ʱ��',
  PRIMARY KEY(seckill_id,user_phone),/*��������*/
  KEY idx_create_time(create_time)
)ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='��ɱ�ɹ���ϸ��';


-- ��������̨

