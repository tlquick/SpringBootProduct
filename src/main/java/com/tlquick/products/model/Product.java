package com.tlquick.products.model;

import org.springframework.lang.NonNull;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class Product {
@Id
@GeneratedValue(strategy = GenerationType.AUTO)
private int id;
@NonNull
private String name;
@NonNull
private String description;
@NonNull
private double price;
public Product(String name, String description, double price ) {
	super();
	this.name = name;
	this.description = description;
	this.price = price;
}
}
