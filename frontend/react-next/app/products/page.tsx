'use client'

import React, { useState, useEffect } from 'react';
import { useRouter } from 'next/navigation';
import Link from 'next/link';
import { Product } from '@/types';
import { useAuth } from '@/hooks/useAuth';
import ProtectedRoute from '@/components/ProtectedRoute';
import ProductCard from '@/components/ProductCard';
import * as productService from '@/services/productService';

export default function ProductsPage() {
  const [products, setProducts] = useState<Product[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);
  const { isAdmin } = useAuth();
  const router = useRouter();

  // Fetch products on component mount
  useEffect(() => {
    const fetchProducts = async () => {
      try {
        setLoading(true);
        const data = await productService.getAllProducts();
        setProducts(data);
        setError(null);
      } catch (err) {
        console.error('Error fetching products:', err);
        setError('Failed to load products. Please try again later.');
      } finally {
        setLoading(false);
      }
    };

    fetchProducts();
  }, []);

  // Handle product deletion
  const handleDelete = async (sku: string) => {
    // Verificar se estamos no navegador
    if (typeof window !== 'undefined') {
      const confirmDelete = window.confirm('Are you sure you want to delete this product?');
      if (!confirmDelete) {
        return;
      }

      try {
        await productService.deleteProduct(sku);
        setProducts(products.filter(product => product.sku !== sku));
      } catch (err) {
        console.error('Error deleting product:', err);
        alert('Failed to delete product. It might be in use or have stock.');
      }
    }
  };

  // Handle product status toggle (enable/disable)
  const handleToggleStatus = async (sku: string, currentStatus: boolean) => {
    try {
      let updatedProduct;
      if (currentStatus) {
        updatedProduct = await productService.disableProduct(sku);
      } else {
        updatedProduct = await productService.enableProduct(sku);
      }
      
      setProducts(products.map(product => 
        product.sku === sku ? updatedProduct : product
      ));
    } catch (err) {
      console.error('Error updating product status:', err);
      alert('Failed to update product status.');
    }
  };

  return (
    <ProtectedRoute>
      <div>
        <div className="flex justify-between items-center mb-6">
          <h1 className="text-2xl font-bold text-gray-800">Products</h1>
          {isAdmin() && (
            <Link href="/products/new" className="btn btn-primary">
              Add New Product
            </Link>
          )}
        </div>

        {loading ? (
          <div className="text-center py-10">
            <p className="text-gray-500">Loading products...</p>
          </div>
        ) : error ? (
          <div className="bg-red-100 border border-red-400 text-red-700 px-4 py-3 rounded">
            {error}
          </div>
        ) : products.length === 0 ? (
          <div className="text-center py-10">
            <p className="text-gray-500">No products found.</p>
            {isAdmin() && (
              <Link href="/products/new" className="btn btn-primary mt-4 inline-block">
                Add Your First Product
              </Link>
            )}
          </div>
        ) : (
          <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
            {products.map(product => (
              <ProductCard
                key={product.sku}
                product={product}
                onDelete={handleDelete}
                onToggleStatus={handleToggleStatus}
              />
            ))}
          </div>
        )}
      </div>
    </ProtectedRoute>
  );
}