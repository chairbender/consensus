/*
A registration button, which attempts to register using credentials supplied
as props and displays a spinner while loading. Redirects to homepage
on success and logs in. Invokes a callback if an error occurs.

Props:
email - email address to use to register
password - plaintext password to use to login
passwordConfirmation - confirm plaintext password to use to login
onLoginError(errorMessage) - callback to invoke if register fails. errorMessage
  is a string describing the exact error.
*/
import React from 'react';
import Webservice from '../../util/Webservice.jsx'
import { hashHistory } from 'react-router'


export default React.createClass({
  getInitialState: function() {
    return {
      queryPending: false
    }
  },

  handleSubmit: function(e) {
    e.preventDefault();
    //confirm that passwords match
    if (this.props.password != this.props.passwordConfirmation) {
      this.props.onRegisterError('Passwords do not match.');
    } else {
      this.setState({queryPending: true});
      Webservice.register(this.props.email,this.props.password)
      .then(function(success) {
        this.setState({queryPending: false});
        if (success) {
          hashHistory.push('/');
        }
      }.bind(this)).catch(function(errorMessage) {
        this.setState({queryPending: false});
        this.props.onRegisterError(errorMessage);
      }.bind(this));
    }
  },

  render: function () {
    return (
        <button onClick={this.handleSubmit} className="btn btn-lg btn-primary btn-block btn-green" type="submit">
          Create account {this.state.queryPending && <i className="fa fa-circle-o-notch fa-spin"></i>}
        </button>
    )
  }
});