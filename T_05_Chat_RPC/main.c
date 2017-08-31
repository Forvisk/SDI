#include <stdio.h>
#include <string.h>
#include <rpc/rpc.h>
#include "hw.h"

/* 
   Simple "hello world" program that demonstrates an rpc call.
*/

int main (int argc, char *argv[]) {

	CLIENT *cl;
	char **p;
	int **r;
	char msg[256];
	
	if (argc != 2) {
		printf("Usage: client hostname\n");
		exit(1);
	}

	cl = clnt_create(argv[1], RPC_CHAT, RPC_CHAT_VERS, "tcp");
	if (cl == NULL) {
		clnt_pcreateerror(argv[1]);
		exit(1);
	}

	printf("Getting ready to call hello world\n");
	p = hw_1(NULL, cl);

	printf("Back from hello world\n");
	if (p == NULL) {
		clnt_perror(cl,argv[1]);
		exit(1);
	}
	printf("Returned string=%s\n", *p);

	strcpy( msg, "Mensagem do cliete");
	printf("Sending message to server\n");
	r = sts_1( msg, cl);
	if( r == NULL){
		clnt_perror(cl, argv[1]);
		exit(1);
	}
	printf("resposta: %i\n", *r);

	printf("Getting response from server:\n");
	p = gfs_1(NULL, cl);
	if( p ==NULL){
		clnt_perror(cl, argv[1]);
		exit(1);		
	}
	printf("Response; %s\n", *p);


	return 0;
}