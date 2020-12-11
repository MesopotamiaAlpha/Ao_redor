<?php

  class dboperation{

      private $con;

        function __construct(){

          require_once dirname(__FILE__).'/dbconnect.php';

          $db = new DbConnect();

          $this->con = $db->connect();


        }
      /*CRUD -> C -> Create */

      function createUser($username, $pass, $email){
        $password = md5($pass);
        $stmt = $this->con->prepare("insert into `users` (`id`, `username`, `password`, `email`) values (null, ?, ?, ?);");
        $stmt->bind_param("sss",$username,$password,$email);

        if($stmt->execute()){
          return true;
        }else{
          return false;
        }

      }

  }


 ?>
