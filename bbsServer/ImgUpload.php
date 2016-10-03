<?php
	include ("conn.php");//连接数据库

	$data = base64_decode($_POST['avatar']);
	$uid  = ($_POST['uid']);

	$filename = date("YmdHis");
	$base_path  = "image/head/";//接收文件目录 
	$file = fopen($base_path.$filename.".png", "w");

	fwrite($file, $data);
	fclose($file);
	if ($data != null) {

		$sql_user = "select * from user where uid = '$uid'";
		$query = mysql_query($sql_user);
		$rs = mysql_fetch_array($query);
		if ($rs['avatar'] != "") {

			//删除数据


			//更新数据库
			$sql="insert into user (avatar) values ('$data') where uid = $uid";
			mysql_query($sql);
			$result= array('status' => "1");//success
			echo json_encode($result);
			
		}else{

			$sql="update user set avatar ='{$base_path}"."{$filename}".".png' where uid ={$uid}";//更新数据库
			mysql_query($sql);
			$result= array('status' => "1");//success
			echo json_encode($result);
		}

	}else{
		$result= array('status' => "0");//fail
		echo json_encode($result);
	}
	
?>