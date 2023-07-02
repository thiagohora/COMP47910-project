<template>
  <div>
    <h1 class="text-center">Register as a Starwood Member</h1>
    <form @submit.prevent="register" class="mx-auto p-4 bg-light" style="max-width: 400px;">
      <div class="mb-3">
        <input v-model="user.name" placeholder="Name" class="form-control" required/>
      </div>
      <div class="mb-3">
        <input v-model="user.surname" placeholder="Surname" class="form-control" required/>
      </div>
      <div class="mb-3">
        <input v-model="user.username" placeholder="Username" class="form-control" required/>
      </div>
      <div class="mb-3">
        <input v-model="user.password" type="password" placeholder="Password" class="form-control" required/>
      </div>
      <button type="submit" class="btn btn-primary">Register</button>
    </form>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      user: {
        name: '',
        surname: '',
        username: '',
        password: ''
      }
    }
  },
  methods: {
    async register() {
      try {
        const response = await axios.post('/api/register', this.user);
                console.log(response.data);
        // Handle the response. This could be a redirection to a login page, etc.
      } catch (error) {
        console.error('Error occurred during registration:', error);
      }
    }
  }
}
</script>

<style scoped>
/* Add your styles here */
</style>
