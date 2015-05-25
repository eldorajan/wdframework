package wdframework.driver;


public enum DriverType {
	Local("local"),
	Grid("grid"),
	Cloud("cloud");

	public static DriverType getDriverType(final String driverType) {
		DriverType dType=null;
		try{
			if (driverType.equalsIgnoreCase("local")) {
				dType=DriverType.Local;
			} else if (driverType.equalsIgnoreCase("grid")) {
				dType=DriverType.Grid;
			} else if (driverType.equalsIgnoreCase("cloud")) {
				dType=DriverType.Cloud;
			} else {
				dType=DriverType.Local;
			}
		} catch (Exception e) {
			//e.printStackTrace();
		}
		return dType;
	}

	private String driverType;

	DriverType(final String type) {
		this.driverType = type;
	}

	public String getDriverType() {
		return this.driverType;
	}

}
