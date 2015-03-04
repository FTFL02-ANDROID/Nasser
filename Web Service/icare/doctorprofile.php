<?php 
include('db.php');

$json='{"id":"2","name":"Enayet ullah","specialization":"Heart Specialist","work_address":"Mirpur","contact_no":"0166555","email":"ishtiaqn2o@gmail.com","profile_id":"2"}';

$obj = json_decode($json,true);

 $sql="INSERT INTO `icare`.`doctors_profile` (`id`, `name`, `specialization`, `work_address`, `contact_no`, `email`, `profile_id`) VALUES 
 (NULL, '".$obj['name']."', '".$obj['specialization']."', '".$obj['work_address']."', '".$obj['contact_no']."', '".$obj['email']."', '".$obj['profile_id']."')";
 if(mysql_query($sql)){
	 echo " Doctors Profile created";
 }
?>