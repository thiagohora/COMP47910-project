<template>
  <div class="container">
    <h1 class="text-center">Login</h1>
    <form @submit.prevent="login" class="mx-auto p-4 bg-light" style="max-width: 400px;">
      <div class="mb-3">
        <input v-model="username" placeholder="Username" class="form-control" required/>
      </div>
      <div class="mb-3">
        <input type="password" v-model="password" placeholder="Password" class="form-control" required/>
      </div>
      <button type="submit" class="btn btn-primary">Login</button>
    </form>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      username: '',
      password: '',
    };
  },
  methods: {
    async login() {
      try {
        const response = await axios.post('/auth/login', {
          username: this.username,
          password: this.password,
        });

        // Store the token in local storage
        localStorage.setItem('authToken', response.data.accessToken);
        localStorage.setItem('refreshToken', response.data.refreshToken);

        // Redirect to the room list
        this.$router.push('/rooms');
      } catch (error) {
        console.error(error);
      }
    },
  },
};
</script>
