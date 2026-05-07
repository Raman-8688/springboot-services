package com.learn.bank_crud_app.repository;

import com.learn.bank_crud_app.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer,Long> {
}
