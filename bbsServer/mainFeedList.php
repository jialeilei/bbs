<?php
	include ("conn.php");//连接数据库

	//$sql="select * from tb_bbs where pid=0";//查询
	//$sql="select * from tb_bbs bbs ,tb_user user  where bbs.pid=0 and bbs.uid=user.id order by dateline desc";//查询
	$sql = "select * from  mainbbs bbs ,user where bbs.userid = user.uid order by sendtime desc";//查询
	$query = mysql_query($sql);
	$n = 0;
	while ($row = mysql_fetch_array($query)) {
		$arr[$n++] = array('userId' => $row['userid'],
			               'name' => $row['name'],
			               'sex' => $row['sex'],
			               	'score' => $row['score'],
						   'sendTime' => $row['sendtime'],
						   'mid' => $row['id'],

						   'title' => $row['title'],
						   'content' => $row['content'],
						   'answerNumber' => $row['answernumber'],
							
							);
	}
		//输出字符串数组
		echo json_encode($arr);
?>