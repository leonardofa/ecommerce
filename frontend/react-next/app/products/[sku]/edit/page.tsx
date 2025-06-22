'use client'

import React, { useState, useEffect } from 'react';
import { useRouter } from 'next/navigation';
import { Product, ProductRequest } from '@/types';
import ProtectedRoute from '@/components/ProtectedRoute';
import ProductForm from '@/components/ProductForm';
import * as productService from '@/services/productService';

interface EditProductPageProps {
  params: {
    sku: string;
  };
}

export default function EditProductPage({ params }: EditProductPageProps) {
  const { sku } = params;
  const [product, setProduct] = useState<Product | null>(null);
  const [isLoading, setIsLoading] = useState(true);
  const [isSubmitting, setIsSubmitting] = useState(false);
  const [error, setError] = useState<string | null>(null);
  const router = useRouter();

  // Fetch product data
  useEffect(() => {
    const fetchProduct = async () => {
      try {
        setIsLoading(true);
        const data = await productService.getProductBySku(sku);
        setProduct(data);
        setError(null);
      } catch (err) {
        console.error('Error fetching product:', err);
        setError('Failed to load product. It might not exist or you may not have permission to view it.');
      } finally {
        setIsLoading(false);
      }
    };

    fetchProduct();
  }, [sku]);

  const handleSubmit = async (data: ProductRequest) => {
    if (!product) return;
    
    setIsSubmitting(true);
    setError(null);
    
    try {
      await productService.updateProduct(sku, data);
      router.push('/products');
    } catch (err) {
      console.error('Error updating product:', err);
      setError('Failed to update product. Please try again.');
    } finally {
      setIsSubmitting(false);
    }
  };

  return (
    <ProtectedRoute requiredRole="ADMIN">
      <div className="max-w-2xl mx-auto">
        <h1 className="text-2xl font-bold mb-6">Edit Product</h1>
        
        {error && (
          <div className="bg-red-100 border border-red-400 text-red-700 px-4 py-3 rounded mb-4">
            {error}
          </div>
        )}
        
        {isLoading ? (
          <div className="text-center py-10">
            <p className="text-gray-500">Loading product...</p>
          </div>
        ) : !product ? (
          <div className="bg-yellow-100 border border-yellow-400 text-yellow-700 px-4 py-3 rounded">
            Product not found.
          </div>
        ) : (
          <div className="bg-white p-6 rounded-lg shadow">
            <div className="mb-4 pb-4 border-b">
              <p className="text-sm text-gray-500">SKU: {product.sku}</p>
              <div className="flex items-center mt-2">
                <span className="text-sm mr-2">Status:</span>
                <span className={`px-2 py-1 rounded text-xs font-medium ${
                  product.enabled 
                    ? 'bg-green-100 text-green-800' 
                    : 'bg-red-100 text-red-800'
                }`}>
                  {product.enabled ? 'Enabled' : 'Disabled'}
                </span>
              </div>
              <p className="text-sm text-gray-500 mt-2">
                Current Stock: {product.stock} units
              </p>
            </div>
            
            <ProductForm 
              initialData={product}
              onSubmit={handleSubmit}
              isSubmitting={isSubmitting}
            />
          </div>
        )}
      </div>
    </ProtectedRoute>
  );
}