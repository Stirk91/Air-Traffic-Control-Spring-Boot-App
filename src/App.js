import React, { Component } from 'react';
import logo from './logo.svg';
import './App.css';
import fetch from 'unfetch';

const getAllPlanes = () => fetch('http://localhost:8080/v1/plane');
const getAllRunways = () => fetch('http://localhost:8080/v1/runway');
const getAllTaxiways = () => fetch('http://localhost:8080/v1/taxiway');
const getAllGates = () => fetch('http://localhost:8080/v1/gate');




class App extends Component {
  render() {
    return (

      <div className="homepage">

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

          <div key={index}>
            <p>{ "Plane Id: " + plane.plane_id}</p>
            <p> { "Tail Number: " + plane.tail_number } </p>
            <p> { "State: " + plane.state } </p>
            <p> { "Last Action: " + plane.last_action } </p>
            <p> { "Distance: " + plane.distance } </p>
            <p> { "Altitude: " + plane.altitude } </p>
            <p> { "Speed: " + plane.speed } </p>
            <p> { "Heading: " + plane.heading } </p>
            <p> { "__________________________" } </p>
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

          <div key={index}>
            <p>{ "Runway Id: " + runway.runway_id}</p>
            <p>{ "Runway Name: " + runway.runway_name } </p>
            <p> { "Plane Id: " + runway.plane_id } </p>
            <p> { "__________________________" } </p>
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

          <div key={index}>
            <p>{ "Taxiway Id: " + taxiway.taxiway_id}</p>
            <p>{ "Taxiway Name: " + taxiway.taxiway_name } </p>
            <p> { "Plane Id: " + taxiway.plane_id } </p>
            <p> { "__________________________" } </p>
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

          <div key={index}>
            <p>{ "Gate Id: " + gate.gate_id}</p>
            <p>{ "Gate Name: " + gate.gate_name } </p>
            <p> { "Plane Id: " + gate.plane_id } </p>
            <p> { "__________________________" } </p>
          </div> 
        );
      })
    }
    return <p>No gates found</p>
  }
}






export default App;
