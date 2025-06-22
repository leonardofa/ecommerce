'use client'

import React from 'react';
import { useForm } from 'react-hook-form';
import { Product, ProductRequest } from '@/types';

interface ProductFormProps {
  initialData?: Product;
  onSubmit: (data: ProductRequest) => Promise<void>;
  isSubmitting: boolean;
}

const ProductForm: React.FC<ProductFormProps> = ({
  initialData,
  onSubmit,
  isSubmitting
}) => {
  const isEditMode = !!initialData;
  
  const { 
    register, 
    handleSubmit, 
    formState: { errors } 
  } = useForm<ProductRequest>({
    defaultValues: isEditMode
      ? {
          name: initialData.name,
          price: initialData.price,
        }
      : undefined
  });

  const submitHandler = async (data: ProductRequest) => {
    await onSubmit(data);
  };

  return (
    <form onSubmit={handleSubmit(submitHandler)} className="space-y-6">
      <div>
        <label htmlFor="name" className="block text-sm font-medium text-gray-700">
          Product Name
        </label>
        <input
          id="name"
          type="text"
          className="input border mt-1"
          {...register('name', { 
            required: 'Product name is required' 
          })}
        />
        {errors.name && (
          <p className="mt-1 text-sm text-red-600">{errors.name.message}</p>
        )}
      </div>

      <div>
        <label htmlFor="price" className="block text-sm font-medium text-gray-700">
          Price ($)
        </label>
        <input
          id="price"
          type="number"
          step="0.01"
          min="0"
          className="input border mt-1"
          {...register('price', { 
            required: 'Price is required',
            min: {
              value: 0,
              message: 'Price must be greater than or equal to 0'
            },
            valueAsNumber: true
          })}
        />
        {errors.price && (
          <p className="mt-1 text-sm text-red-600">{errors.price.message}</p>
        )}
      </div>

      {!isEditMode && (
        <div>
          <label htmlFor="initialStock" className="block text-sm font-medium text-gray-700">
            Initial Stock
          </label>
          <input
            id="initialStock"
            type="number"
            min="0"
            className="input border mt-1"
            {...register('initialStock', { 
              min: {
                value: 0,
                message: 'Initial stock must be greater than or equal to 0'
              },
              valueAsNumber: true
            })}
          />
          {errors.initialStock && (
            <p className="mt-1 text-sm text-red-600">{errors.initialStock.message}</p>
          )}
        </div>
      )}

      <div className="flex justify-end space-x-3">
        <button
          type="button"
          onClick={() => window.history.back()}
          className="btn btn-secondary"
        >
          Cancel
        </button>
        <button
          type="submit"
          disabled={isSubmitting}
          className="btn btn-primary"
        >
          {isSubmitting 
            ? 'Saving...' 
            : isEditMode 
              ? 'Update Product' 
              : 'Create Product'
          }
        </button>
      </div>
    </form>
  );
};

export default ProductForm;