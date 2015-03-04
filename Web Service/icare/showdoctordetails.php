<?php 
//getting all doctors
include('db.php');
$result=array();
$sql="SELECT * FROM `doctors_profile` WHERE `id` = 2 AND `profile_id` = 1 ";
$rs=mysql_query($sql);
$results = array();
while($row = mysql_fetch_assoc($rs)){
  $result[]= $row;
}
//print_r($result);
print json_encode($result);


	
?>