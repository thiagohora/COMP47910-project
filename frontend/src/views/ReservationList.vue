<template>
  <div class="container">
    <h1 class="text-center">My Reservations</h1>
    <ul class="list-unstyled">
      <li v-for="reservation in reservations" :key="reservation.id" class="border rounded p-3 mb-2 bg-light">
        <div>
          <h2>{{ reservation.room.name }}</h2>
          <p>Dates: {{ reservation.startDate }} - {{ reservation.endDate }}</p>
          <p>Status: {{ reservation.status }}</p>
          <button @click="cancelReservation(reservation.id)" class="btn btn-danger">Cancel Reservation</button>
        </div>
      </li>
    </ul>
  </div>
</template>


<script>
import axios from 'axios';

export default {
  data() {
    return {
      reservations: []
    }
  },
  methods: {
    async cancelReservation(reservationId) {
      // The method to cancel the reservation. Not implemented in this example.
      console.log('Reservation ID:', reservationId);
    },
    async getReservations() {
      try {
        const response = await axios.get('/api/reservations');
        this.reservations = response.data;
      } catch (error) {
        console.error('Error occurred while fetching reservations:', error);
      }
    }
  },
  created() {
    this.getReservations();
  }
}
</script>

<style scoped>
/* Add your styles here */
</style>
