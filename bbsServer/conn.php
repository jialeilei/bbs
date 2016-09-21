<?php
$dbhost = "localhost:3306";
$dbuser = "root"; //我的用户名
$dbpass = ""; //我的密码
$dbname = "bbs"; //我的mysql库名
$cn = mysql_connect($dbhost,$dbuser,$dbpass) or die("connect error");
@mysql_select_db($dbname)or die("db error");
mysql_query("set names 'utf8'");
mysql_query("set character_set utf8");
mysql_query("set character_set_result=utf8");
?>