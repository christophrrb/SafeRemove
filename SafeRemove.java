import java.io.*;
import java.util.Scanner;

public class SafeRemove {

	public static void main(String[] args) {
		if (args.length == 2) { //If two arguments were given
			try {
				switch (args[0]) { //args[0] is the user's input.

					case "--set-directory": //Setting the trash directory
							
						//Loading the file and object
						File infoFile = loadInfoFile();
						Information serializedObject = loadSerializedObject(infoFile);

						String originalTrashDirectory = serializedObject.getTrashDirectory(); //Store the original trash directory in a variable.

						serializedObject.setTrashDirectory(args[1]); //Change the trash directory to the user-set one.
	
						FileOutputStream fos = new FileOutputStream(infoFile); //Create a FileOutputStream and ObjectOutputStream objects.
						ObjectOutputStream oos = new ObjectOutputStream(fos);

						oos.writeObject(serializedObject); //Write the newly changed Information object to the serialized-object file.

						System.out.printf("The trash directory was successfully changed from \"%s\" to \"%s\"", originalTrashDirectory, args[1]);

						System.exit(1); //End the program.

					break;

					case "--get-directory":
						Information serializedObj = loadSerializedObject(loadInfoFile());

						System.out.println(serializedObj.getTrashDirectory());
					break;
				} //end switch		
				
				
			} catch (Exception e) {
				e.printStackTrace();
			}

		} //end if

		
		//Moving a file to trash
		try {
			File file = new File(args[0]); //Create a new File object based off of the given file name.
			
			if (file.exists() == false) { //If the file is not found,
				System.out.println(file.getPath() + " does not exist."); //Print the file path and tell the user that a file does not exist there,
				System.exit(0); //and close the program.
			}

			//Checking for the serialized object with the trash directory.
			File trashDirectory = new File(loadSerializedObject(loadInfoFile()).getTrashDirectory());

			File toTrash = new File(trashDirectory + File.separator + file.getName()); //Create the File object for the File that's going to the trash as trashDirectoryLocation/filename.someExtension

			for (int i = 1; toTrash.exists() && i <= 100; i++) { //If a file with the same name is already in the trash directory, then add a number to it. For example, if a file named test.txt was already in the trash directory, then the file would be named test.txt(1).
				String newFileName = trashDirectory + File.separator + file.getName() + "(" + i + ")";

				toTrash = new File(newFileName); //The new file name is stored in the loop, and the loop runs again if the new file name is taken as well.

				if (i > 100) {
					System.out.println("The file could not be inserted in the trash. There may be an error, or too many files in the trash with the same name.");
					System.exit(0);
				}

			}


			file.renameTo(toTrash); //Moves the file to the trash directory.
			System.out.println("File was moved to trash with the name " + toTrash.getName() + ". (" + toTrash.getPath() + ")");

		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("Please enter a file name.");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static File loadInfoFile() {
		
		File infoFile = null;

		try {
			infoFile = new File(new File(SafeRemove.class.getProtectionDomain().getCodeSource().getLocation().toURI()) + File.separator + "information.ser"); //Creates the serialized-object file

			if (infoFile.exists()); //If the serialized-object file exists
				//Do nothing special.	

			else { //If the serialized-object file does not exist,
				Scanner sc = new Scanner(System.in);

				System.out.println("An existing trash directory was not found.");
				System.out.print("Enter the trash directory: ");
				String input = sc.nextLine();

				ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(infoFile));

				oos.writeObject(new Information(input));

				oos.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}

		return infoFile;
	}

	public static Information loadSerializedObject(File infoFile) {
		try {
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(infoFile));

			return (Information) ois.readObject();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}

		return null; //Shouldn't reach this line, but it's here to satisfy the compiler.
	}
}
