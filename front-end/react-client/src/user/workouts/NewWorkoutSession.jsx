import React, { Component } from "react";
import {
  addNewWorkoutSession,
  showDetailsOfWorkout,
} from "../../util/APIUtils";
import { Link } from "react-router-dom";

class NewWorkoutSession extends Component {
  constructor(props) {
    super(props);
    this.state = {
      workoutId: 0,
      workoutName: "",
      workoutType: "",
      exerciseForWorkoutList: [
        {
          id: 0,
          exercise: {
            id: 0,
            name: "",
            type: "",
          },
          sets: 0,
          reps: 0,
          weight: 0,
        },
      ],
      workoutSession: {
        id: 0,
        date: "",
        exerciseForWorkoutSessionList: [],
      },
    };
    this.loadWorkout = this.loadWorkout.bind(this);
    this.createWorkoutSession = this.createWorkoutSession.bind(this);
    this.getWorkoutTable = this.getWorkoutTable.bind(this);
    this.handleSubmit = this.handleSubmit.bind(this);
  }

  componentDidMount() {
    this.loadWorkout();
  }

  loadWorkout() {
    showDetailsOfWorkout(this.props.match.params.id)
      .then((response) => {
        this.setState({
          workoutId: response.id,
          workoutDate: response.date,
          workoutName: response.name,
          workoutType: response.type,
          exerciseForWorkoutList: response.exerciseForWorkoutList,
        });
        let workoutSession = this.createWorkoutSession();
        this.setState({
          workoutSession: workoutSession,
        });
        console.log(this.state.workoutSession);
      })
      .catch((error) => {
        console.log("AN ERROR OCCURED" + error);
      });
  }

  createWorkoutSession() {
    let id = 0;
    let date = "";
    let exerciseForWorkoutSessionList = [];
    let workoutSession = {};
    this.state.exerciseForWorkoutList.forEach((value, index) => {
      let weightForSet = value.weight;
      let reps = value.reps;
      let exercise = {
        id: value.exercise.id,
        name: value.exercise.name,
        type: value.exercise.type,
      };
      let exerciseSetList = [];
      for (let i = 0; i < value.sets; i++) {
        exerciseSetList.push({
          id: 0,
          reps: reps,
          weight: value.weight,
        });
      }
      exerciseForWorkoutSessionList.push({
        id: 0,
        weightForSet: weightForSet,
        exercise: exercise,
        exerciseSetList: exerciseSetList,
      });
    });
    workoutSession = {
      id: id,
      date: date,
      exerciseForWorkoutSessionList: exerciseForWorkoutSessionList,
    };
    return workoutSession;
  }

  getWorkoutTable() {
    let table = [];
    let tableContent = [];
    let i = 1;
    this.state.workoutSession.exerciseForWorkoutSessionList.forEach(
      (value, index) => {
        tableContent.push(
          <tr>
            <td colSpan={2} className={"bg-dark text-white"}>
              <h3>
                {value.exercise.name +
                  " " +
                  " - weight: " +
                  value.weightForSet +
                  " kg"}
              </h3>
            </td>
          </tr>
        );
        tableContent.push(
          <tr>
            <th>Set</th>
            <th>Reps</th>
          </tr>
        );
        i = 1;
        value.exerciseSetList.forEach((value1, index1) => {
          tableContent.push(
            <tr>
              <td>{i}</td>
              <td>
                <input
                  onChange={(event) =>
                    this.handleInputChange(event, index, index1)
                  }
                  type={"number"}
                  name={"reps"}
                  min={0}
                  value={
                    this.state.workoutSession.exerciseForWorkoutSessionList[
                      index
                    ].exerciseSetList[index1].reps
                  }
                />
              </td>
            </tr>
          );
          i++;
        });
        tableContent.push(
          <tr>
            <td colSpan={2} />
          </tr>
        );
      }
    );
    table.push(
      <table
        className={"text-center table my-5 single-workout-table-for-session"}
      >
        <tbody>{tableContent}</tbody>
      </table>
    );
    return table;
  }

  handleInputChange(event, index, index1) {
    const target = event.target;
    const inputValue = target.value;
    console.log(inputValue);
    let workoutSession = this.state.workoutSession;
    workoutSession.exerciseForWorkoutSessionList[index].exerciseSetList[
      index1
    ].reps = inputValue;
    this.setState({
      workoutSession: workoutSession,
    });
  }

  handleSubmit(event) {
    console.log(this.state.workoutSession);
    event.preventDefault();
    addNewWorkoutSession(this.state.workoutId, this.state.workoutSession)
      .then(this.props.history.push(`/workouts`))
      .catch((error) => {
        console.log("AN ERROR OCCURED" + error);
      });
  }

  render() {
    return (
      <div className="container text-center">
        <h1 className={"my-5"}>
          {this.state.workoutName + " for " + this.state.workoutType}
        </h1>
        <div className={"row"}>
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
        </div>

        <h2>Put your reps in every set</h2>
        <div className={"row justify-content-center"}>
          <form className="form col-12 " onSubmit={this.handleSubmit}>
            {this.getWorkoutTable()}
            <div className="form-item">
              <button type="submit" className="btn btn-block btn-primary">
                Save workout session
              </button>
            </div>
          </form>
          <div>
            <Link to="/workouts"> GO BACK TO YOUR WORKOUTS</Link>
          </div>
        </div>
      </div>
    );
  }
}

export default NewWorkoutSession;
