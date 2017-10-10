package com.wsudesc.app;
import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.FileMetadata;
import com.dropbox.core.v2.files.ListFolderResult;
import com.dropbox.core.v2.files.Metadata;
import com.dropbox.core.v2.users.FullAccount;
import java.util.List;
import java.io.FileNotFoundException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.IOException;

public class SDITestDropBox {    
//private static final String ACCESS_TOKEN = "bV9CF_mwE-AAAAAAAAAACk1uSh9BBfEW3njWnALZYrYKHp9pdRGi27_zLY6neJ3o";
	private static final String ACCESS_TOKEN = "cAOcO6AuOGAAAAAAAAABiHFP5z4mOpd3pKHfuJ8q8xL2kep6FP50ATaILd70_zvb";
	private static final String LOCALDOWNLOAD = "/tmp";
	private static final String NOVOARQUIVO = "/test.txt";
	private static final String DIRECTORY = "/tmp";
	private static final String ARQUIVODOWLOAD = "/img1.jpg";
	private static final String NOVAPASTA = "/Pasta00";

	public static void main(String args[]) throws DbxException, IOException {
// Create Dropbox client
		DbxRequestConfig config = new DbxRequestConfig("dropbox/java-tutorial", "en_US");
		DbxClientV2 client = new DbxClientV2(config, ACCESS_TOKEN);

// Get current account info
		FullAccount account = client.users().getCurrentAccount();
		System.out.println(account.getName().getDisplayName());

// Get files and folder metadata from Dropbox root directory
		ListFolderResult result = client.files().listFolder("");
		while (true) {
			for (Metadata metadata : result.getEntries()) {
				System.out.println(metadata.getPathLower());
			}

			if (!result.getHasMore()) {
				break;
			}

			result = client.files().listFolderContinue(result.getCursor());
		}
/*
// Upload "test.txt" to Dropbox
		try (InputStream in = new FileInputStream("/tmp/test.txt")) {
			FileMetadata metadata = client.files().uploadBuilder("/test.txt").uploadAndFinish(in);
		}
		*/
	}

	private static boolean uploadArquivo( String nomeArquivo, DbxClientV2 cliente) throws FileNotFoundException, IOException, DbxException{
		try (InputStream in = new FileInputStream(DIRECTORY + nomeArquivo)) {
			FileMetadata uploadAndFinish = cliente.files().uploadBuilder(nomeArquivo).uploadAndFinish(in);
		}
		return true;
	}

	private static boolean downloadArquivo( String nomeArquivo, DbxClientV2 cliente) throws FileNotFoundException, DbxException, IOException{
		try {
			OutputStream downFile = new FileOutputStream(LOCALDOWNLOAD + nomeArquivo);
			try {
				FileMetadata meta = cliente.files().downloadBuilder(nomeArquivo).download(downFile);
			} finally{
				downFile.close();
			}
		}
		return true;
	}
	/*
	private static boolean criarPasta(String nomePasta, DbxClientV2 cliente) throws DbxException, IOException{
		cliente.files().createFolder(nomePasta);
		return true;
	}
	*/
}
