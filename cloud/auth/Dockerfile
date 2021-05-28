FROM node
WORKDIR /app
COPY package.json /app
RUN npm install
COPY . /app
CMD npm rebuild bcrypt --build-from-source
EXPOSE 8080
CMD node index.js