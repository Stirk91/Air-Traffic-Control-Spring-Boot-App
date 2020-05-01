import React from 'react';
import Container from './Container';
import { Button } from 'antd';
import { Link } from 'react-router-dom';


const Footer = (props) => (
    <div>
        <Container>
            <Link to='/diagnostics'>
                <Button type='primary'>Diagnostics View</Button>
            </Link>
        </Container>
    </div>
);

export default Footer;