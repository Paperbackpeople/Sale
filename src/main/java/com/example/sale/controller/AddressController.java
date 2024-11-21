package com.example.sale.controller;

import com.example.sale.entity.Address;
import com.example.sale.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLOutput;
import java.util.List;

@RestController
@RequestMapping("/api/address")
public class AddressController {

    private final AddressService addressService;

    @Autowired
    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    // 保存地址
    @PostMapping("/save")
    public ResponseEntity<Address> saveAddress(@RequestParam String email, @RequestBody Address address) {
        Address savedAddress = addressService.saveAddress(email, address);
        return new ResponseEntity<>(savedAddress, HttpStatus.CREATED);
    }

    // 获取默认地址
    @GetMapping("/default")
    public ResponseEntity<String> getDefaultAddress(@RequestParam String email) {
        Address defaultAddress = addressService.getDefaultAddress(email);
        if (defaultAddress != null) {
            return ResponseEntity.ok(defaultAddress.getAddressLine() + ", Tel: " + defaultAddress.getTel());
        }
        return ResponseEntity.ok("No default address found");
    }

    // 获取所有地址
    @GetMapping("/all")
    public ResponseEntity<List<Address>> getAllAddresses(@RequestParam String email) {
        List<Address> addresses = addressService.getAllAddresses(email);
        return ResponseEntity.ok(addresses);
    }

    // 设置默认地址
    @PostMapping("/set-default")
    public ResponseEntity<Void> setDefaultAddress(@RequestParam String email, @RequestParam Long addressId) {
        addressService.setDefaultAddress(email, addressId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    // 获取单个地址
    @GetMapping("/{id}")
    public ResponseEntity<Address> getAddressById(@PathVariable("id") Long addressId, @RequestParam String email) {
        Address address = addressService.getAddressById(email, addressId);
        if (address != null) {
            return ResponseEntity.ok(address);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    // 更新地址
    @PostMapping("/update")
    public ResponseEntity<Address> updateAddress(@RequestParam String email, @RequestBody Address address) {
        Address updatedAddress = addressService.updateAddress(email, address);
        return new ResponseEntity<>(updatedAddress, HttpStatus.OK);
    }

}