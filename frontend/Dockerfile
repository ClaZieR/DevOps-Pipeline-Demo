FROM node:18 as build-stage
WORKDIR /app

# Accept build argument
ARG REACT_APP_API_URL=http://backend.local:31919
ENV REACT_APP_API_URL=$REACT_APP_API_URL

COPY . .
RUN npm install
RUN npm run build

FROM httpd:2.4-alpine
COPY --from=build-stage /app/build /usr/local/apache2/htdocs/