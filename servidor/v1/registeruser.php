<?php

require_once '../includes/dboperations.php';

  $response = array();

  if($_SERVER['REQUEST_METHOD']=='POST'){

    if(
        isset($_POST['username']) and
        isset($_POST['email']) and
        isset($_POST['password']))
      {
        //operate de data further
        $db = new dboperation();
        if($db->createUser(
          $_POST['username'],
          $_POST['password'],
          $_POST['email']
        )){
          $response['error'] = false;
          $response['message'] = "Usuario registrado com sucesso";
        }else{
          $response['error'] = true;
          $response['message'] = "Algum erro acontece,tente novamente";
        }

      }else{
        $response['error'] = true;
        $response['message'] = "Campos necessarios estao faltando";
      }


  }else{
    $response['error'] = true;
    $response['message'] = "Pedido invalido";
  }

  echo json_encode($response);

 ?>
