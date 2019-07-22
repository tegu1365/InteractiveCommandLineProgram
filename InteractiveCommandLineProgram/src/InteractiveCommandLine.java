import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class InteractiveCommandLine {
	static String command = new String();
	static String path = "C:/";;

	public static void main(String[] args) {
		input();
		chooser();
	}

	static void input() {
		Scanner scan = new Scanner(System.in);
		command = scan.nextLine();
	}

	static boolean copyAndMoveValidationDest(File target) {
		boolean exist = false;
		if (target.exists()) {
			if (target.isDirectory()) {
				exist = true;
			} else {
				System.out.println("The destination link isn't a directory");
			}
		} else {
			System.out.println("The destination file doesn't exist or the name is invalid.(names can't contain spaces)");
		}
		return exist;
	}

	static boolean copyAndMoveValidationSource(File source) {
		boolean exist = false;
		if (source.exists()) {
			exist = true;
		} else {
			System.out.println("The source file doesn't exist or the name is invalid.(names can't contain spaces)");
		}
		return exist;
	}

	static void copy(String source, String destination) throws IOException {// copies file from source to destination
		if (copyAndMoveValidationSource(new File(source)) && copyAndMoveValidationDest(new File(destination))) {
			Path sourceDirectory = Paths.get(source);
			Path targetDirectory;
			if (destination.endsWith("\\")) {
				targetDirectory = Paths.get(destination + sourceDirectory.toFile().getName());
			} else {
				targetDirectory = Paths.get(destination + "\\" + sourceDirectory.toFile().getName());
			}

			Path temp = Files.copy(sourceDirectory, targetDirectory);

			if (temp != null) {
				System.out.println("File copied successfully");
			} else {
				System.out.println("Failed to copy the file");
			}
		}
	}

	static void list() {// lists all files and sub-dir in the current dir
		File[] files = new File(path).listFiles();
		if (files != null) {
			for (File file : files) {
				System.out.println(file.getPath());
			}
		} else {
			System.out.println("File Not Found");
		}
	}

	static void move(String source, String destination) throws IOException {// moves file from source to destination
		if (copyAndMoveValidationSource(new File(source)) && copyAndMoveValidationDest(new File(destination))) {
			Path sourceDirectory = Paths.get(source);
			Path targetDirectory;
			if (destination.endsWith("\\")) {
				targetDirectory = Paths.get(destination + sourceDirectory.toFile().getName());
			} else {
				targetDirectory = Paths.get(destination + "\\" + sourceDirectory.toFile().getName());
			}

			Path temp = Files.move(sourceDirectory, targetDirectory);

			if (temp != null) {
				System.out.println("File is moved successfully");
			} else {
				System.out.println("Failed to move the file");
			}
		}
	}

	static void properties(String file) {// lists file properties
		File source = new File(file);
		if (source.exists()) {
			StringBuilder output = new StringBuilder();
			Path filePath = source.toPath();
			output.append("Path: " + source.getAbsolutePath() + "\n");

			try {
				output.append("Size: " + Files.size(filePath) + " bytes\n");
			} catch (IOException e) {
				e.printStackTrace();
			}
			String extension = "";

			int i = file.lastIndexOf('.');
			if (i >= 0) {
				extension = file.substring(i + 1);
			}
			output.append("Format: " + extension + "\n");

			System.out.println(output);
		} else {
			System.out.println("File doesn't exist or the name is invalid.(names can't contain spaces)");
		}
	}

	static void changeDir(String dir) {// changes current dir path to submitted by the user dir path
		if (!dir.equals("")) {
			if (new File(dir).exists()) {
				if (new File(dir).isDirectory()) {
					path = dir;
				} else {
					System.out.println("The link isn't a directory");
				}
			} else {
				System.out.println("Directory doesn't exist ar the name is invalid.(names can't contain spaces)");
			}
		}

	}

	static void chooser() {
		while (!command.equals("exit")) {
			String[] cm = command.split(" ");

			switch (cm[0]) {
			case "copy":
				try {
					copy(cm[1], cm[2]);
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			case "ls":
				list();
				break;
			case "move":
				try {
					move(cm[1], cm[2]);
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			case "properties":
				properties(cm[1]);
				break;
			default:
				changeDir(command);
				break;
			}

			input();
		}
	}

}
