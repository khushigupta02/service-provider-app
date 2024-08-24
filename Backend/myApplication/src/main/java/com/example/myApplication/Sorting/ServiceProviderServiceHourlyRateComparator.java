package com.example.myApplication.Sorting;

import java.io.Serializable;
import java.util.Comparator;

import com.example.myApplication.Entity.ServiceProviderService;
import com.example.myApplication.Enum.Order;

public class ServiceProviderServiceHourlyRateComparator implements Comparator<ServiceProviderService>, Serializable {
	private final Order order;

	public ServiceProviderServiceHourlyRateComparator(Order order) {
		this.order = order;
	}

	@Override
	public int compare(ServiceProviderService service1, ServiceProviderService service2) {
		double hourlyRate1 = service1.getService().getHourlyRate();
		double hourlyRate2 = service2.getService().getHourlyRate();

		// Compare based on the order (ASC or DESC)
		if (order == Order.ASC) {
			return Double.compare(hourlyRate1, hourlyRate2);
		} else if (order == Order.DESC) {
			return Double.compare(hourlyRate2, hourlyRate1); // Reverse comparison for descending order
		}
		return 0;
	}
}