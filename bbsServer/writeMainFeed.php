<?php
include ("conn.php");//连接数据库
//$data = $_POST;
/*$attr = $GLOBALS['HTTP_RAW_POST_DATA'];
$data = array();
$data = json_decode($attr);

if (count($data) > 0) {
	$sql="insert into mainbbs (title,content,userid) values ($data.['title'],$data.['content'],21)";//插入数据库
	mysql_query($sql);
	$result= array('status' => "1",'result' =>"success");//success
	echo json_encode($result);
}else{
	$result= array('status' => "0");//fail
	echo json_encode($result);
}

	log("count: ".count($getData));
*/
	
//common
$subject=str_replace(" ","",$_POST['title']);//标题
$content=str_replace(" ", "", $_POST['content']);//内容
$uid=str_replace(" ", "", $_POST['userId']);//用户id

if ($uid > 0) {
	$sql="insert into mainbbs (title,content,userid) values ('$subject','$content','$uid')";//插入数据库
	mysql_query($sql);
	$sql_score = "update user set score = score + 5 where uid ='$uid'";//用户积分
	mysql_query($sql_score);
	$result= array('status' => "1");//success
	echo json_encode($result);
}else{
	$result= array('status' => "0");//fail
	echo json_encode($result);
}

?>