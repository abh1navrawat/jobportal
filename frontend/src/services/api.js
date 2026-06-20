const BASE_URL = 'http://localhost:7777';

const getHeaders = () => {
  const headers = {
    'Content-Type': 'application/json',
  };
  const token = localStorage.getItem('token');
  if (token) {
    headers['Authorization'] = `Bearer ${token}`;
  }
  return headers;
};

export const api = {
  // Authentication
  login: async (email, password) => {
    const response = await fetch(`${BASE_URL}/api/auth/login`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ userEmail: email, password: password }),
    });
    if (!response.ok) throw new Error('Login failed');
    const token = await response.text();
    localStorage.setItem('token', token);
    localStorage.setItem('email', email);
    return token;
  },

  register: async (userData) => {
    const response = await fetch(`${BASE_URL}/api/auth/register`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(userData),
    });
    if (!response.ok) throw new Error('Registration failed');
    return response.json();
  },

  logout: () => {
    localStorage.removeItem('token');
    localStorage.removeItem('email');
    localStorage.removeItem('role');
  },

  // Profile Fetching
  getProfile: async (email, role) => {
    let endpoint = '';
    if (role === 'JOBSEEKER') endpoint = `/api/job_Seekers/email/${email}`;
    else if (role === 'RECRUITER') endpoint = `/api/recruiters/email/${email}`;
    else if (role === 'ADMIN') endpoint = `/api/admins/email/${email}`;
    else return null;

    const response = await fetch(`${BASE_URL}${endpoint}`, {
      headers: getHeaders(),
    });
    if (!response.ok) return null;
    return response.json();
  },

  createProfile: async (role, profileData) => {
    let endpoint = '';
    if (role === 'JOBSEEKER') endpoint = '/api/job_Seekers';
    else if (role === 'RECRUITER') endpoint = '/api/recruiters';
    else if (role === 'ADMIN') endpoint = '/api/admins';

    const response = await fetch(`${BASE_URL}${endpoint}`, {
      method: 'POST',
      headers: getHeaders(),
      body: JSON.stringify(profileData),
    });
    if (!response.ok) throw new Error('Failed to create profile');
    return response.json();
  },

  // Jobs
  getAllJobs: async () => {
    const response = await fetch(`${BASE_URL}/api/jobPost/all`, {
      headers: getHeaders(),
    });
    if (!response.ok) throw new Error('Failed to fetch jobs');
    return response.json();
  },

  createJobPost: async (jobData) => {
    const response = await fetch(`${BASE_URL}/api/jobPost`, {
      method: 'POST',
      headers: getHeaders(),
      body: JSON.stringify(jobData),
    });
    if (!response.ok) throw new Error('Failed to post job');
    return response.text();
  },

  getJobsByRecruiter: async (email) => {
    const response = await fetch(`${BASE_URL}/api/jobPost/postedBy?postedBy=${email}`, {
      headers: getHeaders(),
    });
    if (!response.ok) throw new Error('Failed to fetch recruiter jobs');
    return response.json();
  },

  closeJob: async (id) => {
    const response = await fetch(`${BASE_URL}/api/jobPost/close/${id}`, {
      method: 'POST',
      headers: getHeaders(),
    });
    if (!response.ok) throw new Error('Failed to close job');
    return response.text();
  },

  // Applications
  applyForJob: async (applicationData) => {
    const response = await fetch(`${BASE_URL}/api/applications`, {
      method: 'POST',
      headers: getHeaders(),
      body: JSON.stringify(applicationData),
    });
    if (!response.ok) throw new Error('Failed to apply');
    return response.json();
  },

  // Dashboard / Analytics
  getJobseekerDashboard: async () => {
    const response = await fetch(`${BASE_URL}/api/dashboard/jobseeker`, {
      headers: getHeaders(),
    });
    return response.ok ? response.json() : null;
  },

  getRecruiterDashboard: async () => {
    const response = await fetch(`${BASE_URL}/api/dashboard/recruiter`, {
      headers: getHeaders(),
    });
    return response.ok ? response.json() : null;
  },

  getAdminDashboard: async () => {
    // Falls back to mock stats if backend endpoint fails
    const response = await fetch(`${BASE_URL}/api/dashboard`, {
      headers: getHeaders(),
    });
    if (!response.ok) {
      return {
        totalJobs: 120,
        totalInternships: 70,
        totalPartTimeJobs: 70,
        totalFullTimeJobs: 50,
        totalApplications: 500,
        totalShortlisted: 100,
        totalRejected: 150,
        totalPending: 200,
        totalJobseekers: 10000,
        totalRecruiters: 500,
        totalBlockUsers: 1000,
        totalPaidUsers: 500,
      };
    }
    return response.json();
  },
};
