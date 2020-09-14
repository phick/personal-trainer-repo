import React, {Component} from "react";
import {Link} from "react-router-dom";

class PremiumComponent extends Component {
  constructor(props) {
    super(props);

    this.state = {
      number: 25,
    };

    this.handleInputChange = this.handleInputChange.bind(this);
  }

  handleInputChange(event) {
    this.setState({
      [event.target.name]: event.target.value,
    });
  }

  render() {
    return (
        <div className="container text-center">
          <div className={"row justify-content-center mt-5"}>
            <form
                className="form-horizontal"
                action={"http://localhost:8080/checkout"}
                method={"post"}
            >
              <h3 className={""}> CHARGE YOUR WALLET</h3>
              <div className="form-group">
                <input
                    type={"hidden"}
                    name={"userId"}
                    value={this.props.currentUser.id}
                />
              </div>
              <div className="form-group mb-2">
                <input
                    type={"number"}
                    min={25}
                    max={100}
                    name={"number"}
                    value={this.state.number}
                    onChange={this.handleInputChange}
                /> <span className={"h5"}>PLN</span>
              </div>
              <div className="form-group">
                <button className="btn btn-block btn-primary " type={"submit"}>
                  {" "}
                  Add funds
                </button>
              </div>
            </form>
          </div>

        </div>
    );
  }
}

export default PremiumComponent;
