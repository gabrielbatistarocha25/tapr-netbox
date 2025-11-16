package br.com.netbox.infrastructureservice.adapter.input.web;

import br.com.netbox.infrastructureservice.domain.model.IpAddress;
import br.com.netbox.infrastructureservice.domain.model.Prefix;
import br.com.netbox.infrastructureservice.domain.port.input.IpamUseCase;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class IpamController {

    private final IpamUseCase ipamUseCase;

    public IpamController(IpamUseCase ipamUseCase) {
        this.ipamUseCase = ipamUseCase;
    }

    @PostMapping("/prefixes")
    public ResponseEntity<Prefix> createPrefix(@Valid @RequestBody Prefix prefix) {
        Prefix createdPrefix = ipamUseCase.createPrefix(prefix);
        return new ResponseEntity<>(createdPrefix, HttpStatus.CREATED);
    }

    @GetMapping("/prefixes")
    public ResponseEntity<List<Prefix>> getAllPrefixes(@RequestParam(required = false) Long siteId) {
        if (siteId != null) {
            return ResponseEntity.ok(ipamUseCase.getPrefixesBySite(siteId));
        }
        return ResponseEntity.ok(ipamUseCase.getAllPrefixes());
    }

    @GetMapping("/prefixes/{id}")
    public ResponseEntity<Prefix> getPrefixById(@PathVariable Long id) {
        return ResponseEntity.ok(ipamUseCase.getPrefixById(id));
    }

    @PutMapping("/prefixes/{id}")
    public ResponseEntity<Prefix> updatePrefix(@PathVariable Long id, @Valid @RequestBody Prefix prefix) {
        return ResponseEntity.ok(ipamUseCase.updatePrefix(id, prefix));
    }

    @DeleteMapping("/prefixes/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePrefix(@PathVariable Long id) {
        ipamUseCase.deletePrefix(id);
    }

    @PostMapping("/ip-addresses")
    public ResponseEntity<IpAddress> createIpAddress(@Valid @RequestBody IpAddress ipAddress) {
        IpAddress createdIp = ipamUseCase.createIpAddress(ipAddress);
        return new ResponseEntity<>(createdIp, HttpStatus.CREATED);
    }

    @GetMapping("/ip-addresses")
    public ResponseEntity<List<IpAddress>> getAllIpAddresses(@RequestParam(required = false) Long prefixId) {
        if (prefixId != null) {
            return ResponseEntity.ok(ipamUseCase.getIpAddressesByPrefix(prefixId));
        }
        return ResponseEntity.ok(ipamUseCase.getAllIpAddresses());
    }

    @GetMapping("/ip-addresses/{id}")
    public ResponseEntity<IpAddress> getIpAddressById(@PathVariable Long id) {
        return ResponseEntity.ok(ipamUseCase.getIpAddressById(id));
    }

    @PutMapping("/ip-addresses/{id}")
    public ResponseEntity<IpAddress> updateIpAddress(@PathVariable Long id, @Valid @RequestBody IpAddress ipAddress) {
        return ResponseEntity.ok(ipamUseCase.updateIpAddress(id, ipAddress));
    }

    @DeleteMapping("/ip-addresses/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteIpAddress(@PathVariable Long id) {
        ipamUseCase.deleteIpAddress(id);
    }
}