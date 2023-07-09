<template>
  <div class="container">
      <h2 class="my-4">Available Rooms</h2>
      <div class="row">
          <div class="col-md-4">
              <div class="form-group">
                  <label for="startDate">Start Date:</label>
                  <input id="startDate" v-model="startDate" type="date" class="form-control">
              </div>
          </div>
          <div class="col-md-4">
              <div class="form-group">
                  <label for="endDate">End Date:</label>
                  <input id="endDate" v-model="endDate" type="date" class="form-control">
              </div>
          </div>
          <div class="col-md-4">
              <div class="form-group">
                  <label for="capacity">Capacity:</label>
                  <input id="capacity" v-model.number="capacity" type="number" min="1" class="form-control">
              </div>
          </div>
      </div>
      <br />
      <button @click="getRooms" class="btn btn-primary">Search</button>

      <div v-if="rooms.length > 0" class="mt-4">
        <table class="table table-striped">
              <thead>
                  <tr>
                      <th scope="col">#</th>
                      <th scope="col">Room Type</th>
                      <th scope="col">Capacity</th>
                      <th scope="col">Price</th>
                  </tr>
              </thead>
              <tbody>
                  <tr v-for="room in rooms" :key="room.id">
                      <th scope="row">{{ room.id }}</th>
                      <td>{{ room.roomType }}</td>
                      <td>{{ room.capacity }}</td>
                      <td>{{ room.price }}</td>
                  </tr>
              </tbody>
          </table>
      </div>
      <p v-else class="mt-4">No rooms available</p>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  data() {
      return {
          rooms: [],
          startDate: null,
          endDate: null,
          capacity: null
      }
  },
  methods: {
      async getRooms() {
          const params = {};
          params.startDate = this.startDate ? this.startDate : new Date().toISOString().split('T')[0];
          params.endDate = this.endDate ? this.endDate : new Date().toISOString().split('T')[0];
          params.capacity = this.capacity;
          
          try {
              const response = await axios.get('/api/rooms/available', { params });
              this.rooms = response.data;
          } catch (error) {
              console.error(error);
          }
      }
  },
  created() {
      this.getRooms();
  }
}
</script>
