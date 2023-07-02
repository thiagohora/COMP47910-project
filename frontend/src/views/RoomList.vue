<template>
  <div class="container">
    <h1 class="text-center">Available Rooms</h1>
    <div class="row">
      <div v-for="room in rooms" :key="room.id" class="col-md-4 mb-4">
        <div class="card">
          <img :src="room.imageUrl" class="card-img-top" alt="Room image">
          <div class="card-body">
            <h5 class="card-title">{{ room.name }}</h5>
            <p class="card-text">{{ room.description }}</p>
            <p class="card-text"><small class="text-muted">Max guests: {{ room.maxGuests }}</small></p>
            <button @click="bookRoom(room.id)" class="btn btn-primary">Book this room</button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      rooms: []
    }
  },
  methods: {
    async bookRoom(roomId) {
      // The method to book the room. Not implemented in this example.
      console.log('Room ID:', roomId);
    },
    async getRooms() {
      try {
        const response = await axios.get('/api/rooms');
        this.rooms = response.data;
      } catch (error) {
        console.error('Error occurred while fetching rooms:', error);
      }
    }
  },
  created() {
    this.getRooms();
  }
}
</script>

<style scoped>
/* Add your styles here */
</style>
