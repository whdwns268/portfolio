<?php
$host = "localhost";
$user = "whdwns268"; 
$pwd = "jongjun36!";
$dbname = "whdwns268";

$connect = mysqli_connect($host,$user,$pwd) or die("데이터베이스 접속오류!!");
mysqli_select_db($connect,$dbname);
?>