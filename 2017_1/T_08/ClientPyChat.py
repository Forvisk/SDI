import zeep
import threading
import time

class recebeMsg(threading.Thread):
    def run(self):
        #print "iniciando receptor"
        receber()
        #print "Saindo receptor"

class enviaMsg(threading.Thread):
    def run(self):
        #print "iniciando emissor"
        time.sleep(3)
        envia()
        #print "Saindo emissor"

def receber():
    lstMsg = client.service.getNumMsg() - 1
    #lstMsg = 0
    print lstMsg
    while(client.service.ServerUp(True)):
        while( lstMsg < client.service.getNumMsg()):
            #time.sleep(0.5)
            inMsg = client.service.ReceiveMsg(lstMsg)
            if not( inMsg == "-"):
                print inMsg
                lstMsg+=1

def envia():
    while(client.service.ServerUp(True)):
        time.sleep(1)
        newMsg = raw_input("- ")
        if(len(newMsg) > 0):
            if(client.service.ServerUp(True)):
                if not client.service.SendMsg2(newMsg):
                    print "falha no envio da mensagem!"
                
        

wsdl = 'http://localhost:8080/WebChat_T/ChatService?WSDL'
client = zeep.Client(wsdl = wsdl)

#print client.service.ReceiveMsg(0)

thread1 = recebeMsg()
thread2 = enviaMsg()

thread1.start()
thread2.start()
