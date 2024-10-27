import React, { useState } from 'react';
import { BrowserRouter as Router, Route, Switch } from 'react-router-dom';
import Login from './pages/Login';
import Dashboard from './pages/Dashboard';

const App = () => {
    const [isLoggedIn, setIsLoggedIn] = useState(false);

    return (
        <Router>
            <Switch>
                <Route path="/dashboard">
                    {isLoggedIn ? <Dashboard /> : <Login onLogin={() => setIsLoggedIn(true)} />}
                </Route>
                <Route path="/">
                    <Login onLogin={() => setIsLoggedIn(true)} />
                </Route>
            </Switch>
        </Router>
    );
};

export default App;
