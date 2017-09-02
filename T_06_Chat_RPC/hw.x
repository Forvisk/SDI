
program RPC_CHAT {
	version RPC_CHAT_VERS{
		string HW(void) = 1;
		int STS(string) = 2;
		string GFS(int) = 3;
	} = 1;
} = 0x30000824;