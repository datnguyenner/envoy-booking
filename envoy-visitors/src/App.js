import React, { Component } from 'react';
import './App.scss';
import Modal from 'react-modal';

class App extends Component {

  constructor(props) {
    super(props)
    this.state = {
      visitors: [],
      signedOutVisitors: [],
      isLoading: false,
      displayModal: false,
      fn: '',
      ln: '',
      notes: '',
      isFiltered: false
    }
  }

  async componentDidMount() {
    const response = await fetch("http://localhost:8080/getVisitors")
    const visitors = await response.json();
    this.setState({ visitors: [...visitors] });
  }

  signOutVisitor = async (fn, ln, notes) => {

    this.setState({ isLoading: true })
    const response = await fetch("http://localhost:8080/signOut", {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify({ fn, ln, notes, signOutTime: Date.now() })
    });
    const visitors = await response.json();
    this.setState({ visitors: [...visitors], isLoading: false });
  }

  addVisitor = async () => {

    this.setState({ isLoading: true })
    const response = await fetch("http://localhost:8080/addVisitor", {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify({ fn: this.state.fn, ln: this.state.ln, notes: this.state.notes, signOutTime: 0 })
    })
    const visitors = await response.json();
    this.setState({ visitors: [...visitors], isLoading: false, displayModal: false, fn: '', ln: '', notes: '' })
  }

  filterBySigneOut = () => {
    let isFiltered = this.state.isFiltered;
    this.setState({ isFiltered: !isFiltered })
  }

  renderVisitors = () => {

    let visitors = this.state.visitors;

    if (this.state.isFiltered) {
      visitors = visitors.filter(visitor => visitor.signOutTime > 0);
    }

    let body = visitors.map(({ fn, ln, notes, signOutTime }, i) => {
      let date = new Date(signOutTime);
      return (
        <tr key={i}>
          <td className="p-2 border-t border-grey-light font-mono text-xs">{fn} {ln}</td>
          <td className="p-2 border-t border-grey-light font-mono text-xs">{notes}</td>
          {signOutTime ?
            <td className="p-1 border-t border-grey-light font-mono text-xs">{date.toLocaleDateString() + ' ' + date.toLocaleTimeString()}</td> :
            <td className="p-1 border-t border-grey-light font-mono text-xs">
              {this.state.isLoading ?
                <button className="btn disabled btn--smaller btn--outline">Signing out <i className="fas fa-spinner"></i></button> :
                <button onClick={this.signOutVisitor.bind(this, fn, ln, notes)} className="btn btn--smaller btn--outline">Sign out</button>
              }
            </td>
          }
        </tr>
      )
    })

    return body;
  }

  render() {
    return (
      <div className="container mx-auto mt-12 p-8 border  min-h-screen max-w-3xl">
        <div className="clearfix">
          <button onClick={() => this.setState({ displayModal: true })} className="btn  btn--brand float-right ml-2"><i className="fas fa-user"></i>&nbsp;&nbsp;New visitor</button>
          <img src="https://dashboard.envoy.com/assets/images/logo-small-red-ba0cf4a025dd5296cf6e002e28ad38be.svg" alt="Envoy Logo" width="31" className="py3 block" />
        </div>

        <div className="flex-grow h-screen overflow-y-scroll">

          <div className="mx-auto">

            <div className="mt-8">
              <table className="w-full">
                <thead>
                  <tr>
                    <th className="text-sm font-semibold text-grey-darker p-2 bg-grey-lightest">Name</th>
                    <th className="text-sm font-semibold text-grey-darker p-2 bg-grey-lightest">Notes</th>
                    <th className="text-sm font-semibold text-grey-darker p-1 bg-grey-lightest">Signed out &nbsp;&nbsp;
                      <button onClick={this.filterBySigneOut} className="btn-filter">Filter by Signed out</button>
                    </th>
                  </tr>
                </thead>
                <tbody className="align-baseline">
                  {this.renderVisitors()}
                </tbody>
              </table>
            </div>
            <Modal
              isOpen={this.state.displayModal}
              style={customStyles}
              contentLabel="Example Modal"
            >
              <div>
                <input type="text" className="p-2 text-sm border float-left max-w-xs w-full" placeholder="First Name" onChange={(e) => this.setState({ fn: e.target.value })} />
                <input type="text" className="p-2 text-sm border float-left max-w-xs w-full" placeholder="Last Name" onChange={(e) => this.setState({ ln: e.target.value })} />
                <input type="text" className="p-2 text-sm border float-left max-w-xs w-full" placeholder="Notes" onChange={(e) => this.setState({ notes: e.target.value })} />
                <span>
                  <button onClick={this.addVisitor} className="btn  btn--brand ml-2"><i className="fas fa-user"></i>&nbsp;&nbsp;Add visitor</button>
                  <button onClick={() => this.setState({ displayModal: false })} className="btn  btn--brand ml-2">&nbsp;&nbsp;Cancel</button>
                </span>
              </div>
            </Modal>
          </div>
        </div>
      </div>
    );
  }
}

const customStyles = {
  overlay: { background: 'white' },
  content: {
    top: '50%',
    left: '50%',
    right: 'auto',
    bottom: 'auto',
    marginRight: '-50%',
    transform: 'translate(-50%, -50%)',
  }
};

export default App;