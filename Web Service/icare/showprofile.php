<?php 
include('db.php');
$result=array();
$sql="SELECT * FROM `profile` WHERE `id` = 1 ";
$rs=mysql_query($sql);
$results = array();
while($row = mysql_fetch_assoc($rs)){
  $result[]= $row;
}
print json_encode($result);


	
?>