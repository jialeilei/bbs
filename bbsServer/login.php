<?php

	include ("conn.php");//连接数据库
	$username = str_replace(" ","",$_POST['name']);//接收客户端发来的username；
	$pwd = str_replace(" ","",$_POST['pwd']);
	$sql = "select * from user where name='$username'";
	$query = mysql_query($sql);
	$rs = mysql_fetch_array($query);
	if(is_array($rs)){

		if(md5($pwd) == $rs['password']){
			$arr = array(
				
						'status' => "1",
						'userId'  => $rs['uid'],
					   'userName' => $rs['name'],
					   'sex' => $rs['sex'],
					   'level' => $rs['score'],
						
							);
			echo json_encode($arr);//登陆成功

		}else{
			$arr = array('status' => "2",'result' => md5($pwd));//密码错误
			echo json_encode($arr);
		}

	}else{
		$arr = array('status' => "0",'result' => "not exist");//用户名不存在
		echo json_encode($arr);
	}
	
?>