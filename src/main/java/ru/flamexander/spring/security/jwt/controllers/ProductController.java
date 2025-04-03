package ru.flamexander.spring.security.jwt.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.flamexander.spring.security.jwt.entities.Product;
import ru.flamexander.spring.security.jwt.repositories.ProductRepository;
import ru.flamexander.spring.security.jwt.service.ProductService;

import javax.validation.Valid;
import java.util.List;

@Controller
public class ProductController {

    private final ProductRepository productRepository;

    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

//    @GetMapping("/catalog")
//    public String showCatalog(Model model) {
//        List<Product> products = productRepository.findAll(); // Убедитесь, что вызывается
//        model.addAttribute("products", products); // Проверьте название атрибута ("products")
//        return "catalog";
//    }
@GetMapping("/catalog")
public String showCatalog(@RequestParam(required = false) String category, Model model) {
    List<Product> products;
    if (category != null && !category.isEmpty()) {
        products = productRepository.findByCategory(category);
    } else {
        products = productRepository.findAll();
    }
    model.addAttribute("products", products);
    return "catalog";
}

    @GetMapping("/add-product")
    public String showAddProductForm(Model model) {
        model.addAttribute("product", new Product());
        return "add-product";
    }

    @PostMapping("/add-product")
    public String addProduct(@ModelAttribute Product product) {
        productRepository.save(product);
        return "redirect:/catalog";
    }

    @GetMapping("/edit-product/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid product Id:" + id));
        model.addAttribute("product", product);
        return "edit-product";
    }

    @PostMapping("/update-product/{id}")
    public String updateProduct(@PathVariable Long id, @ModelAttribute Product product) {
        product.setId(id);
        productRepository.save(product);
        return "redirect:/catalog";
    }

    @GetMapping("/delete-product/{id}")
    public String deleteProduct(@PathVariable Long id) {
        productRepository.deleteById(id);
        return "redirect:/catalog";
    }
}

