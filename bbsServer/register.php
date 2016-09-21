<?php
	include ("conn.php");//连接数据库
	$username = str_replace(" ","",$_POST['name']);//接收客户端发来的name
	$pwd = str_replace(" ", "", $_POST['pwd']);//接收客户端发来的pwd
	
	$userpwd = md5($pwd);
	$sql = "select * from user where name='$username'";
	$query = mysql_query($sql);
	$rs = mysql_fetch_array($query);

	if(is_array($rs)){
			$arr = array('status' => "2");
			echo json_encode($arr);
	}else{
			$sqlinsert = "insert into user (name,password) values ('$username','$userpwd')"; //插入数据库
			mysql_query($sqlinsert);
			$arr = array('status' => "1",'result'=>"success"); //success
			echo json_encode($arr);
		}
		
?>	