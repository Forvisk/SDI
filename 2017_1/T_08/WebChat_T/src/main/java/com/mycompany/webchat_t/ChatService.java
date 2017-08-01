/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.webchat_t;

import java.util.ArrayList;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;

/**
 *
 * @author adrianojr
 */
@WebService(serviceName = "ChatService")
public class ChatService {

     private ArrayList<String> mensagens;
     private int numMsg;

     @SuppressWarnings("Convert2Diamond")
     public ChatService() {
	  this.numMsg = 0;
	  mensagens = new ArrayList<String>();
	  mensagens.add("Chat iniciado!");
     }

 
     /**
      * Operação de Web service
      *
      * @param lstMsg
      * @return
      */
     @WebMethod(operationName = "ReceiveMsg")
     public String ReceiveMsg(@WebParam(name = "lstMsg") int lstMsg) {
	  if(( lstMsg < 0) || ( lstMsg > mensagens.size()))
	       return "-";
	  return mensagens.get(lstMsg);
     }

     /**
      * Operação de Web service
      *
      * @param mensagem
      * @return
      */
     @WebMethod(operationName = "SendMsg2")
     @SuppressWarnings("Convert2Diamond")
     public Boolean SendMsg2(@WebParam(name = "mensagem") String mensagem) {
	  if (mensagens == null) {
	       mensagens = new ArrayList<String>();
	       numMsg = 0;
	  }
	  mensagens.add(mensagem);
	  numMsg++;
	  return true;
     }

     /**
      * Operação de Web service
      * @return 
      */
     @WebMethod(operationName = "getNumMsg")
     public int getNumMsg() {
	  return mensagens.size();
     }

     /**
      * Operação de Web service
      * @param clientOk
      * @return 
      */
     @WebMethod(operationName = "ServerUp")
     public Boolean ServerUp(@WebParam(name = "clientOk") boolean clientOk) {
	  return clientOk;
     }
}
