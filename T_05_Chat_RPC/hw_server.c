#include <rpc/rpc.h>
#include <string.h>
#include "hw.h"

/* 
   Hello world RPC server -- it just returns the string.
*/

char buffer[512];
int numCli = 0;

char **hw_1_svc(void *a, struct svc_req *req) {
	static char msg[256];
	static char *p;

	printf("getting ready to return value\n");
	strcpy(msg, "Hello world");
	p = msg;
	printf("Returning...\n");

	return(&p);
}

int *sts_1_svc(char **msg, struct svc_req *req){
	char tam = strlen( *msg) +1;
	int *ret;

	printf("Empilha mendagem %s\n", *msg);
	ret = malloc(sizeof(int));
	ret = 1;
	return (ret);
}

char **gfs_1_svc(void *a, struct svc_req *req){
	static char *msg;
	static char *p;
	msg = malloc(512 * sizeof(char));
	printf("Testando mensagem\n");
	strcpy( msg, "Teste");
	p = msg;

	return (&p);
}
