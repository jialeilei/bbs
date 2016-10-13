<?php

include ("conn.php");//连接数据库

$uid = str_replace(" ", "", $_POST['uid']);//用户id
$uname = str_replace(" ", "", $_POST['uname']);//用户name
$mid = str_replace(" ", "", $_POST['mid']);//主贴id
$aid = str_replace(" ", "", $_POST['aid']);//更贴id
$to_tid = str_replace(" ", "", $_POST['to_tid']);//被回复用户的id
$to_tname = str_replace(" ", "", $_POST['to_tname']);//被回复用户name
$content = str_replace(" ", "", $_POST['content']);

if ($uid >= 0) {

	$sql = "insert into talkbbs (uid,uname,mid,aid,to_tid,to_tname,content) 
	values ('$uid','$uname','$mid','$aid','$to_tid','$to_tname','$content')";//插入数据库
	mysql_query($sql);
	$sql2="update mainbbs set answernumber = answernumber+1 where id ='$mid'";//更新评论数
	mysql_query($sql2);
	$sql_score = "update user set score = score+1 where uid ='$uid'";//用户积分
	mysql_query($sql_score);
	$result = array('status' => "1");

	echo json_encode($result);
}else{
	$result = array('status' => "0");

	echo json_encode($result);
}

?>