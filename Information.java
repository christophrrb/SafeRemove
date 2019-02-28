public class Information implements java.io.Serializable {
	
	private String trashDirectory;

	public Information(String trashDirectory) {
		this.trashDirectory = trashDirectory;
	}

	public String getTrashDirectory() {
		return trashDirectory;
	}

	public void setTrashDirectory(String trashDirectory) {
		this.trashDirectory = trashDirectory;
		System.out.println("Trash directory changed to " + trashDirectory);
	}

}
