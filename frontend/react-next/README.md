# E-commerce Product Management Frontend

This is a Next.js frontend application for the E-commerce Product Management system. It provides a user interface for managing products in an e-commerce system.

## Features

- Authentication with two user roles (ADMIN and USER)
- Product listing (accessible to both ADMIN and USER)
- Product creation (ADMIN only)
- Product editing (ADMIN only)
- Product enable/disable functionality (ADMIN only)
- Product deletion (ADMIN only)

## Technologies Used

- Next.js 14
- React 18
- TypeScript
- Tailwind CSS
- Axios for API requests
- React Hook Form for form handling

## Getting Started

### Prerequisites

- Node.js 18.x or later
- npm or yarn

### Installation

1. Clone the repository
2. Navigate to the frontend directory:
   ```
   cd frontend/react-next
   ```
3. Install dependencies:
   ```
   npm install
   # or
   yarn install
   ```

### Running the Application

1. Start the development server:
   ```
   npm run dev
   # or
   yarn dev
   ```
2. Open [http://localhost:3000](http://localhost:3000) in your browser

### Authentication

The application uses Basic Authentication with the following credentials:

- Admin user: `admin` / `admin`
- Regular user: `user` / `user`

### Building for Production

To build the application for production:

```
npm run build
# or
yarn build
```

Then, you can start the production server:

```
npm run start
# or
yarn start
```

## Project Structure

- `app/`: Next.js app directory with pages and layouts
- `components/`: Reusable React components
- `contexts/`: React context providers
- `hooks/`: Custom React hooks
- `services/`: API service functions
- `types/`: TypeScript type definitions

## Backend API

The frontend connects to a Spring Boot backend API running at `http://localhost:8080/api`. Make sure the backend server is running before using the frontend application.