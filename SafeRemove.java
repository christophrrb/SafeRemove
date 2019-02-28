import java.io.*;
import java.util.Scanner;

public class SafeRemove {

	public static void main(String[] args) {
		try {
			switch (args[0]) {

				case "--set-directory":
						
					File infoFile = new File("information.ser");
				
					if (infoFile.exists()) {
						FileInputStream fis = new FileInputStream(infoFile);
						ObjectInputStream ois = new ObjectInputStream(fis);

						Information serializedObject = (Information) ois.readObject();

						String originalTrashDirectory = serializedObject.getTrashDirectory();

						serializedObject.setTrashDirectory(args[1]);
	
						FileOutputStream fos = new FileOutputStream(infoFile);
						ObjectOutputStream oos = new ObjectOutputStream(fos);

						oos.writeObject(serializedObject);

						System.out.printf("The trash directory was successfully changed from \"%s\" to \"%s\"", originalTrashDirectory, args[1]);

						ois.close();
						fis.close();

						System.exit(1);
					} else {
						//To be written
					}

				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			File file = new File(args[0]); //Create a new File object based off of the given file name.
			
			if (file.exists() == false) { //If the file is not found,
				System.out.println(file.getPath() + " does not exist."); //Print the file path and tell the user that a file does not exist there,
				System.exit(0); //and close the program.
			}

			//Checking for the serialized object with the trash directory.
			File infoFile = new File("information.ser");
			File trashDirectory;

			if (infoFile.exists()) {
				FileInputStream fis = new FileInputStream(infoFile);
				ObjectInputStream ois = new ObjectInputStream(fis);

				Information serializedObject = (Information) ois.readObject();
				trashDirectory = new File(serializedObject.getTrashDirectory());

				ois.close();
				fis.close();
			} else {
				System.out.println("Enter the trash directory");
				
				Scanner sc = new Scanner(System.in);
				String userTrashDirectory = sc.nextLine();

				Information info = new Information(userTrashDirectory);
				
				FileOutputStream fos = new FileOutputStream(infoFile);
				ObjectOutputStream oos = new ObjectOutputStream(fos);

				oos.writeObject(info);

				trashDirectory = new File(userTrashDirectory);

				System.out.printf("The trash directory \"%s\" was successfully saved.\n", userTrashDirectory);
			}


			File toTrash = new File(trashDirectory + File.separator + file.getName());
			//String trashPath = trash.getPath();

			for (int i = 1; toTrash.exists() && i <= 100; i++) {
				String newFileName = trashDirectory + File.separator + file.getName() + "(" + i + ")";

				toTrash = new File(newFileName);
			}


				file.renameTo(toTrash);
				System.out.println("File was moved to trash with the name " + toTrash.getName() + ". (" + toTrash.getPath() + ")");

		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("Please enter a file.");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
