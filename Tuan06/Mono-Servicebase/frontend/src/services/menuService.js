import axios from 'axios';

const API_URL = 'http://localhost:8082/api/menu';

export const menuService = {
  getAll: async () => {
    const response = await axios.get(API_URL);
    return response.data;
  },

  getAvailable: async () => {
    const response = await axios.get(`${API_URL}/available`);
    return response.data;
  },

  getById: async (id) => {
    const response = await axios.get(`${API_URL}/${id}`);
    return response.data;
  },

  create: async (menuItem) => {
    const response = await axios.post(API_URL, menuItem);
    return response.data;
  },

  update: async (id, menuItem) => {
    const response = await axios.put(`${API_URL}/${id}`, menuItem);
    return response.data;
  },

  delete: async (id) => {
    await axios.delete(`${API_URL}/${id}`);
  }
};
