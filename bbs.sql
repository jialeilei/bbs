-- phpMyAdmin SQL Dump
-- version 4.0.4.1
-- http://www.phpmyadmin.net
--
-- 主机: 127.0.0.1
-- 生成日期: 2018 �?06 �?27 �?16:54
-- 服务器版本: 5.6.11
-- PHP 版本: 5.5.1

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- 数据库: `bbs`
--
CREATE DATABASE IF NOT EXISTS `bbs` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
USE `bbs`;

-- --------------------------------------------------------

--
-- 表的结构 `answerbbs`
--

CREATE TABLE IF NOT EXISTS `answerbbs` (
  `aid` int(11) NOT NULL AUTO_INCREMENT,
  `uid` int(11) NOT NULL,
  `mid` int(11) NOT NULL,
  `content` text NOT NULL,
  `answertime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`aid`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=29 ;

--
-- 转存表中的数据 `answerbbs`
--

INSERT INTO `answerbbs` (`aid`, `uid`, `mid`, `content`, `answertime`) VALUES
(1, 23, 11, '回复测试', '0000-00-00 00:00:00'),
(2, 23, 11, '回复测试2反反复复', '2016-09-28 05:16:50'),
(3, 21, 11, '嘎嘎嘎反反复复，肯定觉得肯定。', '2016-09-28 05:17:24'),
(4, 24, 11, '天下第一的回复', '2016-09-29 02:33:40'),
(5, 24, 11, '在回复一条', '2016-09-29 02:34:19'),
(6, 24, 11, '让v让vTV给TVTV他\n也不TV他\n也不TV他\n要v他TV\nTVTV他\n也不TV他\nTV他TV\nTVTV他\n图v图', '2016-09-29 02:47:06'),
(7, 24, 12, '没人？', '2016-09-29 05:11:24'),
(8, 24, 9, '这是一个更贴', '2016-09-29 05:14:35'),
(9, 24, 12, '真的没人?', '2016-09-29 05:37:32'),
(10, 24, 12, '啊啊啊', '2016-09-29 05:50:35'),
(11, 24, 12, '有意义', '2016-09-29 05:52:53'),
(12, 24, 13, '梗', '2016-09-29 05:56:15'),
(13, 24, 13, '测试', '2016-09-29 06:02:30'),
(14, 24, 7, '测试', '2016-09-29 06:04:37'),
(15, 24, 10, '顶一个', '2016-09-29 07:40:01'),
(16, 24, 11, '浏览量', '2016-09-29 09:09:19'),
(17, 22, 12, '吹雪过来支持一下子', '2016-09-29 09:12:02'),
(18, 22, 10, '顶', '2016-09-29 09:12:48'),
(19, 22, 13, '吹雪来啦', '2016-09-29 11:54:31'),
(20, 22, 12, '前面都是楼主', '2016-09-29 12:15:58'),
(21, 22, 9, '顶一个', '2016-09-29 12:47:26'),
(22, 23, 13, '武松打虎', '2016-09-30 01:26:00'),
(23, 23, 11, '我的贴子', '2016-09-30 01:36:46'),
(24, 23, 0, '', '2016-10-03 08:26:41'),
(25, 17, 13, '发一条信息', '2016-10-07 13:12:23'),
(26, 17, 13, '再来\n', '2016-10-07 13:13:47'),
(27, 17, 11, '这是回复的谁？', '2016-10-07 13:24:50'),
(28, 17, 14, '一哥哥们说，他误删了公司的重要文件，被主管打断了一条腿，公司赔了100万，那一年他27……', '2016-10-09 02:44:54');

-- --------------------------------------------------------

--
-- 表的结构 `mainbbs`
--

CREATE TABLE IF NOT EXISTS `mainbbs` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userid` int(11) NOT NULL,
  `sendtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `title` varchar(50) NOT NULL,
  `content` varchar(200) NOT NULL,
  `answer_number` int(11) NOT NULL DEFAULT '0',
  `image_number` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=28 ;

--
-- 转存表中的数据 `mainbbs`
--

INSERT INTO `mainbbs` (`id`, `userid`, `sendtime`, `title`, `content`, `answer_number`, `image_number`) VALUES
(1, 21, '2016-09-20 17:02:08', '测试帖子标题', '测试帖子内容', 0, 0),
(2, 21, '2016-09-20 17:00:00', '测试帖子标题2', '测试帖子内容2', 0, 0),
(3, 21, '0000-00-00 00:00:00', 'd''d', '放放风', 0, 0),
(4, 21, '0000-00-00 00:00:00', 's''s''s''s', '烦烦烦', 0, 0),
(5, 21, '0000-00-00 00:00:00', 's''s', 'd''d''d''d''d''d', 0, 0),
(6, 5, '0000-00-00 00:00:00', 'title', 'content', 0, 0),
(7, 21, '0000-00-00 00:00:00', '天天', '他天天', 1, 0),
(8, 21, '0000-00-00 00:00:00', '测试贴', '这是一哥测试贴而已(⊙o⊙)', 0, 0),
(9, 21, '2016-09-27 06:49:20', '主题贴', 'hello', 2, 0),
(10, 22, '2016-09-27 06:51:28', '来一个', '来一个帖子', 4, 0),
(11, 23, '2016-09-27 06:54:22', '我是武松', '武松打虎', 25, 0),
(12, 24, '2016-09-28 09:12:40', '发一个新帖子', '新帖子，懂不', 9, 0),
(13, 24, '2016-09-29 01:04:14', '隐隐约约', '抬头法国广告', 8, 1),
(14, 17, '2016-10-09 02:11:20', '知乎上最近很火的一个帖子', '问题是:30岁之前赚一百万是什么体验，然后各种奇葩回答……', 2, 1),
(22, 24, '2016-11-01 06:25:20', '测试6', '非国有', 0, 1),
(23, 24, '2016-11-01 06:30:24', '测试7', '法院方法', 0, 1),
(24, 17, '2016-11-03 08:11:14', '2张测试', '高圆圆', 0, 2),
(25, 17, '2016-11-03 08:26:31', '3这嘎不过', '复古风', 0, 2),
(26, 17, '2016-11-03 08:43:01', '关于', '方法', 0, 2),
(27, 17, '2016-11-03 09:02:56', '测试测定', '给狗狗', 0, 2);

-- --------------------------------------------------------

--
-- 表的结构 `talkbbs`
--

CREATE TABLE IF NOT EXISTS `talkbbs` (
  `tid` int(11) NOT NULL AUTO_INCREMENT,
  `uid` int(11) NOT NULL,
  `uname` varchar(30) DEFAULT NULL,
  `mid` int(11) NOT NULL,
  `aid` int(11) NOT NULL,
  `to_uid` int(11) NOT NULL,
  `to_uname` varchar(30) DEFAULT NULL,
  `to_tid` int(11) DEFAULT '0',
  `to_tname` varchar(11) DEFAULT NULL,
  `content` text NOT NULL,
  `talk_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`tid`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=25 ;

--
-- 转存表中的数据 `talkbbs`
--

INSERT INTO `talkbbs` (`tid`, `uid`, `uname`, `mid`, `aid`, `to_uid`, `to_uname`, `to_tid`, `to_tname`, `content`, `talk_time`) VALUES
(1, 24, '天下第一', 11, 1, 23, '武松', 0, '0', '这是一条盖楼评论测试', '2016-10-06 06:41:40'),
(2, 22, '西门吹雪', 11, 1, 23, '武松', 0, '0', '我也来盖楼，盖楼怎么能没有我，是不是', '2016-10-06 06:41:45'),
(3, 22, '西门吹雪', 11, 2, 23, '武松', 0, '0', '回帖测试', '2016-10-07 06:46:07'),
(4, 22, '西门吹雪', 11, 1, 0, '0', 24, '天下第一', '回帖测试测试', '2016-10-07 07:03:49'),
(5, 23, '武松', 11, 3, 0, NULL, 0, '0', '回复的是123', '2016-10-07 13:03:09'),
(6, 23, '武松', 11, 3, 0, NULL, 0, '0', '再来一条', '2016-10-07 13:04:12'),
(7, 17, 'lei', 13, 12, 0, NULL, 0, '0', '跟这是楼中楼，也叫二级恢复', '2016-10-07 13:13:03'),
(8, 17, 'lei', 11, 1, 0, NULL, 0, '0', '没我怎么行啊(⊙o⊙)', '2016-10-07 13:15:37'),
(9, 17, 'lei', 11, 3, 0, NULL, 0, '0', '跟一条', '2016-10-07 13:24:17'),
(10, 23, '武松', 13, 12, 0, NULL, 0, '0', '支持一下下', '2016-10-07 13:27:19'),
(11, 23, '武松', 12, 7, 0, NULL, 0, '0', '嗯', '2016-10-07 13:39:43'),
(12, 23, '武松', 11, 1, 0, NULL, 0, '0', '发我一下联系方式', '2016-10-09 01:20:12'),
(13, 23, '武松', 11, 1, 0, NULL, 0, '0', 'Lei,发一下联系方式', '2016-10-09 01:21:46'),
(14, 23, '武松', 11, 1, 0, NULL, 22, '西门吹雪', '哥们儿，来个联系方式啊', '2016-10-09 01:23:39'),
(15, 23, '武松', 11, 1, 0, NULL, 23, '武松', '好，110', '2016-10-09 01:24:00'),
(16, 23, '武松', 11, 2, 0, NULL, 22, '西门吹雪', '噢噢', '2016-10-09 01:33:54'),
(17, 23, '武松', 11, 2, 0, NULL, 22, '西门吹雪', '噢噢', '2016-10-09 01:33:54'),
(18, 23, '武松', 12, 9, 0, NULL, 0, '0', '阿里克木浆', '2016-10-09 01:50:02'),
(19, 17, 'lei', 12, 9, 0, NULL, 23, '武松', '这是新疆名字', '2016-10-09 01:51:18'),
(20, 17, 'lei', 10, 18, 0, NULL, 0, '0', '楼主好', '2016-10-09 02:08:29'),
(21, 17, 'lei', 10, 15, 0, NULL, 0, '0', '天下第一你好啊(⊙o⊙)', '2016-10-09 03:00:32'),
(22, 24, '天下第一', 14, 28, 0, NULL, 0, '0', '楼主继续', '2016-10-17 13:29:42'),
(23, 24, '天下第一', 11, 1, 0, NULL, 23, '武松', '我来一起', '2016-11-01 08:54:43'),
(24, 17, 'lei', 11, 1, 0, NULL, 23, '武松', '110', '2016-11-01 08:58:16');

-- --------------------------------------------------------

--
-- 表的结构 `tb_image`
--

CREATE TABLE IF NOT EXISTS `tb_image` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `mid` int(11) NOT NULL DEFAULT '0',
  `aid` int(11) NOT NULL DEFAULT '0',
  `img` text,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=13 ;

--
-- 转存表中的数据 `tb_image`
--

INSERT INTO `tb_image` (`id`, `mid`, `aid`, `img`) VALUES
(1, 14, 0, 'image/head/20161015151224.png'),
(2, 13, 0, 'image/head/20161016152352.png'),
(3, 22, 0, 'image/main/20161101072520.png'),
(4, 23, 0, 'image/main/20161101073024.png'),
(5, 24, 0, 'image/main/20161103091115.png'),
(6, 24, 0, 'image/main/20161103091115.png'),
(7, 25, 0, 'image/main/20161103092632.png'),
(8, 25, 0, 'image/main/20161103092632.png'),
(9, 26, 0, 'image/main/20161103094302.png'),
(10, 26, 0, 'image/main/20161103094302.png'),
(11, 27, 0, 'image/main/20161103100256672.png'),
(12, 27, 0, 'image/main/20161103100256472.png');

-- --------------------------------------------------------

--
-- 表的结构 `user`
--

CREATE TABLE IF NOT EXISTS `user` (
  `uid` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL,
  `nickname` varchar(20) DEFAULT NULL,
  `password` varchar(50) NOT NULL,
  `sex` varchar(4) DEFAULT 'girl',
  `score` int(11) DEFAULT '5',
  `avatar` text,
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=26 ;

--
-- 转存表中的数据 `user`
--

INSERT INTO `user` (`uid`, `name`, `nickname`, `password`, `sex`, `score`, `avatar`) VALUES
(16, '22', '', '202cb962ac59075b964b07152d234b70', 'girl', 5, ''),
(17, 'lei', '', '202cb962ac59075b964b07152d234b70', 'boy', 63, 'image/head/20161015151224.png'),
(21, '123', NULL, '202cb962ac59075b964b07152d234b70', 'boy', 25, NULL),
(22, '西门吹雪', NULL, '202cb962ac59075b964b07152d234b70', 'girl', 100, NULL),
(23, '武松', NULL, '202cb962ac59075b964b07152d234b70', 'boy', 5, 'image/head/20161003111901.png'),
(24, '天下第一', NULL, '202cb962ac59075b964b07152d234b70', 'girl', 78, 'image/head/20161016152352.png'),
(25, '', NULL, 'd41d8cd98f00b204e9800998ecf8427e', 'girl', 5, NULL);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
