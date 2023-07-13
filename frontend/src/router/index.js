import * as Router from 'vue-router';
import Login from '../views/LoginView.vue';
import Register from '../views/RegisterView.vue';
import RoomList from '../views/RoomList.vue';
import ReservationList from '../views/ReservationList.vue';
import BookRoom from '../views/BookRoom.vue';
import GuestReservation from '../views/GuestReservation.vue'
import CreditCardRegister from '../views/CreditCardRegister.vue'
import Unregister from '../views/UnregisterView.vue'

const router = Router.createRouter({
  history: Router.createWebHistory(),
  routes: [
      {
        path: '/',
        name: 'home',
        redirect: '/login'
      },
      {
        path: '/login',
        name: 'login',
        component: Login
      },
      {
        path: '/register',
        name: 'register',
        component: Register
      },
      {
        path: '/rooms',
        name: 'rooms',
        component: RoomList,
        meta: {
          requiresAuth: false
        }
      },
      {
        path: '/reservations',
        name: 'reservations',
        component: () => {
          const isLoggedIn = localStorage.getItem('authToken');
          if (isLoggedIn) {
              return ReservationList
          } else {
              return GuestReservation
          }
        }
      },
      {
        path: '/book-room',
        name: 'bookRoom',
        component: BookRoom,
        meta: {
          requiresAuth: false
        }
      },
      {
        path: '/card-register',
        name: 'creditCardRegister',
        component: CreditCardRegister,
        meta: {
          requiresAuth: true
        }
      },
      {
        path: '/unregister',
        name: 'unregister',
        component: Unregister,
        meta: {
          requiresAuth: true
        }
      }
  ]
});


router.beforeEach((to, from, next) => {
  const loggedIn = localStorage.getItem('authToken');
  if (to.matched.some(record => record.meta.requiresAuth)) {
    if (!loggedIn) {
      next('/login')
    } else {
      next()
    }
  } else {
    next() // make sure to always call next()!
  }
});


export default router;