new Vue({
  el: '#app',
  data: {
    auth: {},
    user: {},
    transactions: [],
    newTxn: {
      type: 'EXPENSE'
    }
  },
  created: function () {
    this.checkAuth();
  },
  methods: {
    checkAuth: function() {
      this.authenticate('admin', 'admin');
    },
    authenticate: function (username, password) {
      $.ajax({
        url: '/api/auth/login',
        type: 'post',
        contentType: 'application/json',
        data: JSON.stringify({
          username: username,
          password: password
        })
      })
      .done(function (data) {
        this.auth = data;
        this.fetchCurrentUser();
        this.fetchTransactions();
      }.bind(this));
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
      this.newTxn.createdOn =
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
  }
});
