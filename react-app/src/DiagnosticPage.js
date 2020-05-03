import React, { Component } from 'react';
import './App.css';

//import fetch from 'unfetch';

const getAllPlanes = () => fetch('http://localhost:8080/v1/plane/all');
const getAllRunways = () => fetch('http://localhost:8080/v1/runway');
const getAllTaxiways = () => fetch('http://localhost:8080/v1/taxiway');
const getAllGates = () => fetch('http://localhost:8080/v1/gate');


class DiagnosticPage extends Component {
  render() {
    return (

      <div className="top">

        <div className="dataColumns">
    
            <div class="planeColumn">
            <h2>Planes</h2>
            <Planes /> 
            </div>

            <div class="runwayColumn"> 
            <h2>Runways</h2>
            <Runways /> 
            </div>

            <div class="taxiwayColumn"> 
            <h2>Taxiways</h2>
            <Taxiways /> 
            </div>

            <div class="gateColumn"> 
            <h2>Gates</h2>
            <Gates /> 
            </div> 
            
        </div>

      </div>
    );
  }
}

class Planes extends Component {
  
  state = {
    planes: []
  }

  componentDidMount () {
    this.fetchPlanes();
    setInterval(this.fetchPlanes, 1000);
  }

  fetchPlanes = () => {
    getAllPlanes()
    .then(res => res.json()
    .then(planes => {
      console.log(planes);
      planes.sort(function(a, b) {
        return a.tail_number.localeCompare(b.tail_number, undefined, {
          numeric: true,
          sensitivity: 'base'
        });
      });
      this.setState({
        planes
      });
    }));
  }

  render() {

    const { planes } = this.state;

    if (planes && planes.length) {
      return planes.map((plane, index) => {
        return (

          <div class="json_object" key={index}>
            <p>{ "Plane Id: " + plane.plane_id}</p>
            <p> { "Tail Number: " + plane.tail_number } </p>
            <p> { "State: " + plane.state } </p>
            <p> { "Last Action: " + plane.last_action } </p>
            <p> { "Distance: " + plane.distance } </p>
            <p> { "Altitude: " + plane.altitude } </p>
            <p> { "Speed: " + plane.speed } </p>
            <p> { "Heading: " + plane.heading } </p>
          </div>
        );
      })
    }
    return <p>No planes found</p>
  }
}


class Runways extends Component {
  
  state = {
    runways: []
  }

  componentDidMount () {
    this.fetchRunways();
    setInterval(this.fetchRunways, 1000);
  }

  fetchRunways = () => {
    getAllRunways()
    .then(res => res.json()
    .then(runways => {
      runways.sort((a, b) => a.runway_id - b.runway_id);
      console.log(runways);
      this.setState({
        runways
      });
    }));
  }

  render() {

    const { runways } = this.state;

    if (runways && runways.length) {
      return runways.map((runway, index) => {
        return (

          <div class="json_object" key={index}>
            <p>{ "Runway Id: " + runway.runway_id}</p>
            <p>{ "Runway Name: " + runway.runway_name } </p>
            <p> { "Plane Id: " + runway.plane_id } </p>
          </div> 
        );
      })
    }
    return <p>No runways found</p>
  }
}


class Taxiways extends Component {
  
  state = {
    taxiways: []
  }

  componentDidMount () {
    this.fetchTaxiways();
    setInterval(this.fetchTaxiways, 1000);
  }

  fetchTaxiways = () => {
    getAllTaxiways()
    .then(res => res.json()
    .then(taxiways => {
      taxiways.sort((a, b) => a.taxiway_id - b.taxiway_id);
      console.log(taxiways);
      this.setState({
        taxiways
      });
    }));
  }

  render() {

    const { taxiways } = this.state;

    if (taxiways && taxiways.length) {
      return taxiways.map((taxiway, index) => {
        return (

          <div class="json_object" key={index}>
            <p>{ "Taxiway Id: " + taxiway.taxiway_id}</p>
            <p>{ "Taxiway Name: " + taxiway.taxiway_name } </p>
            <p> { "Plane Id: " + taxiway.plane_id } </p>

          </div> 
        );
      })
    }
    return <p>No taxiways found</p>
  }
}


class Gates extends Component {
  
  state = {
    gates: []
  }

  componentDidMount () {
    this.fetchGates();
    setInterval(this.fetchGates, 1000);
  }

  fetchGates = () => {
    getAllGates()
    .then(res => res.json()
    .then(gates => {
      gates.sort((a, b) => a.gate_id - b.gate_id);
      console.log(gates);
      this.setState({
        gates
      });
    }));
  }

  render() {

    const { gates } = this.state;

    if (gates && gates.length) {
      return gates.map((gate, index) => {
        return (

          <div class="json_object" key={index}>
            <p>{ "Gate Id: " + gate.gate_id}</p>
            <p>{ "Gate Name: " + gate.gate_name } </p>
            <p> { "Plane Id: " + gate.plane_id } </p>
          </div> 
        );
      })
    }
    return <p>No gates found</p>
  }
}

export default DiagnosticPage;
