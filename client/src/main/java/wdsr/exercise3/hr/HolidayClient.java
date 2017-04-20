package wdsr.exercise3.hr;

import java.net.URL;
import java.util.Date;

import wdsr.exercise3.ws.EmployeeType;
import wdsr.exercise3.ws.HolidayRequest;
import wdsr.exercise3.ws.HolidayType;
import wdsr.exercise3.ws.HumanResource;
import wdsr.exercise3.ws.HumanResourceService;

// TODO Complete this class to book holidays by issuing a request to Human Resource web service.
// In order to see definition of the Human Resource web service:
// 1. Run HolidayServerApp.
// 2. Go to http://localhost:8090/holidayService/?wsdl
public class HolidayClient {
	private final HumanResource port;

	/**
	 * Creates this object
	 * 
	 * @param wsdlLocation
	 *            URL of the Human Resource web service WSDL
	 */
	public HolidayClient(URL wsdlLocation) {
		port = new HumanResourceService(wsdlLocation).getHumanResourcePort();
	}

	/**
	 * Sends a holiday request to the HumanResourceService.
	 * 
	 * @param employeeId
	 *            Employee ID
	 * @param firstName
	 *            First name of employee
	 * @param lastName
	 *            Last name of employee
	 * @param startDate
	 *            First day of the requested holiday
	 * @param endDate
	 *            Last day of the requested holiday
	 * @return Identifier of the request, if accepted.
	 * @throws ProcessingException
	 *             if request processing fails.
	 */
	public int bookHoliday(int employeeId, String firstName, String lastName, Date startDate, Date endDate)
			throws ProcessingException {

		EmployeeType employeeType = new EmployeeType(employeeId, firstName, lastName);
		HolidayType holidayType = new HolidayType(startDate, endDate);

		HolidayRequest holidayRequest = new HolidayRequest(holidayType, employeeType);

		try {
			return port.holiday(holidayRequest).getRequestId();
		} catch (RuntimeException e) {
			throw new ProcessingException(e);
		}
	}
}
