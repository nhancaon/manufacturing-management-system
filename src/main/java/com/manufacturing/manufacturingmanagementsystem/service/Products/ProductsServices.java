package com.manufacturing.manufacturingmanagementsystem.service.Products;

import com.manufacturing.manufacturingmanagementsystem.dtos.ProductsDTO;
import com.manufacturing.manufacturingmanagementsystem.dtos.requests.Product.CreateProductRequest;
import com.manufacturing.manufacturingmanagementsystem.exceptions.ResourceNotFoundException;
import com.manufacturing.manufacturingmanagementsystem.models.BOMsEntity;
import com.manufacturing.manufacturingmanagementsystem.models.CategoriesEntity;
import com.manufacturing.manufacturingmanagementsystem.models.ProductsEntity;
import com.manufacturing.manufacturingmanagementsystem.models.SaleForecastDetailsEntity;
import com.manufacturing.manufacturingmanagementsystem.repositories.*;
import com.manufacturing.manufacturingmanagementsystem.service.OrderProductDetails.OrderProductDetailsServices;
import com.manufacturing.manufacturingmanagementsystem.service.SaleForecastDetails.SaleForecastDetailsServices;
import io.micrometer.common.util.StringUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
// Author: Pham Hien Nhan
// this class is used to implement the methods declared in the iProductsServices interface
@Service
@AllArgsConstructor
public class ProductsServices implements iProductsServices {

    private final ProductsRepository productsRepository;
    private final CategoriesRepository categoriesRepository;
    private final BOMsRepository bomsRepository;
    private final SaleForecastDetailsServices saleForecastDetailsServices;
    private final OrderProductDetailsServices orderProductDetailsServices;

    // this service is used to find a product by name
    @Override
    public ProductsEntity findProductbyName(String name) {
        if (StringUtils.isEmpty(name)) {
            return null;
        }
        return productsRepository.findFirstByName(name);
    }
    // this service is used to insert a new product
    @Override
    public void insertProduct(ProductsDTO productsDTO, Long bomID, Long categoryID) {
        ProductsEntity productEntity = new ProductsEntity();
        productEntity.setName(productsDTO.getName());
        productEntity.setUnit(productsDTO.getUnit());
        productEntity.setPrice(productsDTO.getPrice());
        productEntity.setVolume(productsDTO.getVolume());
        Optional<CategoriesEntity> categoryOptional = categoriesRepository.findById(categoryID);
        Optional<BOMsEntity> bomOptional = bomsRepository.findById(bomID);

        if (categoryOptional.isPresent() && bomOptional.isPresent()) {
            productEntity.setCategory(categoryOptional.get());
            productEntity.setBom(bomOptional.get());
        }
        productsRepository.save(productEntity);
    }
    // this service is used to get a product for sale forecast by id
    @Override
    public List<Map<String, Object>> getProductForSaleForecastById(Long id) {
        List<ProductsEntity> productsEntityList = productsRepository.findAll();
        List<Map<String, Object>> listSaleDetail = saleForecastDetailsServices.findSaleForecastDetailById(id);
        List<Map<String, Object>> productMap = new ArrayList<>();
        if (listSaleDetail.isEmpty()) {
            for (ProductsEntity product : productsEntityList) {
                Map<String, Object> productInfo = new HashMap<>();
                productInfo.put("id", product.getId());
                productInfo.put("name", product.getName());
                productInfo.put("price", product.getPrice());
                productInfo.put("sellPrice", product.getSellPrice());
                productMap.add(productInfo);
            }
        } else {
            Set<Long> saleDetailProductIds = listSaleDetail.stream()
                    .map(detail -> (Long) detail.get("product_id"))
                    .collect(Collectors.toSet());
            for (ProductsEntity product : productsEntityList) {
                if (!saleDetailProductIds.contains(product.getId())) {
                    Map<String, Object> productInfo = new HashMap<>();
                    productInfo.put("id", product.getId());
                    productInfo.put("name", product.getName());
                    productInfo.put("price", product.getPrice());
                    productInfo.put("sellPrice", product.getSellPrice());
                    productMap.add(productInfo);
                }
            }
        }
        return productMap;
    }
    // this service is used to get a product for order product by id
    @Override
    public List<Map<String, Object>> getProductForOrderProductById(Long id) {
        List<ProductsEntity> productsEntityList = productsRepository.findAll();
        List<Map<String, Object>> listOrderProductDetail = orderProductDetailsServices.findOrderProductDetailById(id);
        List<Map<String, Object>> productMap = new ArrayList<>();
        if (listOrderProductDetail.isEmpty()) {
            for (ProductsEntity product : productsEntityList) {
                Map<String, Object> productInfo = new HashMap<>();
                productInfo.put("id", product.getId());
                productInfo.put("name", product.getName());
                productInfo.put("price", product.getPrice());
                productInfo.put("sellPrice", product.getSellPrice());
                productMap.add(productInfo);
            }
        } else {
            Set<Long> orderProductDetailProductIds = listOrderProductDetail.stream()
                    .map(detail -> (Long) detail.get("product_id"))
                    .collect(Collectors.toSet());
            for (ProductsEntity product : productsEntityList) {
                if (!orderProductDetailProductIds.contains(product.getId())) {
                    Map<String, Object> productInfo = new HashMap<>();
                    productInfo.put("id", product.getId());
                    productInfo.put("name", product.getName());
                    productInfo.put("price", product.getPrice());
                    productInfo.put("sellPrice", product.getSellPrice());
                    productMap.add(productInfo);
                }
            }
        }
        return productMap;
    }
    // this service is used to get all products
    @Override
    public List<ProductsEntity> getAllProducts() {
        try {
            return productsRepository.findAll();
        } catch (Exception e) {
            System.out.println("Error get All product: " + e.getMessage());
            return null;
        }
    }
    // this service is used to update a product
    @Override
    public void updateProduct(ProductsDTO request) {
        Optional<ProductsEntity> productOptional = productsRepository.findById(request.getId());
        if (productOptional.isPresent()) {
            ProductsEntity product = productOptional.get();
            product.setName(request.getName());
            product.setUnit(request.getUnit());
            product.setPrice(request.getPrice());
            product.setSellPrice(request.getSellPrice());
            product.setVolume(request.getVolume());

            Optional<CategoriesEntity> categoryOptional = categoriesRepository.findById(request.getCategoryID());
            Optional<BOMsEntity> bomOptional = bomsRepository.findById(request.getBomID());

            if (categoryOptional.isPresent() && bomOptional.isPresent()) {
                product.setCategory(categoryOptional.get());
                product.setBom(bomOptional.get());
            }

            productsRepository.save(product);
        } else {
            throw new ResourceNotFoundException("Product with id " + request.getId() + " not found");
        }
    }
}
