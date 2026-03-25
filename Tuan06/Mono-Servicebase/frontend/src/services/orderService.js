import axios from 'axios';

const API_URL = 'http://localhost:8083/api/orders';

export const orderService = {
  getAll: async () => {
    const response = await axios.get(API_URL);
    return response.data;
  },

  getById: async (id) => {
    const response = await axios.get(`${API_URL}/${id}`);
    return response.data;
  },

  getByUserId: async (userId) => {
    const response = await axios.get(`${API_URL}/user/${userId}`);
    return response.data;
  },

  create: async (order) => {
    const response = await axios.post(API_URL, order);
    return response.data;
  },

  updateStatus: async (id, status) => {
    const response = await axios.put(`${API_URL}/${id}/status?status=${status}`);
    return response.data;
  }
};
