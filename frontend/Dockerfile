FROM node:18 as build-stage
WORKDIR /app

# Accept build argument
ARG REACT_APP_API_URL=http://backend.local:31919/
ENV REACT_APP_API_URL=$REACT_APP_API_URL

# Copy package files first for better caching
COPY package*.json ./

# Use npm ci for more reliable installs
RUN npm ci --only=production

# Copy source code
COPY . .

# Build the application
RUN npm run build

FROM httpd:2.4-alpine
COPY --from=build-stage /app/build /usr/local/apache2/htdocs/