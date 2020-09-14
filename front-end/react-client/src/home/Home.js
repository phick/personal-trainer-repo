import React, { Component } from "react";
import home from "../img/home.png";
import "./Home.css"

class Home extends Component {
  render() {
    return (
        <div className={"text-center container-fluid"}>
            <h1 className={"mt-5"}>Welcome on personal trainer page</h1>
            <img alt="home" className="photo mt-5" src={home} />
        </div>

    );
  }
}

export default Home;
