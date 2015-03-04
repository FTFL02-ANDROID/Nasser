<?php
define("HOST","localhost");
define("USER","root");
define("PASS","");

$con = mysql_connect(HOST,USER,PASS);
	if(!$con){
		die("could not connect to database");
	}else{
	mysql_select_db("icare",$con);
	//echo "database created";
	}
?>