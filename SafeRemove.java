import java.io.File;

public class SafeRemove {

	public static void main(String[] args) {
		if (args[0].equals("")) {
			System.out.println("Please enter a file path.");
			System.exit(0);
		}

		try {
			File file = new File(args[0]);

			if (file.exists() == false) {
				System.out.println(file.getPath() + " does not exist.");
				System.exit(0);
			}

			File trash = new File("/home/christophrrb/.local/share/Trash/files/" + file.getName());
			String trashPath = trash.getPath();

			for (int i = 1; trash.exists() && i <= 100; i++) {
				String newFileName = trashPath + "(" + i + ")";

				trash = new File(newFileName);
			}


				file.renameTo(trash);
				System.out.println("File was moved to trash with the name " + trash.getName() + ". (" + trash.getPath() + ")");

		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("Please enter a file.");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
