package br.com.netbox.inventoryservice.adapter.input.web;

import br.com.netbox.inventoryservice.domain.model.Device;
import br.com.netbox.inventoryservice.domain.model.DeviceModel;
import br.com.netbox.inventoryservice.domain.model.Manufacturer;
import br.com.netbox.inventoryservice.domain.port.input.InventoryUseCase;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class DeviceController {

    private final InventoryUseCase inventoryUseCase;

    public DeviceController(InventoryUseCase inventoryUseCase) {
        this.inventoryUseCase = inventoryUseCase;
    }

    @PostMapping("/manufacturers")
    public ResponseEntity<Manufacturer> createManufacturer(@Valid @RequestBody Manufacturer manufacturer) {
        return new ResponseEntity<>(inventoryUseCase.createManufacturer(manufacturer), HttpStatus.CREATED);
    }

    @GetMapping("/manufacturers")
    public ResponseEntity<List<Manufacturer>> getAllManufacturers() {
        return ResponseEntity.ok(inventoryUseCase.getAllManufacturers());
    }

    @PostMapping("/device-models")
    public ResponseEntity<DeviceModel> createDeviceModel(@Valid @RequestBody DeviceModel deviceModel) {
        return new ResponseEntity<>(inventoryUseCase.createDeviceModel(deviceModel), HttpStatus.CREATED);
    }

    @GetMapping("/device-models")
    public ResponseEntity<List<DeviceModel>> getAllDeviceModels() {
        return ResponseEntity.ok(inventoryUseCase.getAllDeviceModels());
    }

    @PostMapping("/devices")
    public ResponseEntity<Device> createDevice(@Valid @RequestBody Device device) {
        return new ResponseEntity<>(inventoryUseCase.createDevice(device), HttpStatus.CREATED);
    }

    @GetMapping("/devices")
    public ResponseEntity<List<Device>> getAllDevices() {
        return ResponseEntity.ok(inventoryUseCase.getAllDevices());
    }

    @GetMapping("/sites/{siteId}/devices")
    public ResponseEntity<List<Device>> getDevicesBySite(@PathVariable Long siteId) {
        List<Device> devices = inventoryUseCase.getDevicesBySite(siteId);
        return ResponseEntity.ok(devices);
    }
}