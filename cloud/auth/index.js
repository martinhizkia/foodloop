//import packages
const express = require("express");
const session = require("express-session");
const bodyParser = require("body-parser");

const app = express();
const router = express.Router();
const { Client } = require("pg");
const bcrypt = require("bcrypt");

const db = new Client({
  user: "postgres",
  host: "34.101.123.153",
  database: "postgres",
  password: "foodloop3333",
  port: 5432,
});

db.connect((err) => {
  if (err) {
    console.error(err);
    return;
  }
  console.log("Database Connected");
});

app.use(
  session({
    secret: "ini contoh secret",
    saveUninitialized: false,
    resave: false,
  })
);
app.use(bodyParser.json());
app.use(
  bodyParser.urlencoded({
    extended: true,
  })
);
app.use(express.static(__dirname + "/"));
var temp;
// ROUTE 1 SIGNUP
//TO-DO
//ENKRIPSI PASSWORD
//CEK KALAU SUDAH ADA, ERROR

router.post("/signup", (req, res) => {
  temp = req.session;
  temp.username = req.body.username;
  temp.password = req.body.password;
  temp.email = req.body.email;
  temp.fullname = req.body.fullname;
  console.log(temp.username);
  console.log(temp.password);
  console.log(temp.email);
  const check = db.query(
    `SELECT * FROM users WHERE username = '${temp.username}';`,
    function (err, result) {
      if (Array.isArray(result.rows) && result.rows.length) {
        console.log(result.rows);
        res.json({ status: "User already exists" });
      } else {
        console.log("tidak ada");
        const hashedPassword = bcrypt.hash(
          temp.password,
          10,
          (err, hashedPassword) => {
            const signup = db.query(
              `INSERT INTO users(username, fullname, password, email) VALUES('${temp.username}','${temp.fullname}','${hashedPassword}', '${temp.email}');`
            );
            db.query(signup, (err, results) => {
              if (err) {
                console.error(err.detail);
                res.send(err.detail);
                return;
              }
            });
            res.json({
              status: "Signup Success",
              fullname: temp.fullname,
              email: temp.email,
            });
          }
        );
      }
    }
  );
});

//ROUTE 2 LOGIN
//DEKRIPSI PASSWORD, MATCH DENGAN YANG DIMASUKKAN
router.post("/login", (req, res) => {
  temp = req.session;
  temp.username = req.body.username;
  temp.password = req.body.password;
  console.log(temp.username);
  console.log(temp.password);
  const query = `SELECT * FROM users WHERE username = '${temp.username}';`;
  db.query(query, (err, results) => {
    if (err) {
      console.error(err);
      res.send(err);
      return;
    }
    if (Array.isArray(results.rows) && results.rows.length) {
      hashed = results.rows[0].password || null;
      temptemp = results.rows[0];
      bcrypt.compare(temp.password, hashed, (err, result) => {
        if (err) {
          console.log(err);
          res.send(err);
          return;
        } else {
          if (result) {
            res.json({
              status: "Login Success",
              fullname: temptemp.fullname,
              email: temptemp.email,
            });
          } else {
            res.json({ status: "Wrong Password" });
          }
        }
      });
    } else {
      res.json({ status: "Wrong Username" });
    }
  });
});

router.post("/update", (req, res) => {
  temp = req.session;
  temp.username = req.body.username;
  temp.password = req.body.password;
  temp.email = req.body.email;
  temp.fullname = req.body.fullname;
  console.log(temp.username);
  console.log(temp.password);
  console.log(temp.email);
  const check = db.query(
    `SELECT * FROM users WHERE username = '${temp.username}';`,
    function (err, result) {
      if (Array.isArray(result.rows) && result.rows.length) {
        console.log(result.rows);
        res.json({ status: "User already exists" });
      } else {
        console.log("tidak ada");
        const hashedPassword = bcrypt.hash(
          temp.password,
          10,
          (err, hashedPassword) => {
            const signup = db.query(
              `INSERT INTO users(username, fullname, password, email) VALUES('${temp.username}','${temp.fullname}','${hashedPassword}', '${temp.email}');`
            );
            db.query(signup, (err, results) => {
              if (err) {
                console.error(err.detail);
                res.send(err.detail);
                return;
              }
            });
            res.json({
              status: "Signup Success",
              fullname: temp.fullname,
              email: temp.email,
            });
          }
        );
      }
    }
  );
});

router.post("/signup", (req, res) => {
  temp = req.session;
  temp.username = req.body.username;
  temp.password = req.body.password;
  temp.email = req.body.email;
  temp.fullname = req.body.fullname;
  console.log(temp.username);
  console.log(temp.password);
  console.log(temp.email);
  const check = db.query(
    `SELECT * FROM users WHERE username = '${temp.username}';`,
    function (err, result) {
      if (Array.isArray(result.rows) && result.rows.length) {
        console.log(result.rows);
        res.json({ status: "User already exists" });
      } else {
        console.log("tidak ada");
        const hashedPassword = bcrypt.hash(
          temp.password,
          10,
          (err, hashedPassword) => {
            const signup = db.query(
              `INSERT INTO users(username, fullname, password, email) VALUES('${temp.username}','${temp.fullname}','${hashedPassword}', '${temp.email}');`
            );
            db.query(signup, (err, results) => {
              if (err) {
                console.error(err.detail);
                res.send(err.detail);
                return;
              }
            });
            res.json({
              status: "Signup Success",
              fullname: temp.fullname,
              email: temp.email,
            });
          }
        );
      }
    }
  );
});

app.use("/", router);
app.listen(process.env.PORT || 8080, () => {
  console.log(`App Started on PORT ${process.env.PORT || 3000}`);
});
