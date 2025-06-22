'use client'

import React from 'react';
import Link from 'next/link';
import { useRouter } from 'next/navigation';
import { useAuth } from '@/hooks/useAuth';

const Header: React.FC = () => {
  const { isAuthenticated, user, logout, isAdmin } = useAuth();
  const router = useRouter();

  const handleLogout = () => {
    logout();
    router.push('/login');
  };

  return (
    <header className="bg-primary-700 text-white shadow-md">
      <div className="container mx-auto px-4 py-4 flex justify-between items-center">
        <Link href="/" className="text-xl font-bold">
          E-commerce Admin
        </Link>

        {isAuthenticated && (
          <nav className="flex items-center space-x-6">
            <Link href="/products" className="hover:text-primary-200">
              Products
            </Link>
            
            {isAdmin() && (
              <Link href="/products/new" className="hover:text-primary-200">
                Add Product
              </Link>
            )}
            
            <div className="flex items-center space-x-4">
              <span className="text-sm">
                Welcome, {user?.username} ({isAdmin() ? 'Admin' : 'User'})
              </span>
              <button
                onClick={handleLogout}
                className="bg-primary-800 hover:bg-primary-900 px-3 py-1 rounded text-sm"
              >
                Logout
              </button>
            </div>
          </nav>
        )}
      </div>
    </header>
  );
};

export default Header;