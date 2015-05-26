package wd.common;

import wdframework.action.SearchAction;
import wdframework.action.dropbox.DropBoxAction;
import wdframework.action.googledrive.GoogleDriveAction;
import wdframework.action.onedrive.OneDriveAction;
import wdframework.common.DriverRunner;

/**
 * Common Method in Test Suite Level
 * @author Eldo Rajan
 *
 */
public class CommonTest extends DriverRunner {
	public SearchAction sa = new SearchAction();
	public OneDriveAction onedrive = new OneDriveAction();
	public GoogleDriveAction googledrive = new GoogleDriveAction();
	public DropBoxAction dropbox = new DropBoxAction();
}
