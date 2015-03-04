<?php 
include('db.php');




$result=array();
$sql="SELECT * FROM `profile`";
$rs=mysql_query($sql);
$results = array();

 $result["profiles"] = array();
while($row = mysql_fetch_assoc($rs)){
 // $result[]= $row;
  
     $profile = array();
        $profile["id"] = $row["id"];
        $profile["name"] = $row["name"];
        $profile["date_of_birth"] = $row["date_of_birth"];
        $profile["height"] = $row["height"];
        $profile["weight"] = $row["weight"];
		$profile["gender"] = $row["gender"];
 
        // push single profile into final response array
        array_push($result["profiles"], $profile);
}
print json_encode($result);


	
?>