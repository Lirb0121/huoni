# huoni
 我的测试 项目dfg
 
 CREATE TABLE `user_info` (
   `id` int(30) NOT NULL AUTO_INCREMENT,
   `age` int(3) DEFAULT NULL,
   `username` varchar(30) CHARACTER SET utf8 NOT NULL COMMENT '用户名',
   `address` varchar(255) CHARACTER SET utf8 DEFAULT NULL COMMENT '地址',
   PRIMARY KEY (`id`)
 ) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

CREATE TABLE `company_member` (
  `id` int(30) NOT NULL AUTO_INCREMENT,
  `member` varchar(32) CHARACTER SET utf8 DEFAULT NULL COMMENT '成员',
  `pinyin` varchar(32) CHARACTER SET utf8 DEFAULT NULL COMMENT '拼音',
  `phone` varchar(11) CHARACTER SET utf8 DEFAULT NULL COMMENT '手机号',
  `create_time` varchar(10) CHARACTER SET utf8 DEFAULT NULL COMMENT '入住日期',
  `plat_account` varchar(32) CHARACTER SET utf8 DEFAULT NULL COMMENT '平台账户',
  `identity` varchar(32) CHARACTER SET utf8 DEFAULT NULL COMMENT '身份',
  `shiro` varchar(255) CHARACTER SET utf8 DEFAULT NULL COMMENT '权限',
  `is_incumbency` varchar(32) CHARACTER SET utf8 DEFAULT NULL COMMENT '是否在职',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;


