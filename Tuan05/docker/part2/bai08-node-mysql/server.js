const express = require("express");
const mysql = require("mysql2/promise");

const app = express();

async function getConnection() {
  return mysql.createConnection({
    host: process.env.DB_HOST || "mysql",
    user: process.env.DB_USER || "user",
    password: process.env.DB_PASSWORD || "password",
    database: process.env.DB_NAME || "mydb",
  });
}

app.get("/", async (req, res) => {
  try {
    const conn = await getConnection();
    const [rows] = await conn.query("SELECT NOW() AS now");
    await conn.end();
    res.json({ message: "Node connected to MySQL", time: rows[0].now });
  } catch (err) {
    res.status(500).json({ error: err.message });
  }
});

app.listen(3000, () => {
  console.log("bai08 app listening on 3000");
});
