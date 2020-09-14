import React, { Component } from "react";

class SingleSessionComponent extends Component {

  render() {
    return (
      <tr>
        {this.props.exercises.map((e, index) => (
          <li key={index} className="list-unstyled">
            <h5>{e.exercise.name}</h5>
            {e.exerciseSetList.map((s, index) => (
              <li key={s.id} className="list-unstyled">
                {index + 1}. {s.reps} x {s.weight} kg
              </li>
            ))}
          </li>
        ))}
      </tr>
    );
  }
}
export default SingleSessionComponent;
