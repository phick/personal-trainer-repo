import React, { Component } from "react";
import { showSessionHistory } from "../../util/APIUtils";
import SingleSessionComponent from "./SingleSessionComponent";
import { Link } from "react-router-dom";

class SessionHistory extends Component {
  constructor(props) {
    super(props);
    this.state = {
      session: [],
      exercise: [],
      e: [],

      isSessionClicked: [false, false, false, false, false, false, false],
    };
    this.loadHistory = this.loadHistory.bind(this);

    this.showExercises = this.showExercises.bind(this);
  }

  loadHistory() {
    showSessionHistory(this.props.match.params.id)
      .then((response) => {
        this.setState({
          session: response,
          isEmpty: false,
        });
        console.log(response);
        if (this.state.session[0] === undefined)
          this.setState({
            isEmpty: true,
          });
      })
      .catch((error) => {
        console.log("AN ERROR OCCURED" + error);
      });
  }

  componentDidMount() {
    this.loadHistory();
  }

  showExercises(id) {
    let newSessionClicked = this.state.isSessionClicked;
    newSessionClicked[id] === false
      ? (newSessionClicked[id] = true)
      : (newSessionClicked[id] = false);
    this.setState((prevState) => ({
      isSessionClicked: newSessionClicked,
    }));
    console.log(this.state.isSessionClicked[id]);
  }

  render() {
    console.log(this.state.isEmpty);
    return (
      <div className="container">
        {this.state.isEmpty ? (
          <div>
            <h3>It's looks like u have no session history.</h3>
            <button
              type="button"
              className=" btn btn-info"
              onClick={() => {
                this.props.history.push(
                  `/workouts/${this.props.match.params.id}`
                );
              }}
            >
              BACK TO PLAN
            </button>
            <button
              type="button"
              className=" btn btn-warning"
              onClick={() => {
                this.props.history.push(
                  `/workouts/${this.props.match.params.id}/new-session`
                );
              }}
            >
              ADD NEW SESSION
            </button>
          </div>
        ) : (
          <div>
            <Link to={`/workouts/${this.props.match.params.id}`}>
              {" "}
              CLICK TO GO BACK TO YOUR PLAN
            </Link>
            <table className="table table-bordered">
              <thead className="thead-light">
                <tr>
                  <th> SESSION HISTORY</th>
                </tr>
              </thead>
              <tbody>
                {this.state.session.map((session, index) => (
                  <div>
                    <tr key={index}>
                      <td colSpan="2"> {index + 1}</td>
                      <td colSpan="3">{session.date}</td>
                      <td colSpan="4">
                        <button
                          className="btn btn-secondary"
                          onClick={() => this.showExercises(index)}
                        >
                          {this.state.isSessionClicked[index] ? (
                            <span>HIDE</span>
                          ) : (
                            <span>EXPAND</span>
                          )}
                        </button>
                      </td>
                    </tr>
                    <tr>
                      <td colSpan="3">
                        {this.state.isSessionClicked[index] &&
                          this.state.session[index]
                            .exerciseForWorkoutSessionList && (
                            <SingleSessionComponent
                              exercises={
                                this.state.session[index]
                                  .exerciseForWorkoutSessionList
                              }
                            />
                          )}
                      </td>
                    </tr>
                  </div>
                ))}
              </tbody>
            </table>
          </div>
        )}
      </div>
    );
  }
}
export default SessionHistory;
