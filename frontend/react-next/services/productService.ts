import api from './api';
import { Product, ProductRequest } from '@/types';

// Get all products
export const getAllProducts = async (): Promise<Product[]> => {
  const response = await api.get<Product[]>('/products');
  return response.data;
};

// Get product by SKU
export const getProductBySku = async (sku: string): Promise<Product> => {
  const response = await api.get<Product>(`/products/${sku}`);
  return response.data;
};

// Create new product (admin only)
export const createProduct = async (productData: ProductRequest): Promise<Product> => {
  const response = await api.post<Product>('/products', productData);
  return response.data;
};

// Update product (admin only)
export const updateProduct = async (sku: string, productData: ProductRequest): Promise<Product> => {
  const response = await api.put<Product>(`/products/${sku}`, productData);
  return response.data;
};

// Enable product (admin only)
export const enableProduct = async (sku: string): Promise<Product> => {
  const response = await api.patch<Product>(`/products/${sku}/enable`);
  return response.data;
};

// Disable product (admin only)
export const disableProduct = async (sku: string): Promise<Product> => {
  const response = await api.patch<Product>(`/products/${sku}/disable`);
  return response.data;
};

// Delete product (admin only)
export const deleteProduct = async (sku: string): Promise<void> => {
  await api.delete(`/products/${sku}`);
};