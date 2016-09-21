<?php
include ("conn.php");//连接数据库
$subject=str_replace(" ","",$_POST['subject']);//标题
$content=str_replace(" ", "", $_POST['content']);//内容
$pid=str_replace(" ", "", $_POST['pid']);//pid=0 发帖，pid>0 跟贴
$uid=str_replace(" ", "", $_POST['uid']);//用户id
$uname=str_replace(" ", "", $_POST['uname']);//用户姓名
if ($pid>=0) {
	$sql="insert into tb_bbs (pid,subject,content,uid,uname) values ('$pid','$subject','$content','$uid','$uname')";//插入数据库
	mysql_query($sql);
	$result= array('status' => "success");
	echo json_encode($result);
}else{
	$result= array('status' => "fail");
	echo json_encode($result);
}

?>