export interface Product {
  sku: string;
  name: string;
  price: number;
  stock: number;
  enabled: boolean;
}

export interface ProductRequest {
  name: string;
  price: number;
  initialStock?: number;
}

export interface User {
  username: string;
  roles: string[];
}

export type AuthUser = User | null;

export interface AuthState {
  user: AuthUser;
  isAuthenticated: boolean;
  isLoading: boolean;
  error: string | null;
}