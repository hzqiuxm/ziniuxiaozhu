/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50713
Source Host           : localhost:3306
Source Database       : znblog

Target Server Type    : MYSQL
Target Server Version : 50713
File Encoding         : 65001

Date: 2016-08-24 09:54:41
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for lessons
-- ----------------------------
DROP TABLE IF EXISTS `lessons`;
CREATE TABLE `lessons` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `lesson_name` varchar(100) DEFAULT NULL,
  `lesson_type` char(30) DEFAULT NULL,
  `lesson_des` varchar(1000) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `is_cycle` char(1) DEFAULT NULL COMMENT 'Y:可重复 N：不可重复',
  `state` char(2) DEFAULT NULL COMMENT '0可选 1不可选',
  `notes` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=86 DEFAULT CHARSET=utf8 COMMENT='存放所有课程信息';

-- ----------------------------
-- Table structure for lessons_plan
-- ----------------------------
DROP TABLE IF EXISTS `lessons_plan`;
CREATE TABLE `lessons_plan` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `lesson_name` varchar(100) DEFAULT NULL COMMENT '和课程表的名字对应，不是最后培训课程的名字。一般这个名字范围有些大',
  `lesson_title` varchar(255) DEFAULT NULL COMMENT '本次课程具体的名字，由讲师自己填写的',
  `lesson_des` varchar(1000) DEFAULT NULL COMMENT '讲师自己介绍本次课程主要内容，培训受众等',
  `lesson_time` date DEFAULT NULL,
  `lesson_teacher` varchar(50) DEFAULT NULL,
  `lesson_grade` char(1) DEFAULT NULL COMMENT '不及格，及格，中等，良好，优秀',
  `lesson_score` float DEFAULT NULL COMMENT '听众给讲师打的分数，总分10分，这里取平均分.去除一个最高分去除一个最低分，剩余求平均分',
  `lesson_place` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `state` char(2) DEFAULT NULL COMMENT '0：未开讲 1：已开讲',
  `notes` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=125 DEFAULT CHARSET=utf8 COMMENT='存放生成的具体课程计划';

-- ----------------------------
-- Table structure for lessons_type
-- ----------------------------
DROP TABLE IF EXISTS `lessons_type`;
CREATE TABLE `lessons_type` (
  `id` int(11) NOT NULL,
  `type_des` varchar(255) DEFAULT NULL,
  `state` char(2) DEFAULT NULL,
  `notes` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for lesson_eval
-- ----------------------------
DROP TABLE IF EXISTS `lesson_eval`;
CREATE TABLE `lesson_eval` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `lesson_plan_id` int(11) DEFAULT NULL COMMENT '用于关联记录,和课程计划表关联',
  `eval_des` varchar(5000) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `state` char(2) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='学员对课程的评价，匿名制度';

-- ----------------------------
-- Table structure for user_base
-- ----------------------------
DROP TABLE IF EXISTS `user_base`;
CREATE TABLE `user_base` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(20) DEFAULT NULL COMMENT '用户登录的账号名称',
  `user_pwd` varchar(20) DEFAULT NULL,
  `sex` char(1) DEFAULT NULL,
  `birthday` date DEFAULT NULL,
  `introduce` varchar(2000) DEFAULT NULL,
  `addr` varchar(255) DEFAULT NULL,
  `qq` char(12) DEFAULT NULL,
  `mail` varchar(50) DEFAULT NULL,
  `wx_id` varchar(50) DEFAULT NULL,
  `real_name` varchar(50) DEFAULT NULL,
  `choose_num` int(11) DEFAULT NULL COMMENT '剩余选课次数',
  `identity` varchar(50) DEFAULT NULL COMMENT '讲师身份类型，不同身份能选到课可能会有所区别',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `state` char(2) DEFAULT NULL,
  `notes` varchar(255) DEFAULT NULL,
  `noweekreport_times` int(11) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8 COMMENT='用户基本信息表';

-- ----------------------------
-- Table structure for zn_article
-- ----------------------------
DROP TABLE IF EXISTS `zn_article`;
CREATE TABLE `zn_article` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `Pcategory` int(10) unsigned DEFAULT '0' COMMENT '文章父类型，0表示没有父类型',
  `category` int(10) unsigned DEFAULT NULL COMMENT '文章类型',
  `imgUrl` varchar(50) DEFAULT NULL COMMENT '文章配图',
  `title` varchar(50) DEFAULT NULL COMMENT '文章标题',
  `secondTitle` varchar(50) DEFAULT NULL COMMENT '文章副标题',
  `summary` varchar(500) DEFAULT NULL COMMENT '文章摘要或导读',
  `content` text COMMENT '文章内容',
  `postTime` datetime DEFAULT NULL COMMENT '发布时间',
  `author` varchar(30) DEFAULT NULL COMMENT '作者',
  `commentCount` int(10) unsigned DEFAULT NULL COMMENT '评论次数',
  `readCount` int(10) unsigned DEFAULT NULL COMMENT '阅读次数',
  `smCount` int(10) DEFAULT NULL,
  `lastUpdateTime` datetime DEFAULT NULL COMMENT '最后一次更新时间',
  `accountId` int(10) DEFAULT NULL,
  `accountName` varchar(40) DEFAULT NULL,
  `lastAccountId` int(10) DEFAULT NULL,
  `lastAccountName` varchar(40) DEFAULT NULL,
  `relPersonId` int(8) DEFAULT NULL,
  `relPersonName` varchar(40) DEFAULT NULL,
  `relCompanyId` int(8) DEFAULT NULL,
  `relCompanyName` varchar(40) DEFAULT NULL,
  `notes` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for zn_comment
-- ----------------------------
DROP TABLE IF EXISTS `zn_comment`;
CREATE TABLE `zn_comment` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `content` varchar(500) DEFAULT NULL COMMENT '评论内容',
  `accountId` int(10) unsigned DEFAULT NULL COMMENT '评论者',
  `accountName` varchar(40) DEFAULT NULL,
  `postTime` datetime DEFAULT NULL COMMENT '评论发布时间',
  `articleId` int(10) DEFAULT NULL COMMENT '评论文章的ID',
  `forId` int(10) DEFAULT NULL COMMENT '针对哪个回复的ID',
  `notes` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for zn_discuss
-- ----------------------------
DROP TABLE IF EXISTS `zn_discuss`;
CREATE TABLE `zn_discuss` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `discuss_report_id` int(11) DEFAULT '0',
  `discuss_critic` varchar(20) DEFAULT '',
  `discuss_reply` varchar(20) DEFAULT '',
  `discuss_message` varchar(1000) DEFAULT '',
  `discuss_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=87 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for zn_judge
-- ----------------------------
DROP TABLE IF EXISTS `zn_judge`;
CREATE TABLE `zn_judge` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `judge_weekreports_id` int(11) DEFAULT '0',
  `judge_critic` varchar(20) DEFAULT '',
  `judge_reply` varchar(20) DEFAULT '',
  `judge_message` varchar(1000) DEFAULT '',
  `judge_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=90 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for zn_lessons
-- ----------------------------
DROP TABLE IF EXISTS `zn_lessons`;
CREATE TABLE `zn_lessons` (
  `id` int(11) NOT NULL DEFAULT '0',
  `lesson_name` varchar(100) DEFAULT NULL,
  `lesson_type` char(30) DEFAULT NULL,
  `lesson_des` varchar(1000) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `is_cycle` char(1) DEFAULT NULL COMMENT 'Y:可重复 N：不可重复',
  `state` char(2) DEFAULT NULL COMMENT '0可选 1不可选',
  `notes` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for zn_lessons_plan
-- ----------------------------
DROP TABLE IF EXISTS `zn_lessons_plan`;
CREATE TABLE `zn_lessons_plan` (
  `id` int(11) unsigned NOT NULL DEFAULT '0',
  `lesson_name` varchar(100) DEFAULT NULL COMMENT '和课程表的名字对应，不是最后培训课程的名字。一般这个名字范围有些大',
  `lesson_title` varchar(255) DEFAULT NULL COMMENT '本次课程具体的名字，由讲师自己填写的',
  `lesson_des` varchar(1000) DEFAULT NULL COMMENT '讲师自己介绍本次课程主要内容，培训受众等',
  `lesson_time` date DEFAULT NULL,
  `lesson_teacher` varchar(50) DEFAULT NULL,
  `lesson_grade` char(1) DEFAULT NULL COMMENT '不及格，及格，中等，良好，优秀',
  `lesson_score` float DEFAULT NULL COMMENT '听众给讲师打的分数，总分10分，这里取平均分.去除一个最高分去除一个最低分，剩余求平均分',
  `lesson_place` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `state` char(2) DEFAULT NULL COMMENT '0：未开讲 1：已开讲',
  `notes` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for zn_user_base
-- ----------------------------
DROP TABLE IF EXISTS `zn_user_base`;
CREATE TABLE `zn_user_base` (
  `id` int(8) unsigned NOT NULL AUTO_INCREMENT,
  `userName` varchar(40) DEFAULT NULL,
  `password` varchar(50) DEFAULT NULL,
  `nickName` varchar(40) DEFAULT NULL COMMENT '昵称 花名 网名',
  `sex` tinyint(1) DEFAULT NULL,
  `phone` varchar(20) DEFAULT NULL,
  `qq` varchar(20) DEFAULT NULL,
  `email` varchar(30) DEFAULT NULL,
  `imgUrl` varchar(50) DEFAULT NULL COMMENT '头像',
  `permission` int(10) unsigned DEFAULT '0' COMMENT '权限 类型 1 admin 超管 2 normal 普通注册用户 3 editer 编辑'',',
  `loginTime` datetime DEFAULT NULL,
  `loginIp` varchar(40) DEFAULT NULL,
  `lastLoginTime` datetime DEFAULT NULL,
  `lastLoginIp` varchar(40) DEFAULT NULL,
  `GMTCreat` datetime DEFAULT NULL,
  `GMTExpire` datetime DEFAULT NULL,
  `notes` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for zn_weekly_report
-- ----------------------------
DROP TABLE IF EXISTS `zn_weekly_report`;
CREATE TABLE `zn_weekly_report` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `report_writer` varchar(20) DEFAULT '',
  `report_this_week` varchar(1000) DEFAULT '',
  `report_next_week` varchar(1000) DEFAULT '',
  `report_difficulty` varchar(1000) DEFAULT '',
  `report_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for zn_weekreports
-- ----------------------------
DROP TABLE IF EXISTS `zn_weekreports`;
CREATE TABLE `zn_weekreports` (
  `ID` int(10) NOT NULL AUTO_INCREMENT,
  `reporter_writer` varchar(20) DEFAULT '',
  `reporter_thisweek` varchar(1000) DEFAULT '',
  `reporter_nextweek` varchar(1000) DEFAULT '',
  `reporter_difficulty` varchar(1000) DEFAULT '',
  `reporter_time` datetime DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8;
