//this is an ai generated benchmark thingy file i asked ai to write a script to test my backend engine and this thingy is someway related to the k6 command line tool i used before in wsl.
import http from 'k6/http';
import { check, sleep } from 'k6';

export const options = {
  stages: [
    { duration: '3s', target: 10 },
    { duration: '5s', target: 20 },
    { duration: '2s', target: 0 },
  ],
  thresholds: {
    http_req_failed: ['rate<0.05'],
    http_req_duration: ['p(95)<500'],
  },
};

const BASE_URL = 'http://localhost:8080';

export default function () {
  const headers = { 'Content-Type': 'application/json' };

  // 1. Check wallet balance
  const resBalance = http.get(`${BASE_URL}/wallet/4/balance`);
  check(resBalance, {
    'get balance status is 200': (r) => r.status === 200,
  });

  // 2. Concurrent Transfer between alice and bob
  const isAliceSender = (__VU % 2 === 0);
  const sender = isAliceSender ? 'alice' : 'bob';
  const receiver = isAliceSender ? 'bob' : 'alice';

  const transferPayload = JSON.stringify({
    username: sender,
    toReceiverUsername: receiver,
    amount: 1.0,
  });

  const resTransfer = http.post(`${BASE_URL}/wallet/transferMoney`, transferPayload, { headers });
  check(resTransfer, {
    'transfer status is 200': (r) => r.status === 200,
  });

  // 3. Paginated transaction history
  const resHistory = http.get(`${BASE_URL}/wallet/4/transactions?page=0&size=5`);
  check(resHistory, {
    'history status is 200': (r) => r.status === 200,
  });

  sleep(0.1);
}
