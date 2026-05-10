const express = require("express");
const { MongoClient } = require("mongodb");

const app = express();
const mongoUrl = process.env.MONGO_URL || "mongodb://mongo:27017";

app.get("/", async (req, res) => {
  try {
    const client = new MongoClient(mongoUrl);
    await client.connect();
    const db = client.db("mydb");
    const coll = db.collection("hits");
    await coll.insertOne({ at: new Date() });
    const count = await coll.countDocuments();
    await client.close();
    res.json({ message: "Node + Mongo is running", count });
  } catch (err) {
    res.status(500).json({ error: err.message });
  }
});

app.listen(3000, () => {
  console.log("bt2 node app listening on 3000");
});
