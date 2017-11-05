import React, { Component } from 'react';
import GraphiQL, {ToolbarButton} from 'graphiql';
import {ModalContainer, ModalDialog} from 'react-modal-dialog';


class EndpointModal extends Component {
  constructor(props) {
    super(props);
    this.state = {
      endpoint: this.props.endpoint
    };
    this.handleEndpointIntput = this.handleEndpointIntput.bind(this);
    this.handleChange = this.handleChange.bind(this);
  }

  handleEndpointIntput(event) {
    this.setState({
      endpoint: event.target.value
    });
  }

  handleChange() {
    this.props.onChange(this.state.endpoint);
  }

  render() {
    return (
      <ModalContainer onClose={this.props.handleClose}>
        <ModalDialog onClose={this.props.handleClose} dismissOnBackgroundClick={true} >
          <label>New endpoint:
            <input type="text" name="name" value={this.state.endpoint} onChange={this.handleEndpointIntput} />
          </label>
          <button onClick={this.handleChange}>Change</button>
        </ModalDialog>
      </ModalContainer>
    );
  }
}

export default class CustommizedBar extends Component {

  constructor(props) {
    super(props);
    this.state = {
      showEndpointModal: false
    };
    this.updateEndpoint = this.updateEndpoint.bind(this);
    this.showEndpoint = this.showEndpoint.bind(this);
    this.handleClose = this.handleClose.bind(this);
  }

  handleClose() {
    this.setState({
      showEndpointModal: false
    });
  }

  updateEndpoint(endpoint) {
    this.props.onChangeEndpoint(endpoint);
    this.handleClose();
  }

  showEndpoint() {
    this.setState({
      showEndpointModal: true
    });
  }

  render() {
    return (
        <GraphiQL.Toolbar>
          <ToolbarButton title="Change Endpoint" label="Change Endpoint" onClick={this.showEndpoint} />
          {this.state.showEndpointModal && <EndpointModal endpoint={this.props.endpoint} handleClose={this.handleClose} onChange={this.updateEndpoint} />}
        </GraphiQL.Toolbar>
    );
  }
}