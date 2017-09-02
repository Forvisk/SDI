#include <stdio.h>
#include <string.h>
#include <rpc/rpc.h>
#include <pthread.h>
#include "hw.h"


void *enviaMsg( void *arg1, void *arg2);
void *recebeMsg( void *arg1, void *arg2);

int main (int argc, char *argv[]) {
	pthread_t envia, recebe;
	int rc;

	CLIENT *cl;
	char **p;
	int *r;
	char username[15] = "";
	int numMsg = 0;

	char *numFIni, *thrash;
	
	msg = malloc(512 * sizeof(char));
	if (argc != 2) {
		printf("Usage: client hostname\n");
		exit(1);
	}

	cl = clnt_create(argv[1], RPC_CHAT, RPC_CHAT_VERS, "tcp");

	if (cl == NULL) {
		clnt_pcreateerror(argv[1]);
		exit(1);
	}

	printf("Bem vindo ao chat!\n");
	pritf("Instucoes:\t\tSair: 'exit';\n");
	printf("\t\tAjuda: 'help'.\n");

	printf("Buscando espaco no servidor...\n");
	p = hw_1(NULL, cl);
	if (p == NULL) {
		clnt_perror(cl,argv[1]);
		exit(1);
	}
	printf("%s\n", *p);

	numFIni = strtok( *p, ":");
	thrash = strtok( *p, ":");
	strcpy( username, *p);

	for(i=0; i<strlen(numFIni); i++){
		numMsg = numMsg * 10 + ( numFIni[i] - '0' );
	}

	rc = pthread_create(&envia, NULL, enviaMsg, (void*)cl, (void*)username);
	rc = pthread_create(&recebe, NULL, recebeMsg, (void*)cl, (void*)numMsg);

    rc = pthread_join( envia, NULL);
    rc = pthread_join( recebe, NULL);
	return 0;
}

void *enviaMsg( void *arg1, void *arg2){
	CLIENT *cl = (CLIENT)arg1;
	char username[15] = (char[15]) arg2;
	char msg[512], newMsg;
	int ok = 1;
	while( ok){
		strcpy( msg, username);
		strcat( msg, ": ");
		scanf("%s", newMsg);
		strcat(msg, newMsg);
		r = sts_1( &msg, cl);
		if( r == NULL){
			clnt_perror(cl, argv[1]);
			return -1;
		}
	}
	return 1;
}

void *recebeMsg( void *arg1, void *arg2){
	CLIENT *cl = (CLIENT)arg1;
	int numMsg = (int)arg2;
	char **p;
	int ok = 1;

	while(ok){
		printf("Getting response from server:\n");
		p = gfs_1( &numMsg, cl);
		if( p ==NULL){
			clnt_perror(cl, argv[1]);
			return -1;		
		}
		printf(">> %s\n", *p);
		numMsg++;
	}
	return 1;
}