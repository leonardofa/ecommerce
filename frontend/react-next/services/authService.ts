import Cookies from 'js-cookie';
import { User } from '@/types';

// Function to encode credentials for Basic Auth
const encodeCredentials = (username: string, password: string): string => {
  return btoa(`${username}:${password}`);
};

// Login function
export const login = async (username: string, password: string): Promise<User> => {
  try {
    // Encode credentials
    const token = encodeCredentials(username, password);
    
    // Store token in cookie
    Cookies.set('auth_token', token, { expires: 1 }); // Expires in 1 day
    
    // For Basic Auth, we need to determine user roles based on username
    // In a real app, you would fetch this from the server
    const roles = username === 'admin' ? ['ADMIN', 'USER'] : ['USER'];
    
    // Create user object
    const user: User = {
      username,
      roles,
    };
    
    // Store user info in cookie
    Cookies.set('user_info', JSON.stringify(user), { expires: 1 });
    
    return user;
  } catch (error) {
    console.error('Login error:', error);
    throw new Error('Authentication failed');
  }
};

// Logout function
export const logout = (): void => {
  Cookies.remove('auth_token');
  Cookies.remove('user_info');
};

// Get current user from cookie
export const getCurrentUser = (): User | null => {
  const userInfo = Cookies.get('user_info');
  if (userInfo) {
    try {
      return JSON.parse(userInfo) as User;
    } catch (error) {
      console.error('Error parsing user info:', error);
      return null;
    }
  }
  return null;
};

// Check if user is authenticated
export const isAuthenticated = (): boolean => {
  return !!Cookies.get('auth_token');
};

// Check if user has a specific role
export const hasRole = (role: string): boolean => {
  const user = getCurrentUser();
  return user ? user.roles.includes(role) : false;
};

// Check if user is an admin
export const isAdmin = (): boolean => {
  return hasRole('ADMIN');
};