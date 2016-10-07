<?php

	include ("conn.php");//连接数据库

	$mid = $_POST['mid'];

	$sql="select * from talkbbs where mid='$mid' order by talk_time";//查询
	$query=mysql_query($sql);
	$n=0;
	while ($row = mysql_fetch_array($query)) {

		$feedList[$n++] = array( 
							'tid' => $row['tid'],
							//评论用户信息
							'uid' => $row['uid'],
							'uname' => $row['uname'],
							//主贴id
							'mid' => $row['mid'],
							//回帖id
							'aid' => $row['aid'],
							//被评论的用户信息
							/*'to_uid' => $row['to_uid'],
							'to_uname' => $row['to_uname'],*/
							'to_tid' => $row['to_tid'],
							'to_tname' => $row['to_tname'],
							//内容、时间
							'content' => $row['content'],
							'talktime' => $row['talk_time']
							);
	}
		//输出字符串数组
		echo json_encode($feedList);
	
?>