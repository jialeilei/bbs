<?php

include ("conn.php");//连接数据库

$content = str_replace(" ", "", $_POST['content']);//内容
$mid = str_replace(" ", "", $_POST['mid']);
$uid = str_replace(" ", "", $_POST['uid']);//用户id
//$uname=str_replace(" ", "", $_POST['uname']);//用户姓名


if ($uid >= 0) {
	$sql = "insert into answerbbs (uid,mid,content) values ('$uid','$mid','$content')";//插入数据库
	$sql2="update mainbbs set answernumber = answernumber+1 where id ='$mid'";//显示评论数
	$sql_score = "update user set score = score + 1 where uid = '$uid'";//用户积分
	mysql_query($sql);
	mysql_query($sql2);
	mysql_query($sql_score);
	$result = array('status' => "1");
	echo json_encode($result);
}else{
	$result = array('status' => "0");
	echo json_encode($result);
}

?>