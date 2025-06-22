'use client'

import React, { useState } from 'react';
import { useRouter } from 'next/navigation';
import { ProductRequest } from '@/types';
import ProtectedRoute from '@/components/ProtectedRoute';
import ProductForm from '@/components/ProductForm';
import * as productService from '@/services/productService';

export default function NewProductPage() {
  const [isSubmitting, setIsSubmitting] = useState(false);
  const [error, setError] = useState<string | null>(null);
  const router = useRouter();

  const handleSubmit = async (data: ProductRequest) => {
    setIsSubmitting(true);
    setError(null);
    
    try {
      await productService.createProduct(data);
      router.push('/products');
    } catch (err) {
      console.error('Error creating product:', err);
      setError('Failed to create product. Please try again.');
    } finally {
      setIsSubmitting(false);
    }
  };

  return (
    <ProtectedRoute requiredRole="ADMIN">
      <div className="max-w-2xl mx-auto">
        <h1 className="text-2xl font-bold mb-6">Create New Product</h1>
        
        {error && (
          <div className="bg-red-100 border border-red-400 text-red-700 px-4 py-3 rounded mb-4">
            {error}
          </div>
        )}
        
        <div className="bg-white p-6 rounded-lg shadow">
          <ProductForm 
            onSubmit={handleSubmit}
            isSubmitting={isSubmitting}
          />
        </div>
      </div>
    </ProtectedRoute>
  );
}