import React, { Component } from "react";
import { showDetailsOfWorkout, getUsersWorkouts } from "../../util/APIUtils";
import { Link } from "react-router-dom";
import WorkoutSpecification from "./WorkoutSpecification";
import "./Workouts.css";

class WorkoutList extends Component {
  constructor(props) {
    super(props);
    this.state = {
      workouts: [],
    };

    this.loadWorkouts = this.loadWorkouts.bind(this);
  }

  componentDidMount() {
    this.loadWorkouts();
  }

  componentDidUpdate(nextProps) {
    if (this.props.location.key !== nextProps.location.key) {
      this.loadWorkouts();
    }
  }

  loadWorkouts() {
   
    getUsersWorkouts()
      .then((response) => {
        this.setState({
          workouts: response,
        });
      })
      .catch((error) => {
        console.log("AN ERROR OCCURED" + error);
      });
  }

  addWorkoutClicked() {
    this.props.history.push(`/workout-specification`);
  }

  workoutClicked(workoutId) {
    this.props.history.push(`workouts/${workoutId}`);
  }

  render() {
    return (
      <div className="container text-center">
        <h2 className={"my-5"}>
          {this.props.currentUser.username}'s workout plans{" "}
        </h2>
        <div className={"row"}>
          <button
            className={"btn btn-success ml-3 col-3"}
            onClick={() => this.addWorkoutClicked()}
          >
            Add new workout plan [ 25zł ]
          </button>
        </div>

        <div>
          <table className={"table table-bordered mt-4 text-center"}>
            <thead className="thead-dark">
              <tr>
                <th>INDEX</th>
                <th>NAME</th>
                <th>TYPE</th>
              </tr>
            </thead>
            <tbody className={"workouts-table-body"}>
              {this.state.workouts.map((workout, index) => (
                <tr
                  className={""}
                  onClick={() => this.workoutClicked(workout.id)}
                  key={workout.id}
                >
                  <td>{index + 1}</td>
                  <td>{workout.name}</td>
                  <td>{workout.type}</td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      </div>
    );
  }
}

export default WorkoutList;
