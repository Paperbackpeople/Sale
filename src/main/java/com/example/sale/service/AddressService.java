package com.example.sale.service;

import com.example.sale.entity.Address;
import com.example.sale.mapper.AddressMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AddressService {

    private final AddressMapper addressMapper;

    @Autowired
    public AddressService(AddressMapper addressMapper) {
        this.addressMapper = addressMapper;
    }

    // 保存地址
    @Transactional
    public Address saveAddress(String email, Address address) {
        Long userId = addressMapper.getUserIdByEmail(email);
        if (userId == null) {
            throw new IllegalArgumentException("User not found with email: " + email);
        }

        // 如果该地址为默认地址，先清除其他默认地址
        if (address.isPrimary()) {
            addressMapper.clearDefaultAddress(userId);
        }

        address.setUserId(userId);
        addressMapper.insertAddress(address);
        return address;
    }

    // 获取默认地址
    public Address getDefaultAddress(String email) {
        Long userId = addressMapper.getUserIdByEmail(email);
        if (userId == null) {
            throw new IllegalArgumentException("User not found with email: " + email);
        }

        return addressMapper.getDefaultAddress(userId);
    }

    // 获取所有地址
    public List<Address> getAllAddresses(String email) {
        Long userId = addressMapper.getUserIdByEmail(email);
        if (userId == null) {
            throw new IllegalArgumentException("User not found with email: " + email);
        }

        return addressMapper.getAllAddresses(userId);
    }

    // 设置默认地址
    @Transactional
    public void setDefaultAddress(String email, Long addressId) {
        Long userId = addressMapper.getUserIdByEmail(email);
        if (userId == null) {
            throw new IllegalArgumentException("User not found with email: " + email);
        }

        addressMapper.setDefaultAddress(addressId);
    }

    public Address getAddressById(String email, Long addressId) {
        Long userId = addressMapper.getUserIdByEmail(email);
        if (userId == null) {
            throw new IllegalArgumentException("User not found with email: " + email);
        }

        Address address = addressMapper.getAddressById(addressId);
        // 确保地址属于当前用户
        if (address != null && address.getUserId().equals(userId)) {
            return address;
        } else {
            return null; // 或者抛出异常
        }
    }

    @Transactional
    public Address updateAddress(String email, Address address) {
        Long userId = addressMapper.getUserIdByEmail(email);
        if (userId == null) {
            throw new IllegalArgumentException("User not found with email: " + email);
        }

        // 确保要更新的地址属于当前用户
        Address existingAddress = addressMapper.getAddressById(address.getAddressId());
        if (existingAddress == null || !existingAddress.getUserId().equals(userId)) {
            throw new IllegalArgumentException("Address not found or does not belong to the user.");
        }

        // 如果更新的地址设置为默认地址，清除其他默认地址
        if (address.isPrimary()) {
            addressMapper.clearDefaultAddress(userId);
        }

        address.setUserId(userId);
        addressMapper.updateAddress(address);
        return address;
    }
}