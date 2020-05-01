import React, { Component } from 'react';
import Container from './Container';
import Footer from './Footer';
import './App.css';
import {
Table
} from 'antd';
import { Button } from 'antd';
import { Link } from 'react-router-dom';
//import fetch from 'unfetch';


const getAllPlanesWithAll = () => fetch('http://localhost:8080/v1/plane/extended');


class HomePage extends Component {
  render() {
    return (
        <div class="homepage_title">
            <h1 class="title_header">ATC v1.0</h1>
                <div>
                    <PlanesWithAll /> 
                </div>
        </div>

    );
  }
}


class PlanesWithAll extends Component {
  
    state = {
      planesWithAll: []
    }
  
    componentDidMount () {
      this.fetchPlanesWithAll();
      setInterval(this.fetchPlanesWithAll, 1000);
    }
  
    fetchPlanesWithAll = () => {
      getAllPlanesWithAll()
      .then(res => res.json()
      .then(planesWithAll => {
        console.log(planesWithAll);
        planesWithAll.sort(function(a, b) {
          return a.tail_number.localeCompare(b.tail_number, undefined, {
            numeric: true,
            sensitivity: 'base'
          });
        });
        this.setState({
          planesWithAll
        });
      }));
    }
  
    render() {
  
      const { planesWithAll } = this.state;
  
      if (planesWithAll && planesWithAll.length) {
  
        const columns = [
  
          {
            title: 'Tail Number',
            dataIndex: 'tail_number',
            key: 'tail_number'
          },
          {
            title: 'Distance (ft)',
            dataIndex: 'distance',
            key: 'distance'
          },
          {
            title: 'Altitude (ft)',
            dataIndex: 'altitude',
            key: 'altitude'
          },
          {
            title: 'Speed (mph)',
            dataIndex: 'speed',
            key: 'speed'
          },
          {
            title: 'Heading',
            dataIndex: 'heading',
            key: 'heading'
          },
          {
            title: 'State',
            dataIndex: 'state',
            key: 'state'
          },
          {
            title: 'Runway',
            dataIndex: 'runway_name',
            key: 'runway_name'
          },
          {
            title: 'Taxiway',
            dataIndex: 'taxiway_name',
            key: 'taxiway_name'
          },
          {
            title: 'Gate',
            dataIndex: 'gate_name',
            key: 'gate_name'
          }
        ];
        return (
          <Container>
              <h2>Planes within 30 miles</h2>
          <Table 
            dataSource={planesWithAll}
            columns={columns}
            pagination={ {pageSize: 25} }
            rowKey='planeId' />
            <Footer></Footer>
            </Container>
        );
      }
      return <h2>No planes within 30 miles</h2>
    }
  }

  export default HomePage