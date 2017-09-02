#include <rpc/rpc.h>
#include <string.h>
#include "hw.h"

/* 
   Hello world RPC server -- it just returns the string.
*/
#define maxCli 10

char buffer[512];
int numMsg = 0;
int numCli = 0;
int codCli[11];

char **hw_1_svc(void *a, struct svc_req *req) {
	static char msg[256], newUser[15], newBuffer[512];
	static char *p;
	numCli++;
	if (numCli > maxCli){
		printf("Maximo de usuarios exedido\n");
		strcpy( msg, "Erro: Maximo de usarios exedido, nao permitido conexao.");
		p = msg;
		return (&p);
	}else if(numCli > 99){
		numCli = 1;
	}
	numMsg++;
	sprintf( newUser, "batata%i", numCli);
	sprintf( msg, "%04i:Bem vindo ao chat! Nome do usuario:", numMsg);
	strcat( msg, newUser);

	printf("New User: batata%i. Enviando boas vindas...\n", numCli);

	sprintf( newBuffer, "O usuario %s se conectou.", newUser);
	printf("Empilha mensagem %s\n", newBuffer);
	strcpy(buffer, newBuffer);

	p = msg;
	return(&p);
}

int *sts_1_svc(char **msg, struct svc_req *req){
	char tam = strlen( *msg) +1;
	int *ret;

	printf("Empilha mensagem %s\n", *msg);
	strcpy( buffer, msg);
	ret = malloc(sizeof(int));
	ret = 1;
	return (ret);
}

char **gfs_1_svc(int *numAtu, struct svc_req *req){
	static char *msg;
	static char *p;

	msg = malloc(512 * sizeof(char));
	if( numAtu < numMsg){
		strcpy( msg, buffer);
	}else{
		strcpy( msg, "Vazio")
	}
	p = msg;
	return (&p);
}
