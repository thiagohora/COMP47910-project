<template>
    <div class="container">
      <h2>Unregister</h2>
      <p>Are you sure you want to unregister? This process is irreversible.</p>
      <button class="btn btn-danger" @click="confirmUnregister">Yes, Unregister</button>
      <router-link to="/login">No, go back</router-link>
    </div>
  </template>
  
  <script>
  import axios from 'axios';

  export default {
    methods: {
      async confirmUnregister() {
        try {
            await axios.delete(`/api/members`);
            alert('User unregisted successfully!');
            localStorage.removeItem('authToken')
            localStorage.removeItem('refreshToken')

            location.href = '/login';
        } catch (error) {
            console.error(error);
        }
      }
    }
  }
  </script>
  
  <style scoped>
  .container {
    margin-top: 50px;
    text-align: center;
  }
  
  h2 {
    margin-bottom: 20px;
  }
  
  .btn {
    margin-right: 10px;
  }
  </style>
  