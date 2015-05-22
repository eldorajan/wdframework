package wd.common;

import wdframework.action.SearchAction;
import wdframework.action.onedrive.OneDriveAction;
import wdframework.common.DriverRunner;

/**
 * Common Method in Test Suite Level
 * @author erajan
 *
 */
public class CommonTest extends DriverRunner {
	public SearchAction sa = new SearchAction();
	public OneDriveAction onedrive = new OneDriveAction();
}
