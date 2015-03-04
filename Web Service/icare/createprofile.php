<?php 
include('db.php');

if(isset($_POST['json']))
{
	echo "Yahoo! I got it";
//	$json = "'" + $_POST["json"] +"'";



$obj = json_decode($_POST['json'],true);
 $sql="INSERT INTO `icare`.`profile` (`id`, `name`, `date_of_birth`, `height`, `weight`, `gender`)
 VALUES (NULL, '".$obj['name']."', '".$obj['date_of_birth']."', '".$obj['height']."', '".$obj['weight']."', '".$obj['gender']."')";
 if(mysql_query($sql)){
	 echo "Profile created";
 }
 }
 //echo($obj['name']);
 //print_r($obj);
?>