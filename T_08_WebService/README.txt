Adriano Zanella Junior

O servidor é em java e esta na pasta chat
O cliente é em python e é o arquivo ClientPyChat.py

Para o cliente é preciso as bibliotecas Zeep, que implementa o acesso WSDL que eu utilisei (o SOAPpy e o SOAPpy com WSDL não funcionaram)
A versão do python utilizado é o 2.7 e para aplicar a biblioteca zeep pode ser seguido os passos encontrados no site "http://docs.python-zeep.org/en/master/", na sessão de Installation.


1) Compilar classes:
  javac chat/*.java
2) lancar servidor WS:
  java chat.ChatServerPublisher
3) Verificar disponibilidade do servico:
  navegador http://127.0.0.1:9876/chatAZJ?wsdl
4) executar cliente:
  java chat.ChatClient 