<template>
  <div id="app">
    <nav>
      <router-link to="/login">Login</router-link>
      |
      <router-link to="/register">Register</router-link>
      |
      <router-link to="/rooms">Rooms</router-link>
      |
      <router-link to="/reservations">My Reservations</router-link>
      |
      <router-link to="/book-room">Book a Room</router-link>
    </nav>
    <router-view/>
  </div>
</template>

<script>
export default {
  name: 'App'
}
</script>

<style>
#app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}
</style>
