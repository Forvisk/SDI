Adriano Zanella Junior

O servidor � em java e esta na pasta chat
O cliente � em python e � o arquivo ClientPyChat.py

Para o cliente � preciso as bibliotecas Zeep, que implementa o acesso WSDL que eu utilisei (o SOAPpy e o SOAPpy com WSDL n�o funcionaram)
A vers�o do python utilizado � o 2.7 e para aplicar a biblioteca zeep pode ser seguido os passos encontrados no site "http://docs.python-zeep.org/en/master/", na sess�o de Installation.


1) Compilar classes:
  javac chat/*.java
2) lancar servidor WS:
  java chat.ChatServerPublisher
3) Verificar disponibilidade do servico:
  navegador http://127.0.0.1:9876/chatAZJ?wsdl
4) executar cliente:
  java chat.ChatClient 