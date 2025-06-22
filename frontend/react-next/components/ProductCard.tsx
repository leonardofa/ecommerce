'use client'

import React from 'react';
import Link from 'next/link';
import { Product } from '@/types';
import { useAuth } from '@/hooks/useAuth';

interface ProductCardProps {
  product: Product;
  onDelete: (sku: string) => void;
  onToggleStatus: (sku: string, currentStatus: boolean) => void;
}

const ProductCard: React.FC<ProductCardProps> = ({ 
  product, 
  onDelete, 
  onToggleStatus 
}) => {
  const { isAdmin } = useAuth();
  const isAdminUser = isAdmin();

  return (
    <div className={`bg-white rounded-lg shadow-md overflow-hidden border ${
      product.enabled ? 'border-green-200' : 'border-red-200'
    }`}>
      <div className="p-5">
        <div className="flex justify-between items-start">
          <div>
            <h3 className="text-lg font-semibold text-gray-800">{product.name}</h3>
            <p className="text-sm text-gray-500 mt-1">SKU: {product.sku}</p>
          </div>
          <div className={`px-2 py-1 rounded text-xs font-medium ${
            product.enabled 
              ? 'bg-green-100 text-green-800' 
              : 'bg-red-100 text-red-800'
          }`}>
            {product.enabled ? 'Enabled' : 'Disabled'}
          </div>
        </div>
        
        <div className="mt-4">
          <p className="text-gray-700 font-medium">
            ${product.price.toFixed(2)}
          </p>
          <p className="text-sm text-gray-600 mt-1">
            Stock: {product.stock} units
          </p>
        </div>
        
        {isAdminUser && (
          <div className="mt-5 flex flex-wrap gap-2">
            <Link 
              href={`/products/${product.sku}/edit`}
              className="btn btn-secondary text-xs py-1 px-3"
            >
              Edit
            </Link>
            
            <button
              onClick={() => onToggleStatus(product.sku, product.enabled)}
              className={`btn text-xs py-1 px-3 ${
                product.enabled 
                  ? 'bg-amber-500 hover:bg-amber-600 text-white' 
                  : 'bg-green-500 hover:bg-green-600 text-white'
              }`}
            >
              {product.enabled ? 'Disable' : 'Enable'}
            </button>

            {!product.enabled && product.stock === 0 && <button
              onClick={() => onDelete(product.sku)}
              className="btn btn-danger text-xs py-1 px-3"
              title={!product.enabled && product.stock === 0
                ? "Delete product" 
                : "Product must be disabled and have 0 stock to be deleted"}
            >
              Delete
            </button>}
          </div>
        )}
      </div>
    </div>
  );
};

export default ProductCard;