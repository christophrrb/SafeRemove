import java.io.*;
import java.util.Scanner;

public class SafeRemove {

	public static void main(String[] args) {
		try {
			switch (args[0]) { //args[0] is the user's input.

				case "--set-directory": //Setting the trash directory
						
					File infoFile = new File("information.ser"); //Creates the serialized-object file.
				
					if (infoFile.exists()) { //If the serialized-object file exists
						FileInputStream fis = new FileInputStream(infoFile); //then create FileInputStream and ObjectInputSream objects.
						ObjectInputStream ois = new ObjectInputStream(fis);

						Information serializedObject = (Information) ois.readObject(); //Store the object from the file in a variable.

						String originalTrashDirectory = serializedObject.getTrashDirectory(); //Store the original trash directory in a variable.

						serializedObject.setTrashDirectory(args[1]); //Change the trash directory to the user-set one.
	
						FileOutputStream fos = new FileOutputStream(infoFile); //Create a FileOutputStream and ObjectOutputStream objects.
						ObjectOutputStream oos = new ObjectOutputStream(fos);

						oos.writeObject(serializedObject); //Write the newly changed Information object to the serialized-object file.

						System.out.printf("The trash directory was successfully changed from \"%s\" to \"%s\"", originalTrashDirectory, args[1]);

						ois.close(); //Close the ObjectInputStream and FileInputStream.
						fis.close();

						System.exit(1); //End the program.
					} else {
						//To be written
					}

				break;

				case "--get-directory":
//					File file = new File("/home/christophrrb/safe_remove/information.ser"); //Create a File Obeject of the file for the Information obejct.


					File file = new File(new File(SafeRemove.class.getProtectionDomain().getCodeSource().getLocation().toURI()) + File.separator + "information.ser"); //Creates a new File with the supposed location of the serialized object.

					if (file.exists()) {
						ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file)); //ObjectInputStream Object
						Information info = (Information) ois.readObject(); //Reloading Information Object

						System.out.println(info.getTrashDirectory()); //Print the user's trash directory location to the console

						ois.close(); //Close the ObjectInputStream
						System.exit(1); //Exit the program.
					} else {
						System.out.println("The file doesn't exist. --get-directory");
					}
				break;
			} //end switch		

		} catch (Exception e) {
			e.printStackTrace();
		}

		
		//Moving a file to trash
		try {
			File file = new File(args[0]); //Create a new File object based off of the given file name.
			
			if (file.exists() == false) { //If the file is not found,
				System.out.println(file.getPath() + " does not exist."); //Print the file path and tell the user that a file does not exist there,
				System.exit(0); //and close the program.
			}

			//Checking for the serialized object with the trash directory.
			File infoFile = new File(new File(SafeRemove.class.getProtectionDomain().getCodeSource().getLocation().toURI()) + File.separator + "information.ser");
			File trashDirectory;

			if (infoFile.exists()) { //If the file to be deleted exists,
				//Create an ObjectInputStream
				ObjectInputStream ois = new ObjectInputStream(new FileInputStream(infoFile));

				//Get the trash directory's location
				Information serializedObject = (Information) ois.readObject();
				trashDirectory = new File(serializedObject.getTrashDirectory());

				//Close the ObjectInputStream and 
				ois.close();
			} else { //If the serialized-object file doesn't exist,
				System.out.println("Enter the trash directory");
				
				Scanner sc = new Scanner(System.in);
				String userTrashDirectory = sc.nextLine(); //get the user's input for where the trash directory is stored,

				Information info = new Information(userTrashDirectory); //create an Information object to store the directory,
				
				ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(infoFile)); //create an ObjectOutputStream to write tot he file with the serialized object,

				oos.writeObject(info); //and write the serialized object to the file.

				trashDirectory = new File(userTrashDirectory); //Put the trash directory in a variable called trahDirectory (so it can be used to move the file to the trash.

				System.out.printf("The trash directory \"%s\" was successfully saved.\n", userTrashDirectory);
				oos.close(); //Close the ObjectOutputStream.
			}


			File toTrash = new File(trashDirectory + File.separator + file.getName()); //Create the File object for the File that's going to the trash as trashDirectoryLocation/filename.someExtension

			for (int i = 1; toTrash.exists() && i <= 100; i++) { //If a file with the same name is already in the trash directory, then add a number to it. For example, if a file named test.txt was already in the trash directory, then the file would be named test.txt(1).
				String newFileName = trashDirectory + File.separator + file.getName() + "(" + i + ")";

				toTrash = new File(newFileName); //The new file name is stored in the loop, and the loop runs again if the new file name is taken as well.
			}


				file.renameTo(toTrash); //Moves the file to the trasg directory.
				System.out.println("File was moved to trash with the name " + toTrash.getName() + ". (" + toTrash.getPath() + ")");

		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("Please enter a file name.");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
