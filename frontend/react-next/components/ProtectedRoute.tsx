'use client'

import React, { ReactNode, useEffect } from 'react';
import { useRouter } from 'next/navigation';
import { useAuth } from '@/hooks/useAuth';

interface ProtectedRouteProps {
  children: ReactNode;
  requiredRole?: string;
}

const ProtectedRoute: React.FC<ProtectedRouteProps> = ({ 
  children, 
  requiredRole 
}) => {
  const { isAuthenticated, isLoading, hasRole } = useAuth();
  const router = useRouter();

  useEffect(() => {
    if (!isLoading) {
      if (!isAuthenticated) {
        router.push('/login');
      } else if (requiredRole && !hasRole(requiredRole)) {
        // If a specific role is required and user doesn't have it
        router.push('/unauthorized');
      }
    }
  }, [isAuthenticated, isLoading, requiredRole, hasRole, router]);

  // Show nothing while checking authentication
  if (isLoading) {
    return (
      <div className="flex items-center justify-center min-h-screen">
        <div className="text-center">
          <h2 className="text-xl font-semibold mb-2">Loading...</h2>
          <p className="text-gray-500">Please wait while we verify your access.</p>
        </div>
      </div>
    );
  }

  // If not authenticated or doesn't have required role, don't render children
  // (useEffect will handle redirect)
  if (!isAuthenticated || (requiredRole && !hasRole(requiredRole))) {
    return null;
  }

  // If authenticated and has required role (or no role required), render children
  return <>{children}</>;
};

export default ProtectedRoute;