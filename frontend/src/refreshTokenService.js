// refreshTokenService.js

import axios from 'axios';

const refreshTokenService = {
    async refreshToken() {
        // Get the refresh token from wherever you have stored it
        const refreshToken = localStorage.getItem('refreshToken');

        // Request a new token
        const response = await axios.post('/auth/token', {
            refreshToken: refreshToken
        });

        // If the response is successful, store the new tokens
        if (response.status === 200) {
            localStorage.setItem('authToken', response.data.accessToken);
            localStorage.setItem('refreshToken', response.data.refreshToken);
        }

        return response;
    },
    cleanToken() {
        localStorage.removeItem('authToken');
        localStorage.removeItem('refreshToken');
    }
}

export default refreshTokenService;
