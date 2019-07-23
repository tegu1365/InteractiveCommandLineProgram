import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class InteractiveCommandLine {
	static String command = new String();
	static String path = "C:\\";

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
				System.out.println("The destination isn't a directory");
			}
		} else {
			System.out
					.println("The destination file doesn't exist or the name is invalid.(names can't contain spaces)");
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

	static String relativePathValidation(String source) {
		File file = new File(source);
		return file.getAbsolutePath();
	}

	static void copy(String source, String destination) throws IOException {// copies file from source to destination
		source = relativePathValidation(source);
		destination = relativePathValidation(destination);
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

	static String list() {// lists all files and sub-dir in the current dir
		File[] files = new File(path).listFiles();
		StringBuilder output = new StringBuilder();
		if (files.length != 0) {
			for (File file : files) {
				output.append(file.getPath() + "\n");
			}
		} else {
			output.append("Directory is empty");
		}
		return output.toString();
	}

	static void move(String source, String destination) throws IOException {// moves file from source to destination
		source = relativePathValidation(source);
		destination = relativePathValidation(destination);
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

	static String properties(String file) {// lists file properties
		file = relativePathValidation(file);
		File source = new File(file);
		StringBuilder output = new StringBuilder();
		if (source.exists()) {
			if (source.isFile()) {
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

				// System.out.println(output);
			} else {
				output.append("This is NOT a file");
			}
		} else {
			output.append("File doesn't exist or the name is invalid.(names can't contain spaces)");
		}
		return output.toString();
	}

	static void changeDir(String dir) {// changes current dir path to submitted by the user dir path
		dir = relativePathValidation(dir);
		if (!dir.equals("")) {
			if (new File(dir).exists()) {
				if (new File(dir).isDirectory()) {
					path = dir;
				} else {
					System.out.println("The path isn't a directory");
				}
			} else {
				System.out.println("Directory doesn't exist or the name is invalid.(names can't contain spaces)");
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
				System.out.print(list());
				break;
			case "move":
				try {
					move(cm[1], cm[2]);
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			case "properties":
				System.out.print(properties(cm[1]));
				break;
			default:
				changeDir(command);
				break;
			}

			input();
		}
	}

}
