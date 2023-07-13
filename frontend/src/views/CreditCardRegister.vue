<template>
     <div class="container">
        <h2>Credit Card Register</h2>
        <!-- your existing form goes here -->
        ...
        <!-- below your form, add a table to display registered credit cards -->
        <table class="table">
        <thead>
            <tr>
            <th scope="col">#</th>
            <th scope="col">Card Number</th>
            <th scope="col">Name</th>
            <th scope="col">Actions</th>
            </tr>
        </thead>
        <tbody>
            <tr v-for="(card, index) in registeredCards" :key="index">
            <th scope="row">{{ index + 1 }}</th>
            <td>{{ card.cardNumber }}</td>
            <td>{{ card.cardName }}</td>
            <td>
                <button class="btn btn-danger" @click="deleteCard(card.id)">Delete</button>
            </td>
            </tr>
        </tbody>
        </table>
    </div>
    <div class="credit-card-register container mt-5">
        <h2>Add Credit Card</h2>
        <form @submit.prevent="submitForm">
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
                                minlength="3"
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

            <br />
            <br />
            <br />
            <br />
            <br />
            <br />
            <button type="submit" :disabled="validated" class="btn btn-primary">Add Credit Card</button>
        </form>
    </div>
</template>
<script>
import { useVuelidate } from '@vuelidate/core'
import { required, minLength } from '@vuelidate/validators'
import { reactive } from 'vue'

import axios from 'axios';
  
export default {
    setup () {
        return { v$: useVuelidate() }
    },
    data() {
        return reactive({
            registeredCards: [],
            valueFields: {
                cardName: '',
                cardNumber: '',
                cardMonth: null,
                cardYear: null,
                cardCvv: null
            },
            minCardYear: new Date().getFullYear(),
            mainCardNumber: '',
            cardNumberMaxLength: 19,
            inputFields: {
                cardNumber: 'v-card-number',
                cardName: 'v-card-name',
                cardMonth: 'v-card-month',
                cardYear: 'v-card-year',
                cardCvv: 'v-card-cvv'
            }
        })
    },
    validations: {
        valueFields: {
            cardName: { required },
            cardNumber: { required },
            cardMonth: { required },
            cardYear: { required },
            cardCvv: { required, minLength: minLength(3) }
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
        },
        validated() {
            return this.v$.$invalid === true || this.v$.$invalid === undefined
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
            console.log(this.v$)
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
        async deleteCard(id) {
            try {
                await axios.delete(`/api/members/creditcards/${id}`);
                this.getRegisteredCards()
                alert('Card deleted successfully!');
            } catch (error) {
                console.error(error);
            }
        },
        async submitForm() {
            // Trigger validation
            this.v$.$touch();

            if (this.v$.$invalid) {
                return;
            }

            try {
                await axios.post('/api/members/creditcards', {
                    cardName: this.valueFields.cardName,
                    cardNumber: this.valueFields.cardNumber,
                    month: this.valueFields.cardMonth,
                    year: this.valueFields.cardYear
                });
                this.getRegisteredCards()
                alert('Card Added successfully!');
            } catch (error) {
                console.error(error);
            }
        },
        async getRegisteredCards() {
            try {
                const response = await axios.get(`/api/members/creditcards`)
                this.registeredCards = response.data;
            } catch(error) { 

                if (error.response.status === 401) {
                    location.href = '/login';
                }

                alert("Error when loading results")
                this.registeredCards = [];
            }

        },
    },
    created() {
        this.getRegisteredCards()
    }
}
</script>
<style scoped>
@import '@/assets/css/style.min.css';
</style>
