<?php

	include ("conn.php");//连接数据库

	$pid=$_POST['mid'];
	$sql="select * from answerbbs bbs ,user user where bbs.mid='$pid' and bbs.uid=user.uid  order by answertime";//查询
	$query=mysql_query($sql);
	$n=0;
	while ($row=mysql_fetch_array($query)) {
		$arr[$n++] = array( 'aid' => $row['aid'],
							'content' => $row['content'],
							'sendTime' => $row['answertime'],
							'score' => $row['score'],
							'uid' => $row['uid'],
							'name' => $row['name'],
							'sex' => $row['sex']
							);
	}
		//输出字符串数组
		echo json_encode($arr);
?>