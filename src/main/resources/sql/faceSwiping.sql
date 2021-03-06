USE faceSwiping;

 -- 用户表
CREATE TABLE IF NOT EXISTS `USER` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `PASSWORD` varchar(255) DEFAULT NULL COMMENT '密码',
  `USERNAME` varchar(50) DEFAULT NULL COMMENT '用户名为邮箱',
  `ACCOUNT_EXPIRED` TINYINT(1) NOT NULL COMMENT '账户是否到期',
  `ACCOUNT_LOCKED` TINYINT(1) NOT NULL COMMENT '账户是否被锁定',
  `CREDENTIALS_EXPIRED` TINYINT(1) NOT NULL COMMENT '证书是否到期',
  `ACCOUNT_ENABLED` TINYINT(1) NOT NULL COMMENT '账户是否激活',
  `SECRET` TINYINT(1) NOT NULL DEFAULT 0 COMMENT '用户刷脸服务是否开启,0代表开启,1代表不开启',
   PRIMARY KEY (`ID`),
  UNIQUE KEY `USERNAME` (`USERNAME`)
) ENGINE=InnoDB  DEFAULT CHARSET=UTF8 AUTO_INCREMENT=14 ;
-- 用户权限表
CREATE TABLE `USER_AUTHORITY`(
  `USER_ID` BIGINT(20) NOT NULL ,
  `AUTHORITY` VARCHAR(20) NOT NULL ,
  PRIMARY KEY (USER_ID,AUTHORITY)
)ENGINE=InnoDB  DEFAULT CHARSET=UTF8;


-- 用户详细信息表
CREATE TABLE `USER_DETAIL_INFO`(
  `USER_ID` BIGINT(20) NOT NULL ,
  `NICK_NAME`  VARCHAR(50) DEFAULT '',
  `HEAD_IMAGE_KEY` VARCHAR(256) DEFAULT NULL,
  `GE_TUI_CLIENT_ID` VARCHAR(50) DEFAULT NULL ,
  PRIMARY KEY(`USER_ID`)
)ENGINE=InnoDB  DEFAULT CHARSET=UTF8;


-- 用户人脸训练集表
CREATE TABLE `USER_FACE_IMAGES`(
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `USER_ID` BIGINT(20) NOT NULL ,
  `IMAGE_KEY` VARCHAR(256) NOT NULL COMMENT '图片的key',
  `FACE_ID` VARCHAR(50) NOT NULL ,
  `CREATE_TIME`     TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`ID`)
)ENGINE=InnoDB  DEFAULT CHARSET=UTF8;

-- 用户刷脸上传图片表
CREATE TABLE `USER_SWIPING_IMAGES`(
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `USER_ID` BIGINT(20) NOT NULL ,
  `IMAGE_KEY` VARCHAR(256) NOT NULL COMMENT '图片的key',
  `FACE_ID` VARCHAR(50) NOT NULL ,
  `CREATE_TIME`     TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`ID`)
)ENGINE=InnoDB  DEFAULT CHARSET=UTF8;

-- 用户好友关系表
CREATE TABLE `USER_RELATION`(
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `USER_ID` BIGINT(20) NOT NULL COMMENT '用户ID',
  `TARGET_ID` BIGINT(20) NOT NULL COMMENT '好友ID',
  `ACCEPTED` TINYINT(1) NOT NULL COMMENT '是否被接受,0表示未接受,1表示已接受',
  `UPDATE_TIME` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`ID`)
)ENGINE=InnoDB  DEFAULT CHARSET=UTF8;

