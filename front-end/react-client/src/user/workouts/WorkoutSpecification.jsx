import React, {Component} from "react";
import {addNewWorkout} from "../../util/APIUtils";
import {Link} from "react-router-dom";

class WorkoutSpecification extends Component {
    constructor(props) {
        super(props);
        this.state = {
            type: "strength",
            level: "beginner",
            isError: false,
        };
        this.handleInputChange = this.handleInputChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    handleInputChange(event) {
        const target = event.target;
        const inputName = target.name;
        const inputValue = target.value;

        this.setState({
            [inputName]: inputValue,
        });
    }

    handleSubmit(event) {
        console.log(this.state);
        event.preventDefault();

        addNewWorkout(this.state.type, this.state.level)
            .then((response) => {
                this.props.loadCurrentlyLoggedInUser();
                this.props.history.push(`/workouts/${response.id}`);
            })
            .catch((error) => {
                this.setState({
                    isError: true,
                });
            });
    }

    render() {
        return (
            <div className={"container text-center"}>
                {this.state.isError ? (
                    <div className="container text-center">
                        <div className={"mt-5"}>
                            <h3 className={"alert alert-warning"}> INSUFFICIENT FUNDS</h3>
                            <Link type={"button"} className={"btn btn-primary mt-2"} to="/premium"> CHARGE YOUR
                                WALLET</Link>
                        </div>

                    </div>
                ) : (
                    <div>
                        <h1 className={"my-5"}>Give us some details about you</h1>
                        <form
                            className="form-horizontal workout-specification-form"
                            onSubmit={this.handleSubmit}
                        >
                            <div className="form-group my-4">
                                <div>
                                    <div className={"h4"}>What is your goal:</div>
                                    <label className="radio-inline mr-3">
                                        <input
                                            type="radio"
                                            name={"type"}
                                            value={"strength"}
                                            checked={this.state.type === "strength"}
                                            onChange={this.handleInputChange}
                                        />
                                        Strength
                                    </label>
                                    <label className="radio-inline">
                                        <input
                                            type={"radio"}
                                            name={"type"}
                                            value={"endurance"}
                                            checked={this.state.type === "endurance"}
                                            onChange={this.handleInputChange}
                                        />
                                        Endurance
                                    </label>
                                </div>
                            </div>
                            <div className="form-group my-4">
                                <div>
                                    <div className={"h4"}>What is your current level:</div>
                                    <label className="radio-inline mr-3">
                                        <input
                                            type={"radio"}
                                            name={"level"}
                                            value={"beginner"}
                                            checked={this.state.level === "beginner"}
                                            onChange={this.handleInputChange}
                                        />
                                        Beginner
                                    </label>
                                    <label className="radio-inline mr-3">
                                        <input
                                            type="radio"
                                            name={"level"}
                                            value={"intermediate"}
                                            checked={this.state.level === "intermediate"}
                                            onChange={this.handleInputChange}
                                        />
                                        Intermediate
                                    </label>
                                    <label className="radio-inline">
                                        <input
                                            type="radio"
                                            name={"level"}
                                            value={"advanced"}
                                            checked={this.state.level === "advanced"}
                                            onChange={this.handleInputChange}
                                        />
                                        Advanced
                                    </label>
                                </div>
                            </div>
                            <div className="form-group my-5">
                                <button type="submit" className="btn col-10 btn-primary">
                                    Create workout
                                </button>
                            </div>
                        </form>
                    </div>
                )}
            </div>
        );
    }
}

export default WorkoutSpecification;
