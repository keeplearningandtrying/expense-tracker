new Vue({
  el: '#app',
  data: {
    auth: {},
    user: {},
    credentials: {},
    transactions: [],
    newTxn: {
      type: 'EXPENSE',
      createdOn: new Date().toJSON().slice(0,10)
    }
  },
  created: function () {
    this.checkAuth();
  },
  methods: {
    checkAuth: function() {
      if(localStorage.getItem('access_token') != null){
        this.auth['access_token'] = localStorage.getItem('access_token');
        this.fetchCurrentUser();
        this.fetchTransactions();
      }
    },
    authenticate: function () {
      $.ajax({
        url: '/api/auth/login',
        type: 'post',
        contentType: 'application/json',
        data: JSON.stringify(this.credentials)
      })
      .done(function (data) {
        this.auth = data;
        localStorage.setItem('access_token', this.auth['access_token']);
        this.fetchCurrentUser();
        this.fetchTransactions();
      }.bind(this));
    },
    logout: function() {
      localStorage.removeItem('access_token')
      window.location = "/"
    },
    fetchCurrentUser: function () {
      $.ajax({
        url: '/api/me',
        contentType: 'application/json',
        headers: {
          Authorization: 'Bearer '+this.auth['access_token']
        }
      })
      .done(function (data) {
        this.user = data;
      }.bind(this));
    },
    fetchTransactions: function () {
      $.ajax({
        url: '/api/transactions',
        contentType: 'application/json',
        headers: {
          Authorization: 'Bearer '+this.auth['access_token']
        }
      })
      .done(function (data) {
        this.transactions = data;
      }.bind(this));
    },
    createTransaction: function() {
      console.log(this.newTxn.createdOn);
      $.ajax({
        url: '/api/transactions',
        type: 'post',
        contentType: 'application/json',
        data: JSON.stringify(this.newTxn),
        headers: {
          Authorization: 'Bearer '+this.auth['access_token']
        }
      })
      .done(function (data) {
        this.fetchTransactions();
      }.bind(this));
    }
  },
  computed: {
    isAuthenticated: function() {
      console.log('access_token=', this.auth['access_token'])
      return this.auth['access_token'] != null
    }
  }
});
