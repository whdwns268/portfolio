<?php
$host = "localhost";
$user = "memberd"; 
$pwd = "dclass";
$dbname = "dclass";

$connect = mysqli_connect($host,$user,$pwd) or die("데이터베이스 접속오류!!");
mysqli_select_db($connect,$dbname);
?>