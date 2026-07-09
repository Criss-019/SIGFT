# ──────────────────────────────────────────────
#  STAGE 1: Build del frontend React + Vite
# ──────────────────────────────────────────────
FROM node:22-alpine AS build
WORKDIR /app

# Argumento inyectable desde docker-compose (URL del API Gateway)
ARG VITE_API_GATEWAY_URL=http://localhost:8080
ENV VITE_API_GATEWAY_URL=$VITE_API_GATEWAY_URL

# Copiar dependencias y descargarlas primero (cacheado)
COPY package.json package-lock.json ./
RUN npm install --legacy-peer-deps

# Copiar el resto del código y compilar
COPY . .
RUN npm run build

# ──────────────────────────────────────────────
#  STAGE 2: Servidor Nginx liviano para el SPA
# ──────────────────────────────────────────────
FROM nginx:stable-alpine
WORKDIR /usr/share/nginx/html

# Eliminar contenido por defecto de nginx
RUN rm -rf ./*

# Copiar el build generado por Vite
COPY --from=build /app/dist .

# Configuración de Nginx para SPA (React Router)
COPY nginx.conf /etc/nginx/conf.d/default.conf

EXPOSE 80

CMD ["nginx", "-g", "daemon off;"]
