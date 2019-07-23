import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.junit.Assert;
import org.junit.Test;

public class InteractiveCommandLineTest {
	
	@Test
	public void copyAndMoveValidationDestTest() {
		String newPath = "src\\TestResources";
		File file = new File(newPath);
		Assert.assertTrue(InteractiveCommandLine.copyAndMoveValidationDest(file));
	}

	@Test
	public void copyAndMoveValidationDestTestAboutNonExistingFolder() {
		String newPath = "src\\TestResources\\f4";
		File file = new File(newPath);
		Assert.assertFalse(InteractiveCommandLine.copyAndMoveValidationDest(file));
	}

	@Test
	public void copyAndMoveValidationDestTestAboutDestinationThatIsNotFolder() {
		String newPath = "src\\TestResources\\t1.txt";
		File file = new File(newPath);
		Assert.assertFalse(InteractiveCommandLine.copyAndMoveValidationDest(file));
	}

	@Test
	public void copyAndMoveValidationSourceTest() {
		String newPath = "src\\TestResources\\t1.txt";
		File file = new File(newPath);
		Assert.assertTrue(InteractiveCommandLine.copyAndMoveValidationSource(file));
	}

	@Test
	public void copyAndMoveValidationSourceTestAboutNonExistingFile() {
		String newPath = "src\\TestResources\\t11.txt";
		File file = new File(newPath);
		Assert.assertFalse(InteractiveCommandLine.copyAndMoveValidationSource(file));
	}

	@Test
	public void copyAndMoveValidationSourceTestAboutPathForFolder() {
		String newPath = "src\\TestResources";
		File file = new File(newPath);
		// System.out.println(file.getAbsolutePath());
		Assert.assertTrue(InteractiveCommandLine.copyAndMoveValidationSource(file));
	}

	@Test
	public void relativePathValidationTest() {
		String newPath = "src\\TestResources\\t1.txt";
		Assert.assertTrue(InteractiveCommandLine.relativePathValidation(newPath).contains(":"));
	}

	@Test
	public void copyTest() {
		String sourcePath = "src\\TestResources\\t1.txt";
		String destPath = "src\\TestResources\\f1";
		try {
			InteractiveCommandLine.copy(sourcePath, destPath);
		} catch (IOException e) {
			e.printStackTrace();
		}
		String newPath = "src\\TestResources\\f1\\t1.txt";
		Assert.assertTrue(new File(newPath).exists());
		new File(newPath).delete();
	}

	@Test
	public void changeDirTest() {
		String newPath = "src\\TestResources";
		File file = new File(newPath);
		String absPath = file.getAbsolutePath();
		InteractiveCommandLine.changeDir(absPath);
		Assert.assertTrue(InteractiveCommandLine.path.equals(absPath));
	}

	@Test
	public void changeDirTestAboutRelativePath() {
		String newPath = "src\\TestResources";
		File file = new File(newPath);
		String absPath = file.getAbsolutePath();
		InteractiveCommandLine.changeDir(newPath);
		// System.out.println(InteractiveCommandLine.path+"\n"+absPath);
		Assert.assertTrue(InteractiveCommandLine.path.equals(absPath));
	}

	@Test
	public void changeDirTestAboutNonDirectoryPath() {
		String newPath = "src\\TestResources\\t1.txt";
		File file = new File(newPath);
		String absPath = file.getAbsolutePath();
		InteractiveCommandLine.changeDir(newPath);
		// System.out.println(InteractiveCommandLine.path+"\n"+absPath);
		Assert.assertFalse(InteractiveCommandLine.path.equals(absPath));
	}

	@Test
	public void propertiesTest() {
		String newPath = "src\\TestResources\\t1.txt";
		File file = new File(newPath);
		String absPath = file.getAbsolutePath();
		StringBuilder properties = new StringBuilder("Path: " + absPath + "\n" + "Size: 37 bytes\n" + "Format: txt\n");
		// System.out.println(InteractiveCommandLine.properties(absPath)+"\n"+properties);
		Assert.assertTrue(InteractiveCommandLine.properties(absPath).equals(properties.toString()));
	}

	@Test
	public void propertiesTestAboutNonExistingFile() {
		String newPath = "src\\TestResources\\t11.txt";
		File file = new File(newPath);
		String absPath = file.getAbsolutePath();
		Assert.assertTrue(InteractiveCommandLine.properties(absPath)
				.equals("File doesn't exist or the name is invalid.(names can't contain spaces)"));
	}

	@Test
	public void propertiesTestAboutDirectory() {
		String newPath = "src\\TestResources";
		File file = new File(newPath);
		String absPath = file.getAbsolutePath();
		Assert.assertTrue(InteractiveCommandLine.properties(absPath).equals("This is NOT a file"));
	}

	@Test
	public void listTest() {
		String newPath = "src\\TestResources";
		InteractiveCommandLine.changeDir(newPath);
		StringBuilder output = new StringBuilder();
		String[] files = new File(newPath).list();
		for (String file : files) {
			output.append(new File(newPath + "\\" + file).getAbsolutePath() + "\n");
		}
		// System.out.println(InteractiveCommandLine.list()+"\n"+output);
		Assert.assertTrue(InteractiveCommandLine.list().equals(output.toString()));
	}
	
	@Test
	public void listTestAboutEmptyDir() {
		String newPath = "src\\TestResources\\f2\\f4";
		InteractiveCommandLine.changeDir(newPath);
		// System.out.println(InteractiveCommandLine.list()+"\n"+output);
		Assert.assertTrue(InteractiveCommandLine.list().equals("Directory is empty"));
	}

	@Test
	public void moveTest(){
		String sourcePath = "src\\TestResources\\t1.txt";
		String destPath = "src\\TestResources\\f1";
		try {
			InteractiveCommandLine.move(sourcePath, destPath);
		} catch (IOException e) {
			e.printStackTrace();
		}
		String newPath = "src\\TestResources\\f1\\t1.txt";
		Assert.assertTrue(new File(newPath).exists());
		try {
			InteractiveCommandLine.move(newPath, "src\\TestResources");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
