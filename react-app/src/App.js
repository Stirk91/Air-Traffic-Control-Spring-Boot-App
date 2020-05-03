import React, { Component } from 'react';
import { BrowserRouter, Switch, Route } from 'react-router-dom';
import logo from './logo.svg';
import './App.css';
import HomePage from './HomePage';
import DiagnosticPage from './DiagnosticPage';
//import fetch from 'unfetch';


class App extends Component {
  render() {
    return (
      <BrowserRouter>
        <Switch>
          <Route path="/" exact component={HomePage} />
          <Route path="/diagnostics" exact component={DiagnosticPage} />
          <Route path="/" render={() => <div>404</div>} />
        </Switch>
      </BrowserRouter>
    );
  }
}

export default App;
