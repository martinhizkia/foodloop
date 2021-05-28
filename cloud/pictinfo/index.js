//import packages
const express = require("express");
const session = require("express-session");
const bodyParser = require("body-parser");

const app = express();
const router = express.Router();
const { Client } = require("pg");

const db = new Client({
  user: "postgres",
  host: "34.101.123.153",
  database: "pictinfo",
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

router.post("/foodinfo", (req, res) => {
  temp = req.session;
  temp.foodname = req.body.foodname;
  temp.address = req.body.address;
  temp.description = req.body.description;
  temp.price = req.body.price;
  temp.contact = req.body.contact;
  temp.username = req.body.username;
  temp.category = req.body.category;
  temp.img = req.body.img;
  console.log(temp.username);
  console.log(temp.foodname);
  console.log(temp.description);
  const check = db.query(
    `INSERT INTO foodinfo(username, foodname, address, description, price, contact, category, img)
    VALUES('${temp.username}','${temp.foodname}','${temp.address}', '${temp.description}','${temp.price}',
    '${temp.contact}','${temp.category}','${temp.img}');`,
    function (err, result) {
      if (err) {
        res.json({
          status: "Failed",
        });
      } else {
        res.json({
          status: "Info Uploaded",
        });
      }
    }
  );
});

router.get("/foodinfo", (req, res) => {
  const check = db.query(`SELECT * FROM foodinfo;`, function (err, result) {
    if (err) {
      res.json({
        status: "Failed",
      });
    } else {
      res.json({ status: "Success", result: result.rows });
    }
  });
});

router.delete("/foodinfo", (req, res) => {
  const check = db.query(
    `DELETE FROM foodinfo where id=${req.body.id} and username='${req.body.username}';`,
    function (err, reas) {
      if (err) {
        res.json({
          status: "Error",
        });
      } else {
        res.json({
          status: "Data Has Been Deleted",
        });
      }
    }
  );
});

app.get("/foodinfo/:username", function (req, res) {
  const check = db.query(
    `SELECT * FROM foodinfo WHERE username='${req.params.username}';`,
    function (err, result) {
      if (err) {
        res.json({
          status: "Failed",
        });
      } else {
        res.json({ status: "Success", result: result.rows });
      }
    }
  );
});

app.use("/", router);
app.listen(process.env.PORT || 8080, () => {
  console.log(`App Started on PORT ${process.env.PORT || 3000}`);
});
