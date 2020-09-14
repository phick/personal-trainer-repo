import React, { Component } from "react";
import { Link } from "react-router-dom";
import "./AppHeader.css";
import logo from "../img/logo3.png";

class AppHeader extends Component {
 
  render() {
   
    return (
      <header className="app-header">
        <nav className="navbar navbar-dark navbar-expand-md text-center bg-dark">
          <Link to="/" style={{ textDecoration: "none" }}>
            <a className="navbar-brand">
              <img src={logo} className={"mr-2"} alt="" />
              Personal-Trainer
            </a>
          </Link>

          <button
            className="navbar-toggler"
            type="button"
            data-toggle="collapse"
            data-target="#mainmenu"
          >
            <span className="navbar-toggler-icon"></span>
          </button>

          <div className="collapse navbar-collapse" id="mainmenu">
            <div className="mr-auto">
              <ul className="navbar-nav">
                {this.props.authenticated && (
                  <li className="nav-item mx-1">
                    <Link className="nav-link " to={"/premium"}>
                      CHARGE WALLET
                    </Link>
                  </li>
                )}
                {this.props.authenticated && (
                  <li className="nav-item mx-1">
                    <Link className="nav-link " to={"/workouts"}>
                      {" "}
                      Workouts
                    </Link>
                  </li>
                )}
              </ul>
            </div>

            {this.props.authenticated && (
              <div className=" mr-4 text-white bg-dark">
                {" "}
                YOUR BALANCE: {this.props.currentUser.balance} zł
              </div>
            )}

            {this.props.authenticated && (
              <a
                className={"btn btn-secondary mr-2 text-white"}
                type={"button"}
                onClick={this.props.onLogout}
              >
                Logout
              </a>
            )}
            {!this.props.authenticated && (
              <Link
                className={"btn btn-secondary mr-2"}
                type={"button"}
                to={"/signup"}
              >
                SignUp{" "}
              </Link>
            )}
            {!this.props.authenticated && (
              <Link
                className={"btn btn-light mr-2"}
                type={"button"}
                to={"/login"}
              >
                Login{" "}
              </Link>
            )}
          </div>
        </nav>
      </header>
    );
  }
}
export default AppHeader;
