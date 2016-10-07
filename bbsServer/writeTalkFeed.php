<?php

include ("conn.php");//连接数据库

$uid = str_replace(" ", "", $_POST['uid']);//用户id
$uname = str_replace(" ", "", $_POST['uname']);//用户name
$mid = str_replace(" ", "", $_POST['mid']);//主贴id
$aid = str_replace(" ", "", $_POST['aid']);//更贴id
$to_tid = str_replace(" ", "", $_POST['to_tid']);//被回复用户的id
$to_tname = str_replace(" ", "", $_POST['to_tname']);
$content = str_replace(" ", "", $_POST['content']);

if ($uid >= 0) {

	$sql = "insert into talkbbs (uid,uname,mid,aid,to_tid,to_tname,content) 
	values ('$uid','$uname','$mid','$aid','$to_tid','$to_tname','$content')";//插入数据库
	mysql_query($sql);
	$result = array('status' => "1");

	echo json_encode($result);
}else{
	$result = array('status' => "0");

	echo json_encode($result);
}

?>