import zeep
import threading
import time

import zeep
import threading
import time

username

class recebeMsg(threading.Thread):
    def run(self):
        #print "iniciando receptor"
        receber()
        #print "Saindo receptor"

class enviaMsg(threading.Thread):
    def run(self):
        #print "iniciando emissor"
        time.sleep(3)
        enviar()
        #print "Saindo emissor"

def receber():
    lstMsg = client.service.getNumMsg() - 1
    #lstMsg = 0
    print lstMsg
    while(true):
        while( lstMsg < client.service.getNumMsg()):
            #time.sleep(0.5)
            inMsg = client.service.getMesage(lstMsg)
            if not( inMsg == "-"):
                print inMsg
                lstMsg+=1

def enviar():
	msg
    while(true):
    	msg = raw_input(":")
    	msg = ">>"+username+":"+msg
    	client.service.sendMesage(msg)

                
        

wsdl = 'http://127.0.0.1:9876/chatAZJ?wsdl'
client = zeep.Client(wsdl = wsdl)

#print client.service.ReceiveMsg(0)
username = raw_input("Usuariop:")
client.loginServer(username)

thread1 = recebeMsg()
thread2 = enviaMsg()

thread1.start()
thread2.start()
