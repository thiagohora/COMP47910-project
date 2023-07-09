<template>
  <div class="container">
    <h1 class="text-center">Book a Room</h1>
    <form @submit.prevent="bookRoom" class="mx-auto p-4 bg-light" style="max-width: 400px;">
      <div class="mb-3">
        <label for="rooms">Rooms:</label>
        <select id="rooms" v-model="booking.selectedRooms" class="form-control" multiple>
            <option v-for="room in rooms" :key="room.id" :value="room.id">{{ room.roomType }} - Price {{ formatPrice(room.price) }} (Capacity: {{ room.capacity }})</option>
        </select>
      </div>
      <div class="mb-3">
        <input v-model="booking.startDate" type="date" class="form-control" @change="checkDates" :min="today" required/>
      </div>
      <div class="mb-3">
        <input v-model="booking.endDate" type="date" class="form-control" @change="checkDates" :min="startDate || today" required/>
      </div>

      <h2 class="my-4">User Details</h2>
        <div class="form-group">
            <label for="name">Name:</label>
            <input id="name" v-model="booking.name" type="text" class="form-control" maxlength="255" required>
        </div>
        <div class="form-group">
            <label for="surname">Surname:</label>
            <input id="surname" v-model="booking.surname" type="text" class="form-control" maxlength="255" required>
        </div>
        <div class="form-group">
            <label for="address">Address street:</label>
            <input id="address" v-model="booking.street" type="text" class="form-control" maxlength="255" required>
        </div>
        <div class="form-group">
            <label for="city">City:</label>
            <input id="city" v-model="booking.city" type="text" class="form-control" maxlength="255" required>
        </div>
        <div class="form-group">
            <label for="state">State:</label>
            <input id="state" v-model="booking.state" type="text" class="form-control" maxlength="255" required>
        </div>
        <div class="form-group">
            <label for="zipcode">Zipcode:</label>
            <input id="zipcode" v-model="booking.zipcode" type="text" class="form-control" maxlength="50" required>
        </div>
        <div class="form-group">
            <label for="country">Country:</label>
            <input id="country" v-model="booking.country" type="text" class="form-control" maxlength="2" required>
        </div>
        <div class="form-group">
            <label for="phone">Phone Number:</label>
            <input id="phone" v-model="booking.phone" type="text" class="form-control" maxlength="17" @input="validatePhoneNumber" required>
        </div>
        <div class="form-group">
            <label for="email">Email Address:</label>
            <input id="email" v-model="booking.email" type="email" maxlength="255" class="form-control" required>
        </div>
        <div class="form-group">
          <br />
          <label for="email">Payment Info:</label >
          <br />
          <div class="form-group">
            <div class="card-list">
              <div class="form-group">
                <vue-paycard :value-fields="valueFields" />  
              </div>
              <br />
              <div class="card-form__inner form-group">
                <div class="card-input form-group">
                  <label for="cardNumber" class="card-input__label">Card Number</label>
                  <input
                    type="tel"
                    :id="inputFields.cardNumber"
                    title="Number"
                    class="card-input__input form-control"
                    :value="valueFields.cardNumber"
                    @input="changeNumber"
                    data-card-field
                    autocomplete="off"
                    :maxlength="cardNumberMaxLength"
                    required
                  />
                </div>
                <div class="card-input form-group">
                  <label for="cardName" class="card-input__label">Card Holder</label>
                  <input
                    type="text"
                    :id="inputFields.cardName"
                    title="Name"
                    v-letter-only
                    class="card-input__input form-control"
                    :value="valueFields.cardName"
                    @input="changeName"
                    data-card-field
                    autocomplete="off"
                    required
                  />
                </div>
                <div class="card-form__row row">
                  <div class="card-form__col col-sm">
                    <div class="card-form__group form-group">
                      <label for="cardMonth" class="card-input__label" aria-label="Expiration Date">Expiration Date</label>
                      <div class="input-group">
                        <select
                          style="width:20% "
                          class="card-input__input -select form-control"
                          :id="inputFields.cardMonth"
                          aria-label="Card Month"
                          title="Month"
                          v-model="valueFields.cardMonth"
                          data-card-field
                          required
                        >
                          <option value disabled selected>Month</option>
                          <option
                            v-bind:value="n < 10 ? '0' + n : n"
                            v-for="n in 12"
                            v-bind:disabled="n < minCardMonth"
                            v-bind:key="n"
                          >{{generateMonthValue(n)}}</option>
                        </select>
                        <label class="input-group-addon glyphicon glyphicon-user"> / </label>
                        <select
                          style="width:30% "
                          class="card-input__input -select form-control"
                          :id="inputFields.cardYear"
                          aria-label="Card year"
                          title="Year"
                          v-model="valueFields.cardYear"
                          data-card-field
                          required
                        >
                          <option value disabled selected>Year</option>
                          <option
                            v-bind:value="$index + minCardYear"
                            v-for="(n, $index) in 12"
                            v-bind:key="n"
                          >{{$index + minCardYear}}</option>
                        </select>
                      </div>
                      
                    </div>
                  </div>
                  <div class="card-form__col -cvv form-group">
                    <div class="card-input form-group">
                      <label for="cardCvv" class="card-input__label" aria-label="Card CVV">CVV</label>
                      <input
                        type="tel"
                        title="CVV"
                        class="card-input__input form-control"
                        v-number-only
                        :id="inputFields.cardCvv"
                        maxlength="4"
                        :value="valueFields.cardCvv"
                        @input="changeCvv"
                        data-card-field
                        autocomplete="off"
                        required
                      />
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>

        <div class="form-group">
          <br />
          <br />
          <br />
          <br />
          <br />
          <br />
          <p class="text-danger" v-if="dateError">{{ dateError }}</p>
          <p class="text-danger" v-if="!isValid">{{ error }}</p>
          <button @click="bookRooms" class="btn btn-primary">Book Room</button>
        </div>
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
        selectedRooms: [],
        name: '',
        surname: '',
        street: '',
        city: '',
        zipcode: '',
        country: '',
        phone: '',
        email: '',
        startDate: '',
        endDate: '',
      },
      minCardYear: new Date().getFullYear(),
      mainCardNumber: '',
      cardNumberMaxLength: 19,
      valueFields: {
        cardName: '',
        cardNumber: '',
        cardMonth: null,
        cardYear: null,
        cardCvv: null
      },
      inputFields: {
        cardNumber: 'v-card-number',
        cardName: 'v-card-name',
        cardMonth: 'v-card-month',
        cardYear: 'v-card-year',
        cardCvv: 'v-card-cvv'
      },
      dateError: null,
      isValid: true,
      error: '',
      today: new Date().toISOString().split('T')[0]
    }
  },
  directives: {
    'number-only': {
      bind (el) {
        function checkValue (event) {
          event.target.value = event.target.value.replace(/[^0-9]/g, '')
          if (event.charCode >= 48 && event.charCode <= 57) {
            return true
          }
          event.preventDefault()
        }
        el.addEventListener('keypress', checkValue)
      }
    },
    'letter-only': {
      bind (el) {
        function checkValue (event) {
          if (event.charCode >= 48 && event.charCode <= 57) {
            event.preventDefault()
          }
          return true
        }
        el.addEventListener('keypress', checkValue)
      }
    }
  },
  computed: {
    minCardMonth () {
      if (this.valueFields.cardYear === this.minCardYear) return new Date().getMonth() + 1
      return 1
    }
  },
  watch: {
    cardYear () {
      if (this.valueFields.cardMonth < this.minCardMonth) {
        this.valueFields.cardMonth = ''
      }
    }
  },
  methods: {
    validatePhoneNumber() {
      const regex = /^\+\d{1,3}\d{1,14}$/;
      this.isValid = regex.test(this.booking.phone);
      this.error = this.isValid ? '' : 'Invalid phone number';
    },
    changeName (e) {
      this.valueFields.cardName = e.target.value
      this.$emit('input-card-name', this.valueFields.cardName)
    },
    changeNumber (e) {
      this.valueFields.cardNumber = e.target.value
      const value = this.valueFields.cardNumber.replace(/\D/g, '')
      // american express, 15 digits
      if ((/^3[47]\d{0,13}$/).test(value)) {
        this.valueFields.cardNumber = value.replace(/(\d{4})/, '$1 ').replace(/(\d{4}) (\d{6})/, '$1 $2 ')
        this.cardNumberMaxLength = 17
      } else if ((/^3(?:0[0-5]|[68]\d)\d{0,11}$/).test(value)) { // diner's club, 14 digits
        this.valueFields.cardNumber = value.replace(/(\d{4})/, '$1 ').replace(/(\d{4}) (\d{6})/, '$1 $2 ')
        this.cardNumberMaxLength = 16
      } else if (/^62[0-9]\d*/.test(value)) {
        this.valueFields.cardNumber = value.replace(/(\d{6})/, '$1 ').replace(/(\d{6}) (\d{7})/, '$1 $2 ').replace(/(\d{6}) (\d{7}) (\d{6})/, '$1 $2 $3 ').replace(/(\d{5}) (\d{5}) (\d{5}) (\d{4})/, '$1 $2 $3 $4')
        this.cardNumberMaxLength = 21
      } else if ((/^\d{0,16}$/).test(value)) { // regular cc number, 16 digits
        this.valueFields.cardNumber = value.replace(/(\d{4})/, '$1 ').replace(/(\d{4}) (\d{4})/, '$1 $2 ').replace(/(\d{4}) (\d{4}) (\d{4})/, '$1 $2 $3 ')
        this.cardNumberMaxLength = 19
      }
      // eslint-disable-next-line
      if (e.inputType == 'deleteContentBackward') {
        const lastChar = this.valueFields.cardNumber.substring(this.valueFields.cardNumber.length, this.valueFields.cardNumber.length - 1)
        // eslint-disable-next-line
        if (lastChar == ' ') { this.valueFields.cardNumber = this.valueFields.cardNumber.substring(0, this.valueFields.cardNumber.length - 1) }
      }
      this.$emit('input-card-number', this.valueFields.cardNumber)
    },
    changeMonth () {
      this.$emit('input-card-month', this.valueFields.cardMonth)
    },
    changeYear () {
      this.$emit('input-card-year', this.valueFields.cardYear)
    },
    changeCvv (e) {
      this.valueFields.cardCvv = e.target.value
      this.$emit('input-card-cvv', this.valueFields.cardCvv)
    },
    generateMonthValue (n) {
      return n < 10 ? `0${n}` : n
    },
    toggleMask () {
      this.isCardNumberMasked = !this.isCardNumberMasked
      if (this.isCardNumberMasked) {
        this.maskCardNumber()
      } else {
        this.unMaskCardNumber()
      }
    },
    maskCardNumber () {
      this.valueFields.cardNumberNotMask = this.valueFields.cardNumber
      this.mainCardNumber = this.valueFields.cardNumber
      const arr = this.valueFields.cardNumber.split('')
      arr.forEach((element, index) => {
        if (index > 4 && index < 14 && element.trim() !== '') {
          arr[index] = '*'
        }
      })
      this.valueFields.cardNumber = arr.join('')
    },
    unMaskCardNumber () {
      this.valueFields.cardNumber = this.mainCardNumber
    },
    formatPrice(value) {
      if (typeof value !== "number") {
        return value;
      }
      var formatter = new Intl.NumberFormat('en-US', {
          style: 'currency',
          currency: 'EUR',
          minimumFractionDigits: 2
      });
      return formatter.format(value);
    },
    checkDates() {
        this.dateError = '';
        if (this.booking.startDate && this.booking.endDate) {
            if (this.booking.startDate > this.booking.endDate) {
                this.dateError = 'End date cannot be earlier than start date.';
            } else {
                this.getRooms();
            }
        }
    },
    async getRooms() {

          if (!this.booking.startDate || !this.booking.endDate) {
              return []
          }

          const params = {};
          params.startDate = this.booking.startDate ? this.booking.startDate : new Date().toISOString().split('T')[0];
          params.endDate = this.booking.endDate ? this.booking.endDate : new Date().toISOString().split('T')[0];
          
            try {
                const response = await axios.get('/api/rooms/available', { params });
                this.rooms = response.data;
            } catch (error) {
                console.error(error);
            }
    },
    async bookRooms() {
        const reservation = {
            guest: {
              type: 'guest',
              name: this.booking.name,
              surname: this.booking.surname,
              address: {
                street: this.booking.street,
                city: this.booking.city,
                state: this.booking.state,
                zipcode: this.booking.zipcode,
                country: this.booking.country
              },
              contact: {
                email: this.booking.email,
                phone: this.booking.phone
              }
            },
            creditCard: `${this.valueFields.cardNumber}/${this.valueFields.cardMonth}-${this.valueFields.cardYear}/${this.valueFields.cardCvv}`,
            startDate: this.booking.startDate, 
            endDate: this.booking.endDate, 
            roomIds: this.booking.selectedRooms
        };


        try {
            await axios.post('/api/reservations', reservation);
            alert('Rooms booked successfully!');
            this.selectedRooms = [];
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

<style scoped>
  @import '@/assets/css/style.min.css';
</style>
