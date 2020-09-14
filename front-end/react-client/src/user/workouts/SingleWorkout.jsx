import React, { Component } from "react";
import { showDetailsOfWorkout } from "../../util/APIUtils";
import { Link } from "react-router-dom";

class SingleWorkout extends Component {
  constructor(props) {
    super(props);
    this.state = {
      workoutId: 0,
      workoutName: "",
      workoutType: "",
      exerciseForWorkoutList: [],
      isLoaded: false,
    };

    this.loadWorkout = this.loadWorkout.bind(this);
    this.addWorkoutSessionClicked = this.addWorkoutSessionClicked.bind(this);
  }

  componentDidMount() {
    this.loadWorkout();
  }

  loadWorkout() {
    showDetailsOfWorkout(this.props.match.params.id)
      .then((response) => {
        this.setState({
          workoutId: response.id,
          workoutName: response.name,
          workoutType: response.type,
          exerciseForWorkoutList: response.exerciseForWorkoutList,
        });
      })
      .catch((error) => {
        console.log("AN ERROR OCCURED" + error);
      });
  }

  addWorkoutSessionClicked() {
    this.props.history.push(`/workouts/${this.state.workoutId}/new-session`);
  }

  showHistorySessionClicked() {
    this.props.history.push(
      `/workouts/${this.state.workoutId}/session-history`
    );
  }

  render() {
    return (
      <div className="container text-center">
        <h2 className={"my-5"}>
          {this.state.workoutName} for {this.state.workoutType}
        </h2>
        <table className={"table table-bordered mt-4 text-center"}>
          <thead className="thead-dark">
            <tr>
              <th colSpan={"4"}> EXERCISES</th>
            </tr>
            <tr>
              <th>NAME</th>
              <th>SETS</th>
              <th>REPS</th>
              <th>WEIGHT</th>
            </tr>
          </thead>
          <tbody className={"single-workout-table-body"}>
            {this.state.exerciseForWorkoutList.map((e) => (
              <tr key={e.id}>
                <td>{e.exercise.name}</td>
                <td>{e.sets}</td>
                <td>{e.reps}</td>
                <td>{e.weight} kg</td>
              </tr>
            ))}
          </tbody>
        </table>
        <div className={"row justify-content-center my-3"}>
          <div className={"col-6"}>
            <button
              className={"btn btn-block btn-success"}
              onClick={() => this.addWorkoutSessionClicked()}
            >
              Add new session
            </button>
          </div>
          <div className={"col-6"}>
            <button
              className={"btn btn-block btn-primary"}
              onClick={() => this.showHistorySessionClicked()}
            >
              Show session history
            </button>
          </div>
        </div>

        <div className={"row mt-5 justify-content-center"}>
          <Link to="/workouts"> GO BACK</Link>
        </div>
      </div>
    );
  }
}

export default SingleWorkout;
