package wdframework.action.fileupload;

import java.io.File;

import wdframework.action.Action;
import wdframework.logger.Logger;

/**
 * File Upload Action Class
 * @author Eldo Rajan
 *
 */
public class FileUploadAction extends Action{

	/**
	 * File upload function for chrome with autoit
	 * @param fileName
	 */
	public void autoitFileUploadChrome(String fileName){
		Logger.info("Started the method:"+Thread.currentThread().getStackTrace()[1].getMethodName());

		File fileLocation   =  new File(System.getProperty("user.dir")+File.separator+"src/test/resources/data");
		String absolutePath =   fileLocation.getAbsolutePath() + "\\" + fileName;
		File exeLocation 	= new File("src/test/resources/utils/fileuploadchrome.exe");
		String exeAbsolutePath  =  exeLocation.getAbsolutePath().replace("\\", "\\\\");                    
		try {
			new ProcessBuilder(exeAbsolutePath,
					absolutePath, "Open").start();
			Thread.sleep(10000);
		} catch (Exception e) {
			Logger.info("Failed the method:"+Thread.currentThread().getStackTrace()[1].getMethodName()+" "+e.toString());
		}
		Logger.info("Completed the method:"+Thread.currentThread().getStackTrace()[1].getMethodName());
	}

	/**
	 * File upload function for firefox with autoit
	 * @param fileName
	 */
	public void autoitFileUploadFirefox(String fileName){
		Logger.info("Started the method:"+Thread.currentThread().getStackTrace()[1].getMethodName());
		
		File fileLocation   =  new File(System.getProperty("user.dir")+File.separator+"src/test/resources/data");
		String absolutePath =   fileLocation.getAbsolutePath() + "\\" + fileName;
		File exeLocation 	= new File("src/test/resources/utils/fileuploadfirefox.exe");
		String exeAbsolutePath  =  exeLocation.getAbsolutePath().replace("\\", "\\\\");                    
		try {
			new ProcessBuilder(exeAbsolutePath,
					absolutePath, "Open").start();
			Thread.sleep(10000);
		} catch (Exception e) {
			Logger.info("Failed the method:"+Thread.currentThread().getStackTrace()[1].getMethodName()+" "+e.toString());
		}
		Logger.info("Completed the method:"+Thread.currentThread().getStackTrace()[1].getMethodName());
	}

}
