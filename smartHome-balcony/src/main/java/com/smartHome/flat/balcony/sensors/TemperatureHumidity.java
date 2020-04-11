package com.smartHome.flat.balcony.sensors;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pi4j.system.SystemInfo;
import com.smartHome.flat.balcony.model.DataResponse;

/**
 * This example code demonstrates how to perform sensor state
 * control of a GPIO pin on the Raspberry Pi.
 *
 * @author Jan Pojezdala
 */
public class TemperatureHumidity {
	
	DataResponse dataResponse = new DataResponse();

	private final Logger log = LoggerFactory.getLogger(TemperatureHumidity.class);

    public DataResponse main() throws Exception {
    	
        final NumberFormat NF = new DecimalFormat("##00.00");
		BMP180 sensor = new BMP180();
		float press = 0;
		float temp = 0;
		double alt = 0;

		try {
			press = sensor.readPressure();
			dataResponse.setPress(press / 100);
		} catch (Exception ex) {
			System.err.println(ex.getMessage());
			ex.printStackTrace();
		}
		sensor.setStandardSeaLevelPressure((int) press); // As we ARE at the sea level (in San Francisco).
		try {
			alt = sensor.readAltitude();
			dataResponse.setAlt(alt);
		} catch (Exception ex) {
			System.err.println(ex.getMessage());
			ex.printStackTrace();
		}
		try {
			temp = sensor.readTemperature();
			dataResponse.setTemperature(temp);
		} catch (Exception ex) {
			System.err.println(ex.getMessage());
			ex.printStackTrace();
		}
		String lineSeparator = System.getProperty("line.separator");
		log.info("<TemperatureHumidity> Temperature: " + NF.format(temp) + " C" + lineSeparator + "Pressure   : " + NF.format(press / 100) + " hPa"
				+ lineSeparator + "Altitude   : " + NF.format(alt) + " m" + lineSeparator + "CPU Temperature   :  "
				+ SystemInfo.getCpuTemperature() + lineSeparator + "CPU Core Voltage  :  " + SystemInfo.getCpuVoltage());
		return dataResponse;	
    }
}