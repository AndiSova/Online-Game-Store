package com.ssn.aso.common;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Game {
	@Id
	@GeneratedValue
	private Long id;

	private String name;
	private double price;
	private String image;

	@SuppressWarnings("unused")
	private Game() {
	}

	public Game(String name) {
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getGameName() {
		return name;
	}

	public void setGameName(String productname) {
		this.name = productname;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	@Override
	public String toString() {
		return "Product [productname=" + name + ", price=" + price + "]";
	}

}