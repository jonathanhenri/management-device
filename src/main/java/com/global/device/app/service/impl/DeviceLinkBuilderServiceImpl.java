package com.global.device.app.service.impl;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.global.device.app.api.DeviceController;
import com.global.device.app.model.DeviceHateoas;
import com.global.device.app.service.DeviceLinkBuilderService;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Service;

@Service
public class DeviceLinkBuilderServiceImpl implements DeviceLinkBuilderService {
	
	@Override
	public void addLinks(DeviceHateoas device) {
		if (device.getIdentifier() != null) {
			Link selfLink = linkTo(
					methodOn(DeviceController.class).getDeviceByIdentifier(device.getIdentifier())).withSelfRel();
			Link updateLink = linkTo(
					methodOn(DeviceController.class).updateDevice(device.getIdentifier(), null)).withRel(
							"updateFull");
			Link deleteLink = linkTo(methodOn(DeviceController.class).deleteDevice(device.getIdentifier())).withRel(
					"delete");
			
			Link getByBrand = linkTo(
					methodOn(DeviceController.class).getDeviceByBrand(device.getBrand())).withSelfRel();
			
			device.add(selfLink, updateLink, deleteLink, getByBrand);
		}
	}
	
}
