FROM node:latest as build

WORKDIR /usr/local/app
COPY ./ui/ /usr/local/app/

RUN npm install
RUN npm run build

FROM nginx:latest
COPY --from=build /usr/local/app/dist/student-work-ui /usr/share/nginx/html
EXPOSE 80