package com.sam.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import com.fasterxml.jackson.databind.ser.Serializers;
import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Product extends BaseEntity {



    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private int price;

    @Column(name = "discounted_price")
    private int discountedPrice;
    
    @Column(name="discount_persent")
    private int discountPersent;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "brand")
    private String brand;

    @Column(name = "color")
    private String color;

	@ElementCollection
	@CollectionTable(name = "product_sizes", joinColumns = @JoinColumn(name = "product_id"))
	@Column(name = "size")
	private Set<Size> sizes = new HashSet<>();

    @Column(name = "image_url")
    private String imageUrl;

    @OneToMany(mappedBy = "product",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Rating>ratings=new ArrayList<>();
    
    @OneToMany(mappedBy = "product",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Review>reviews=new ArrayList<>();

    @Column(name = "num_ratings")
    private int numRatings;
    

    @ManyToOne()
    @JoinColumn(name="category_id")
    private Category category;


	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Product product = (Product) o;
		return price == product.price && discountedPrice == product.discountedPrice && discountPersent == product.discountPersent && quantity == product.quantity && numRatings == product.numRatings && Objects.equals(title, product.title) && Objects.equals(description, product.description) && Objects.equals(brand, product.brand) && Objects.equals(color, product.color) && Objects.equals(sizes, product.sizes) && Objects.equals(imageUrl, product.imageUrl) && Objects.equals(ratings, product.ratings) && Objects.equals(reviews, product.reviews) && Objects.equals(category, product.category);
	}

	@Override
	public int hashCode() {
		return Objects.hash(title, description, price, discountedPrice, discountPersent, quantity, brand, color, sizes, imageUrl, ratings, reviews, numRatings, category);
	}
}
