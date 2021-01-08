import Vue from 'vue'
import Vuex from 'vuex'
import auth from './modules/auth'
import users from './modules/users'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    account: null,
    xsrf: null
  },
  getters: {
    getAccount: (state) => {
      return state.account
    },
    getXsrf: (state) => {
      return state.xsrf
    }
  },
  mutations: {
    setAccount(state, account) {
      state.account = account
    },
    deleteAccount(state) {
      state.account = null
    },
    setXsrf(state, xsrf) {
      state.xsrf = xsrf
    }
  },
  actions: {
  },
  modules: {
    auth,
    users
  }
})
