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

    @GetMapping("/manufacturers/{id}")
    public ResponseEntity<Manufacturer> getManufacturerById(@PathVariable Long id) {
        return ResponseEntity.ok(inventoryUseCase.getManufacturerById(id));
    }

    @PutMapping("/manufacturers/{id}")
    public ResponseEntity<Manufacturer> updateManufacturer(@PathVariable Long id, @Valid @RequestBody Manufacturer manufacturer) {
        return ResponseEntity.ok(inventoryUseCase.updateManufacturer(id, manufacturer));
    }

    @DeleteMapping("/manufacturers/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteManufacturer(@PathVariable Long id) {
        inventoryUseCase.deleteManufacturer(id);
    }

    @PostMapping("/device-models")
    public ResponseEntity<DeviceModel> createDeviceModel(@Valid @RequestBody DeviceModel deviceModel) {
        return new ResponseEntity<>(inventoryUseCase.createDeviceModel(deviceModel), HttpStatus.CREATED);
    }

    @GetMapping("/device-models")
    public ResponseEntity<List<DeviceModel>> getAllDeviceModels() {
        return ResponseEntity.ok(inventoryUseCase.getAllDeviceModels());
    }

    @GetMapping("/device-models/{id}")
    public ResponseEntity<DeviceModel> getDeviceModelById(@PathVariable Long id) {
        return ResponseEntity.ok(inventoryUseCase.getDeviceModelById(id));
    }

    @PutMapping("/device-models/{id}")
    public ResponseEntity<DeviceModel> updateDeviceModel(@PathVariable Long id, @Valid @RequestBody DeviceModel deviceModel) {
        return ResponseEntity.ok(inventoryUseCase.updateDeviceModel(id, deviceModel));
    }

    @DeleteMapping("/device-models/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteDeviceModel(@PathVariable Long id) {
        inventoryUseCase.deleteDeviceModel(id);
    }

    @PostMapping("/devices")
    public ResponseEntity<Device> createDevice(@Valid @RequestBody Device device) {
        return new ResponseEntity<>(inventoryUseCase.createDevice(device), HttpStatus.CREATED);
    }

    @GetMapping("/devices")
    public ResponseEntity<List<Device>> getAllDevices() {
        return ResponseEntity.ok(inventoryUseCase.getAllDevices());
    }

    @GetMapping("/devices/{id}")
    public ResponseEntity<Device> getDeviceById(@PathVariable Long id) {
        return ResponseEntity.ok(inventoryUseCase.getDeviceById(id));
    }

    @PutMapping("/devices/{id}")
    public ResponseEntity<Device> updateDevice(@PathVariable Long id, @Valid @RequestBody Device device) {
        return ResponseEntity.ok(inventoryUseCase.updateDevice(id, device));
    }

    @DeleteMapping("/devices/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteDevice(@PathVariable Long id) {
        inventoryUseCase.deleteDevice(id);
    }


    @GetMapping("/sites/{siteId}/devices")
    public ResponseEntity<List<Device>> getDevicesBySite(@PathVariable Long siteId) {
        List<Device> devices = inventoryUseCase.getDevicesBySite(siteId);
        return ResponseEntity.ok(devices);
    }
}