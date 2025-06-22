'use client'

import { useContext } from 'react';
import { AuthContext } from '@/contexts/AuthContext';
import * as authService from '@/services/authService';

export const useAuth = () => {
  const context = useContext(AuthContext);
  
  if (!context) {
    throw new Error('useAuth must be used within an AuthProvider');
  }
  
  // Add additional helper functions
  const isAdmin = () => {
    return authService.isAdmin();
  };
  
  const hasRole = (role: string) => {
    return authService.hasRole(role);
  };
  
  return {
    ...context,
    isAdmin,
    hasRole,
  };
};