<template>
  <div class="container">
    <h1 class="text-center">Book a Room</h1>
    <form @submit.prevent="bookRoom" class="mx-auto p-4 bg-light" style="max-width: 400px;">
      <div class="mb-3">
        <select v-model="booking.roomId" class="form-select" required>
            <option disabled value="">Please select a room</option>
            <option v-for="room in rooms" :value="room.id" :key="room.id">
              {{ room.name }}
            </option>
        </select>
      </div>
      <div class="mb-3">
        <input v-model="booking.startDate" type="date" class="form-control" required/>
      </div>
      <div class="mb-3">
        <input v-model="booking.endDate" type="date" class="form-control" required/>
      </div>
      <button type="submit" class="btn btn-primary">Book Room</button>
    </form>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      rooms: [],
      booking: {
        roomId: '',
        startDate: '',
        endDate: ''
      }
    }
  },
  methods: {
    async bookRoom() {
      try {
        const response = await axios.post('/api/book-room', this.booking);
        console.log(response.data);
        // Handle the response. This could be a redirection to a list of reservations, etc.
      } catch (error) {
        console.error('Error occurred during booking:', error);
      }
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
