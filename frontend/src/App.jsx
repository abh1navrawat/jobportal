import React, { useState, useEffect } from 'react';
import { api } from './services/api';
import { 
  Briefcase, 
  User, 
  MapPin, 
  Clock, 
  PlusCircle, 
  List, 
  PieChart, 
  LogOut, 
  Search, 
  Layers, 
  Send, 
  CheckCircle, 
  XCircle, 
  AlertCircle,
  Building,
  TrendingUp
} from 'lucide-react';

function App() {
  // Navigation & Auth State
  const [currentPage, setCurrentPage] = useState('login'); // login, seeker, recruiter, admin
  const [isLoggedIn, setIsLoggedIn] = useState(false);
  const [userEmail, setUserEmail] = useState('');
  const [userRole, setUserRole] = useState('JOBSEEKER'); // JOBSEEKER, RECRUITER, ADMIN
  
  // Auth Form State
  const [isRegistering, setIsRegistering] = useState(false);
  const [loginEmail, setLoginEmail] = useState('');
  const [loginPassword, setLoginPassword] = useState('');
  const [regName, setRegName] = useState('');
  const [regEmail, setRegEmail] = useState('');
  const [regPassword, setRegPassword] = useState('');
  const [regRole, setRegRole] = useState('JOBSEEKER');
  const [authError, setAuthError] = useState('');
  const [authSuccess, setAuthSuccess] = useState('');

  // Seeker State
  const [jobs, setJobs] = useState([]);
  const [searchQuery, setSearchQuery] = useState('');
  const [filterType, setFilterType] = useState('ALL');
  const [selectedJob, setSelectedJob] = useState(null);
  const [seekerProfile, setSeekerProfile] = useState(null);
  const [showProfileModal, setShowProfileModal] = useState(false);
  const [showApplyModal, setShowApplyModal] = useState(false);
  
  // Profile Form State
  const [seekerSkills, setSeekerSkills] = useState('');
  const [seekerExperience, setSeekerExperience] = useState('');
  const [seekerResume, setSeekerResume] = useState('');
  const [seekerEducation, setSeekerEducation] = useState('');

  // Recruiter State
  const [recruiterJobs, setRecruiterJobs] = useState([]);
  const [activeTab, setActiveTab] = useState('view-jobs'); // post-job, view-jobs
  const [jobTitle, setJobTitle] = useState('');
  const [jobType, setJobType] = useState('FULLTIME');
  const [jobLocation, setJobLocation] = useState('');
  const [jobRemote, setJobRemote] = useState('No');
  const [jobCompany, setJobCompany] = useState('');
  const [jobCategory, setJobCategory] = useState('');
  const [jobDesc, setJobDesc] = useState('');

  // Admin Dashboard State
  const [adminStats, setAdminStats] = useState(null);

  // Check login on load
  useEffect(() => {
    const token = localStorage.getItem('token');
    const email = localStorage.getItem('email');
    const role = localStorage.getItem('role');
    if (token && email && role) {
      setIsLoggedIn(true);
      setUserEmail(email);
      setUserRole(role);
      if (role === 'JOBSEEKER') setCurrentPage('seeker');
      else if (role === 'RECRUITER') setCurrentPage('recruiter');
      else if (role === 'ADMIN') setCurrentPage('admin');
    }
  }, []);

  // Fetch jobs for seeker
  useEffect(() => {
    if (isLoggedIn && userRole === 'JOBSEEKER') {
      api.getAllJobs().then(setJobs).catch(console.error);
      api.getProfile(userEmail, 'JOBSEEKER').then(setSeekerProfile).catch(console.error);
    }
  }, [isLoggedIn, userRole]);

  // Fetch recruiter specific jobs
  useEffect(() => {
    if (isLoggedIn && userRole === 'RECRUITER') {
      api.getJobsByRecruiter(userEmail).then(setRecruiterJobs).catch(console.error);
    }
  }, [isLoggedIn, userRole, activeTab]);

  // Fetch admin dashboard stats
  useEffect(() => {
    if (isLoggedIn && userRole === 'ADMIN') {
      api.getAdminDashboard().then(setAdminStats).catch(console.error);
    }
  }, [isLoggedIn, userRole]);

  const handleLogin = async (e) => {
    e.preventDefault();
    setAuthError('');
    try {
      // Step 1: Login
      await api.login(loginEmail, loginPassword);
      // Step 2: Store role based on choice (simplified for prototype)
      localStorage.setItem('role', userRole);
      setIsLoggedIn(true);
      setUserEmail(loginEmail);
      
      if (userRole === 'JOBSEEKER') setCurrentPage('seeker');
      else if (userRole === 'RECRUITER') setCurrentPage('recruiter');
      else if (userRole === 'ADMIN') setCurrentPage('admin');
    } catch (err) {
      setAuthError('Invalid credentials or network issue.');
    }
  };

  const handleRegister = async (e) => {
    e.preventDefault();
    setAuthError('');
    setAuthSuccess('');
    try {
      await api.register({
        userName: regName,
        userEmail: regEmail,
        password: regPassword,
        role: regRole
      });
      setAuthSuccess('Registration successful! Please login.');
      setIsRegistering(false);
      setLoginEmail(regEmail);
    } catch (err) {
      setAuthError('Registration failed. Email might already exist.');
    }
  };

  const handleLogout = () => {
    api.logout();
    setIsLoggedIn(false);
    setUserEmail('');
    setCurrentPage('login');
  };

  const handleCreateProfile = async (e) => {
    e.preventDefault();
    try {
      const profile = await api.createProfile('JOBSEEKER', {
        jobSeekerName: seekerProfile?.jobSeekerName || userEmail.split('@')[0],
        jobSeekerEmail: userEmail,
        skills: seekerSkills,
        experience: seekerExperience,
        resumeUrl: seekerResume,
        education: seekerEducation
      });
      setSeekerProfile(profile);
      setShowProfileModal(false);
    } catch (err) {
      alert('Failed to save profile');
    }
  };

  const handleCreateJob = async (e) => {
    e.preventDefault();
    try {
      await api.createJobPost({
        jobTitle,
        jobType,
        jobLocation,
        remote: jobRemote,
        companyName: jobCompany,
        jobCategory: jobCategory,
        jobDescription: jobDesc,
        postedBy: userEmail
      });
      alert('Job Posted Successfully!');
      setJobTitle('');
      setJobLocation('');
      setJobCompany('');
      setJobCategory('');
      setJobDesc('');
      setActiveTab('view-jobs');
    } catch (err) {
      alert('Failed to post job');
    }
  };

  const handleApply = async (e) => {
    e.preventDefault();
    try {
      await api.applyForJob({
        jobSeekerName: seekerProfile?.jobSeekerName || userEmail.split('@')[0],
        jobSeekerEmail: userEmail,
        jobId: selectedJob.id,
        jobTitle: selectedJob.jobTitle,
        jobType: selectedJob.jobType,
        recruiterEmail: selectedJob.postedBy,
        status: 'PENDING'
      });
      alert('Successfully applied!');
      setShowApplyModal(false);
    } catch (err) {
      alert('Failed to apply (or you have already applied).');
    }
  };

  const handleCloseJob = async (id) => {
    if (confirm('Are you sure you want to close this job posting?')) {
      try {
        await api.closeJob(id);
        setRecruiterJobs(prev => prev.map(j => j.id === id ? { ...j, active: false } : j));
      } catch (err) {
        alert('Failed to close job');
      }
    }
  };

  return (
    <div>
      {/* Dynamic Header */}
      {isLoggedIn && (
        <header className="glass-panel" style={{ margin: '16px', borderRadius: '12px', padding: '16px 24px' }}>
          <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
            <div style={{ display: 'flex', alignItems: 'center', gap: '12px' }}>
              <div style={{ background: 'linear-gradient(135deg, var(--primary), var(--secondary))', padding: '8px', borderRadius: '8px' }}>
                <Briefcase size={24} color="white" />
              </div>
              <div>
                <h1 style={{ fontFamily: 'var(--font-display)', fontSize: '1.25rem', fontWeight: 800, letterSpacing: '-0.02em' }}>
                  JOB<span style={{ color: 'var(--secondary)' }}>FIND</span>
                </h1>
                <span className="badge badge-secondary" style={{ fontSize: '0.65rem' }}>{userRole} PORTAL</span>
              </div>
            </div>
            
            <div style={{ display: 'flex', alignItems: 'center', gap: '20px' }}>
              <div style={{ textAlign: 'right', display: 'none', md: 'block' }}>
                <div style={{ fontSize: '0.85rem', fontWeight: 600 }}>{userEmail}</div>
                <div style={{ fontSize: '0.75rem', color: 'var(--text-muted)' }}>Status: Active</div>
              </div>
              <button onClick={handleLogout} className="btn btn-secondary" style={{ padding: '8px 12px' }}>
                <LogOut size={16} /> Logout
              </button>
            </div>
          </div>
        </header>
      )}

      {/* Login / Register Pages */}
      {!isLoggedIn && (
        <div style={{ display: 'flex', justifyContent: 'center', alignItems: 'center', minHeight: '85vh', padding: '24px' }}>
          <div className="glass-panel animate-fade-in" style={{ width: '100%', maxWidth: '440px', padding: '40px' }}>
            <div style={{ textAlign: 'center', marginBottom: '32px' }}>
              <div style={{ background: 'linear-gradient(135deg, var(--primary), var(--secondary))', width: '48px', height: '48px', borderRadius: '12px', display: 'inline-flex', alignItems: 'center', justifyContent: 'center', marginBottom: '16px' }}>
                <Briefcase size={28} color="white" />
              </div>
              <h2 style={{ fontFamily: 'var(--font-display)', fontSize: '1.75rem', fontWeight: 700 }}>
                {isRegistering ? 'Create Account' : 'Welcome Back'}
              </h2>
              <p style={{ color: 'var(--text-muted)', fontSize: '0.9rem', marginTop: '4px' }}>
                {isRegistering ? 'Join the leading microservices job finder' : 'Log in to manage your professional journey'}
              </p>
            </div>

            {authError && (
              <div style={{ background: 'rgba(239,68,68,0.15)', border: '1px solid rgba(239,68,68,0.2)', color: 'var(--danger)', borderRadius: '8px', padding: '12px', fontSize: '0.85rem', marginBottom: '20px', display: 'flex', alignItems: 'center', gap: '8px' }}>
                <AlertCircle size={16} /> {authError}
              </div>
            )}

            {authSuccess && (
              <div style={{ background: 'rgba(16,185,129,0.15)', border: '1px solid rgba(16,185,129,0.2)', color: 'var(--success)', borderRadius: '8px', padding: '12px', fontSize: '0.85rem', marginBottom: '20px', display: 'flex', alignItems: 'center', gap: '8px' }}>
                <CheckCircle size={16} /> {authSuccess}
              </div>
            )}

            <form onSubmit={isRegistering ? handleRegister : handleLogin}>
              {isRegistering && (
                <div className="form-group">
                  <label className="form-label">Full Name</label>
                  <input type="text" required value={regName} onChange={e => setRegName(e.target.value)} className="form-input" placeholder="John Doe" />
                </div>
              )}

              <div className="form-group">
                <label className="form-label">Email Address</label>
                <input type="email" required value={isRegistering ? regEmail : loginEmail} onChange={e => isRegistering ? setRegEmail(e.target.value) : setLoginEmail(e.target.value)} className="form-input" placeholder="name@company.com" />
              </div>

              <div className="form-group">
                <label className="form-label">Password</label>
                <input type="password" required value={isRegistering ? regPassword : loginPassword} onChange={e => isRegistering ? setRegPassword(e.target.value) : setLoginPassword(e.target.value)} className="form-input" placeholder="••••••••" />
              </div>

              <div className="form-group">
                <label className="form-label">Select Your Role</label>
                <select value={isRegistering ? regRole : userRole} onChange={e => isRegistering ? setRegRole(e.target.value) : setUserRole(e.target.value)} className="form-input" style={{ background: 'var(--bg-dark)' }}>
                  <option value="JOBSEEKER">Job Seeker</option>
                  <option value="RECRUITER">Recruiter</option>
                  <option value="ADMIN">Administrator</option>
                </select>
              </div>

              <button type="submit" className="btn btn-primary" style={{ width: '100%', padding: '12px', marginTop: '16px' }}>
                {isRegistering ? 'Register Now' : 'Sign In'}
              </button>
            </form>

            <div style={{ textAlign: 'center', marginTop: '24px', fontSize: '0.9rem' }}>
              <span style={{ color: 'var(--text-muted)' }}>
                {isRegistering ? 'Already have an account?' : "Don't have an account?"}
              </span>{' '}
              <button onClick={() => { setIsRegistering(!isRegistering); setAuthError(''); }} style={{ background: 'none', border: 'none', color: 'var(--secondary)', fontWeight: 600, cursor: 'pointer' }}>
                {isRegistering ? 'Sign In' : 'Register Here'}
              </button>
            </div>
          </div>
        </div>
      )}

      {/* Job Seeker Dashboard */}
      {isLoggedIn && currentPage === 'seeker' && (
        <main className="container animate-fade-in" style={{ paddingBottom: '60px' }}>
          <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginBottom: '32px' }}>
            <div>
              <h2 style={{ fontFamily: 'var(--font-display)', fontSize: '1.75rem', fontWeight: 700 }}>Find Your Next Opportunity</h2>
              <p style={{ color: 'var(--text-muted)', fontSize: '0.95rem' }}>Browse microservice job posting posts</p>
            </div>
            <button onClick={() => {
              if (seekerProfile) {
                setSeekerSkills(seekerProfile.skills || '');
                setSeekerExperience(seekerProfile.experience || '');
                setSeekerResume(seekerProfile.resumeUrl || '');
                setSeekerEducation(seekerProfile.education || '');
              }
              setShowProfileModal(true);
            }} className="btn btn-secondary">
              <User size={16} /> My Professional Profile
            </button>
          </div>

          {/* Search bar */}
          <div className="glass-panel" style={{ padding: '16px', display: 'flex', flexWrap: 'wrap', gap: '16px', alignItems: 'center', marginBottom: '32px' }}>
            <div style={{ flex: 1, display: 'flex', alignItems: 'center', gap: '8px', background: 'rgba(255,255,255,0.03)', border: '1px solid var(--border-color)', borderRadius: '8px', padding: '10px 16px' }}>
              <Search size={18} color="var(--text-muted)" />
              <input type="text" placeholder="Search job title, skills, or company name..." value={searchQuery} onChange={e => setSearchQuery(e.target.value)} style={{ background: 'none', border: 'none', width: '100%', color: 'white', outline: 'none' }} />
            </div>
            <div>
              <select value={filterType} onChange={e => setFilterType(e.target.value)} className="form-input" style={{ background: 'var(--bg-dark)', padding: '10px 16px' }}>
                <option value="ALL">All Contract Types</option>
                <option value="FULLTIME">Full Time</option>
                <option value="PARTTIME">Part Time</option>
                <option value="INTERNSHIP">Internship</option>
                <option value="CONTRACT">Contractual</option>
              </select>
            </div>
          </div>

          {/* Jobs List */}
          <div className="grid-cards">
            {jobs
              .filter(j => j.active)
              .filter(j => j.jobTitle.toLowerCase().includes(searchQuery.toLowerCase()) || j.companyName.toLowerCase().includes(searchQuery.toLowerCase()))
              .filter(j => filterType === 'ALL' || j.jobType === filterType)
              .map(job => (
                <div key={job.id} className="glass-panel job-card">
                  <div>
                    <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'flex-start', marginBottom: '8px' }}>
                      <span className="badge badge-primary">{job.jobType}</span>
                      <span style={{ fontSize: '0.8rem', color: 'var(--text-muted)' }}>{job.remote === 'Yes' ? 'Remote' : 'On-Site'}</span>
                    </div>
                    <h3 style={{ fontSize: '1.2rem', fontWeight: 700, fontFamily: 'var(--font-display)', marginBottom: '4px' }}>{job.jobTitle}</h3>
                    <div style={{ display: 'flex', alignItems: 'center', gap: '6px', color: 'var(--text-muted)', fontSize: '0.9rem' }}>
                      <Building size={14} /> {job.companyName}
                    </div>
                  </div>
                  
                  <p style={{ fontSize: '0.9rem', color: 'var(--text-muted)', display: '-webkit-box', WebkitLineClamp: 3, WebkitBoxOrient: 'vertical', overflow: 'hidden' }}>
                    {job.jobDescription}
                  </p>

                  <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginTop: 'auto', paddingTop: '16px', borderTop: '1px solid var(--border-color)' }}>
                    <div style={{ display: 'flex', alignItems: 'center', gap: '4px', fontSize: '0.8rem', color: 'var(--text-muted)' }}>
                      <MapPin size={12} /> {job.jobLocation}
                    </div>
                    <button onClick={() => { setSelectedJob(job); setShowApplyModal(true); }} className="btn btn-primary" style={{ padding: '8px 16px', fontSize: '0.85rem' }}>
                      Apply Now
                    </button>
                  </div>
                </div>
              ))}
          </div>
        </main>
      )}

      {/* Recruiter Dashboard */}
      {isLoggedIn && currentPage === 'recruiter' && (
        <main className="container animate-fade-in" style={{ paddingBottom: '60px' }}>
          <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginBottom: '32px' }}>
            <div>
              <h2 style={{ fontFamily: 'var(--font-display)', fontSize: '1.75rem', fontWeight: 700 }}>Recruitment Hub</h2>
              <p style={{ color: 'var(--text-muted)', fontSize: '0.95rem' }}>Manage job posts and applicant selections</p>
            </div>
            
            <div className="glass-panel" style={{ padding: '6px', display: 'flex', gap: '8px', borderRadius: '10px' }}>
              <button onClick={() => setActiveTab('view-jobs')} className={`btn ${activeTab === 'view-jobs' ? 'btn-primary' : 'btn-secondary'}`} style={{ padding: '8px 16px' }}>
                <List size={16} /> My Postings
              </button>
              <button onClick={() => setActiveTab('post-job')} className={`btn ${activeTab === 'post-job' ? 'btn-primary' : 'btn-secondary'}`} style={{ padding: '8px 16px' }}>
                <PlusCircle size={16} /> Post a Job
              </button>
            </div>
          </div>

          {activeTab === 'post-job' && (
            <div className="glass-panel" style={{ maxWidth: '680px', margin: '0 auto', padding: '40px' }}>
              <h3 style={{ fontSize: '1.3rem', fontWeight: 700, marginBottom: '24px', fontFamily: 'var(--font-display)' }}>Create New Job Posting</h3>
              <form onSubmit={handleCreateJob}>
                <div style={{ display: 'grid', gridTemplateColumns: '1fr 1fr', gap: '20px' }}>
                  <div className="form-group">
                    <label className="form-label">Job Title</label>
                    <input type="text" required value={jobTitle} onChange={e => setJobTitle(e.target.value)} className="form-input" placeholder="Senior Backend Developer" />
                  </div>
                  <div className="form-group">
                    <label className="form-label">Company Name</label>
                    <input type="text" required value={jobCompany} onChange={e => setJobCompany(e.target.value)} className="form-input" placeholder="Google DeepMind" />
                  </div>
                </div>

                <div style={{ display: 'grid', gridTemplateColumns: '1fr 1fr', gap: '20px' }}>
                  <div className="form-group">
                    <label className="form-label">Job Type</label>
                    <select value={jobType} onChange={e => setJobType(e.target.value)} className="form-input" style={{ background: 'var(--bg-dark)' }}>
                      <option value="FULLTIME">Full Time</option>
                      <option value="PARTTIME">Part Time</option>
                      <option value="INTERNSHIP">Internship</option>
                      <option value="CONTRACT">Contractual</option>
                    </select>
                  </div>
                  <div className="form-group">
                    <label className="form-label">Remote Option</label>
                    <select value={jobRemote} onChange={e => setJobRemote(e.target.value)} className="form-input" style={{ background: 'var(--bg-dark)' }}>
                      <option value="Yes">Yes (Fully Remote)</option>
                      <option value="No">No (On-Site)</option>
                    </select>
                  </div>
                </div>

                <div style={{ display: 'grid', gridTemplateColumns: '1fr 1fr', gap: '20px' }}>
                  <div className="form-group">
                    <label className="form-label">Job Location</label>
                    <input type="text" required value={jobLocation} onChange={e => setJobLocation(e.target.value)} className="form-input" placeholder="Bangalore, India" />
                  </div>
                  <div className="form-group">
                    <label className="form-label">Job Category</label>
                    <input type="text" required value={jobCategory} onChange={e => setJobCategory(e.target.value)} className="form-input" placeholder="Engineering" />
                  </div>
                </div>

                <div className="form-group">
                  <label className="form-label">Detailed Job Description</label>
                  <textarea required value={jobDesc} onChange={e => setJobDesc(e.target.value)} className="form-input" rows={6} placeholder="Provide roles, responsibilities, and qualifications..." style={{ resize: 'vertical' }}></textarea>
                </div>

                <button type="submit" className="btn btn-primary" style={{ width: '100%', padding: '12px', marginTop: '12px' }}>
                  Publish Job Posting
                </button>
              </form>
            </div>
          )}

          {activeTab === 'view-jobs' && (
            <div className="grid-cards">
              {recruiterJobs.map(job => (
                <div key={job.id} className="glass-panel job-card" style={{ opacity: job.active ? 1 : 0.6 }}>
                  <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'flex-start' }}>
                    <span className="badge badge-primary">{job.jobType}</span>
                    <span className={`badge ${job.active ? 'badge-success' : 'badge-danger'}`}>
                      {job.active ? 'Active' : 'Closed'}
                    </span>
                  </div>
                  <h3 style={{ fontSize: '1.2rem', fontWeight: 700, fontFamily: 'var(--font-display)' }}>{job.jobTitle}</h3>
                  <p style={{ fontSize: '0.9rem', color: 'var(--text-muted)' }}>{job.jobDescription}</p>
                  
                  <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginTop: 'auto', paddingTop: '16px', borderTop: '1px solid var(--border-color)' }}>
                    <span style={{ fontSize: '0.8rem', color: 'var(--text-muted)' }}>Location: {job.jobLocation}</span>
                    {job.active && (
                      <button onClick={() => handleCloseJob(job.id)} className="btn btn-danger" style={{ padding: '6px 12px', fontSize: '0.8rem' }}>
                        Close Post
                      </button>
                    )}
                  </div>
                </div>
              ))}
              {recruiterJobs.length === 0 && (
                <div style={{ gridColumn: '1 / -1', textAlign: 'center', padding: '60px 20px', color: 'var(--text-muted)' }}>
                  <Briefcase size={48} style={{ marginBottom: '16px', opacity: 0.3 }} />
                  <p>No jobs posted yet. Click "Post a Job" to get started.</p>
                </div>
              )}
            </div>
          )}
        </main>
      )}

      {/* Admin Dashboard */}
      {isLoggedIn && currentPage === 'admin' && (
        <main className="container animate-fade-in" style={{ paddingBottom: '60px' }}>
          <div style={{ marginBottom: '32px' }}>
            <h2 style={{ fontFamily: 'var(--font-display)', fontSize: '1.75rem', fontWeight: 700 }}>Administrative Control Center</h2>
            <p style={{ color: 'var(--text-muted)', fontSize: '0.95rem' }}>System-wide performance metrics and analytical data</p>
          </div>

          {adminStats ? (
            <div>
              {/* Metric grid */}
              <div style={{ display: 'grid', gridTemplateColumns: 'repeat(auto-fit, minmax(240px, 1fr))', gap: '24px', marginBottom: '40px' }}>
                <div className="glass-panel" style={{ padding: '24px' }}>
                  <div style={{ color: 'var(--primary-light)', marginBottom: '8px' }}><Briefcase size={28} /></div>
                  <h4 style={{ fontSize: '0.85rem', color: 'var(--text-muted)', textTransform: 'uppercase' }}>Total Job Postings</h4>
                  <div style={{ fontSize: '2rem', fontWeight: 800, fontFamily: 'var(--font-display)', marginTop: '4px' }}>{adminStats.totalJobs}</div>
                </div>
                <div className="glass-panel" style={{ padding: '24px' }}>
                  <div style={{ color: 'var(--secondary)', marginBottom: '8px' }}><Send size={28} /></div>
                  <h4 style={{ fontSize: '0.85rem', color: 'var(--text-muted)', textTransform: 'uppercase' }}>Applications Handled</h4>
                  <div style={{ fontSize: '2rem', fontWeight: 800, fontFamily: 'var(--font-display)', marginTop: '4px' }}>{adminStats.totalApplications}</div>
                </div>
                <div className="glass-panel" style={{ padding: '24px' }}>
                  <div style={{ color: 'var(--success)', marginBottom: '8px' }}><User size={28} /></div>
                  <h4 style={{ fontSize: '0.85rem', color: 'var(--text-muted)', textTransform: 'uppercase' }}>Registered Seekers</h4>
                  <div style={{ fontSize: '2rem', fontWeight: 800, fontFamily: 'var(--font-display)', marginTop: '4px' }}>{adminStats.totalJobseekers}</div>
                </div>
                <div className="glass-panel" style={{ padding: '24px' }}>
                  <div style={{ color: 'var(--accent)', marginBottom: '8px' }}><Building size={28} /></div>
                  <h4 style={{ fontSize: '0.85rem', color: 'var(--text-muted)', textTransform: 'uppercase' }}>Active Recruiters</h4>
                  <div style={{ fontSize: '2rem', fontWeight: 800, fontFamily: 'var(--font-display)', marginTop: '4px' }}>{adminStats.totalRecruiters}</div>
                </div>
              </div>

              {/* Graphical summaries */}
              <div style={{ display: 'grid', gridTemplateColumns: '1fr 1fr', gap: '24px' }}>
                <div className="glass-panel" style={{ padding: '32px' }}>
                  <h3 style={{ fontSize: '1.2rem', fontWeight: 700, fontFamily: 'var(--font-display)', marginBottom: '20px', display: 'flex', alignItems: 'center', gap: '8px' }}>
                    <TrendingUp size={20} color="var(--primary-light)" /> Job Breakdown by Type
                  </h3>
                  <div style={{ display: 'flex', flexDirection: 'column', gap: '16px' }}>
                    <div>
                      <div style={{ display: 'flex', justifyContent: 'space-between', fontSize: '0.85rem', marginBottom: '6px' }}>
                        <span>Full Time ({adminStats.totalFullTimeJobs})</span>
                        <span>{Math.round((adminStats.totalFullTimeJobs/adminStats.totalJobs) * 100)}%</span>
                      </div>
                      <div style={{ height: '8px', background: 'rgba(255,255,255,0.05)', borderRadius: '10px', overflow: 'hidden' }}>
                        <div style={{ width: `${(adminStats.totalFullTimeJobs/adminStats.totalJobs) * 100}%`, height: '100%', background: 'var(--primary)', borderRadius: '10px' }}></div>
                      </div>
                    </div>

                    <div>
                      <div style={{ display: 'flex', justifyContent: 'space-between', fontSize: '0.85rem', marginBottom: '6px' }}>
                        <span>Internships ({adminStats.totalInternships})</span>
                        <span>{Math.round((adminStats.totalInternships/adminStats.totalJobs) * 100)}%</span>
                      </div>
                      <div style={{ height: '8px', background: 'rgba(255,255,255,0.05)', borderRadius: '10px', overflow: 'hidden' }}>
                        <div style={{ width: `${(adminStats.totalInternships/adminStats.totalJobs) * 100}%`, height: '100%', background: 'var(--secondary)', borderRadius: '10px' }}></div>
                      </div>
                    </div>
                  </div>
                </div>

                <div className="glass-panel" style={{ padding: '32px' }}>
                  <h3 style={{ fontSize: '1.2rem', fontWeight: 700, fontFamily: 'var(--font-display)', marginBottom: '20px', display: 'flex', alignItems: 'center', gap: '8px' }}>
                    <PieChart size={20} color="var(--accent)" /> Seeker Application Funnel
                  </h3>
                  <div style={{ display: 'flex', flexDirection: 'column', gap: '16px' }}>
                    <div>
                      <div style={{ display: 'flex', justifyContent: 'space-between', fontSize: '0.85rem', marginBottom: '6px' }}>
                        <span>Shortlisted ({adminStats.totalShortlisted})</span>
                        <span>{Math.round((adminStats.totalShortlisted/adminStats.totalApplications) * 100)}%</span>
                      </div>
                      <div style={{ height: '8px', background: 'rgba(255,255,255,0.05)', borderRadius: '10px', overflow: 'hidden' }}>
                        <div style={{ width: `${(adminStats.totalShortlisted/adminStats.totalApplications) * 100}%`, height: '100%', background: 'var(--success)', borderRadius: '10px' }}></div>
                      </div>
                    </div>

                    <div>
                      <div style={{ display: 'flex', justifyContent: 'space-between', fontSize: '0.85rem', marginBottom: '6px' }}>
                        <span>Pending Review ({adminStats.totalPending})</span>
                        <span>{Math.round((adminStats.totalPending/adminStats.totalApplications) * 100)}%</span>
                      </div>
                      <div style={{ height: '8px', background: 'rgba(255,255,255,0.05)', borderRadius: '10px', overflow: 'hidden' }}>
                        <div style={{ width: `${(adminStats.totalPending/adminStats.totalApplications) * 100}%`, height: '100%', background: 'var(--warning)', borderRadius: '10px' }}></div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          ) : (
            <div style={{ textAlign: 'center', padding: '100px 0' }}>
              <p style={{ color: 'var(--text-muted)' }}>Retrieving system metrics...</p>
            </div>
          )}
        </main>
      )}

      {/* Profiles Modal */}
      {showProfileModal && (
        <div style={{ position: 'fixed', top: 0, left: 0, right: 0, bottom: 0, backgroundColor: 'rgba(0,0,0,0.6)', display: 'flex', justifyContent: 'center', alignItems: 'center', zIndex: 1000, padding: '24px' }}>
          <div className="glass-panel animate-fade-in" style={{ width: '100%', maxWidth: '520px', padding: '36px' }}>
            <h3 style={{ fontSize: '1.3rem', fontWeight: 700, fontFamily: 'var(--font-display)', marginBottom: '24px' }}>My Professional Profile</h3>
            <form onSubmit={handleCreateProfile}>
              <div className="form-group">
                <label className="form-label">Core Skills (Comma separated)</label>
                <input type="text" required value={seekerSkills} onChange={e => setSeekerSkills(e.target.value)} className="form-input" placeholder="Java, React, Spring Boot, MySQL" />
              </div>
              <div className="form-group">
                <label className="form-label">Years of Experience</label>
                <input type="text" required value={seekerExperience} onChange={e => setSeekerExperience(e.target.value)} className="form-input" placeholder="2 Years" />
              </div>
              <div className="form-group">
                <label className="form-label">Education / Degree</label>
                <input type="text" required value={seekerEducation} onChange={e => setSeekerEducation(e.target.value)} className="form-input" placeholder="Bachelor of Science in Computer Science" />
              </div>
              <div className="form-group">
                <label className="form-label">Resume Link (Cloudinary or Drive URL)</label>
                <input type="text" required value={seekerResume} onChange={e => setSeekerResume(e.target.value)} className="form-input" placeholder="https://res.cloudinary.com/..." />
              </div>

              <div style={{ display: 'flex', gap: '12px', marginTop: '24px' }}>
                <button type="button" onClick={() => setShowProfileModal(false)} className="btn btn-secondary" style={{ flex: 1 }}>Cancel</button>
                <button type="submit" className="btn btn-primary" style={{ flex: 1 }}>Save Changes</button>
              </div>
            </form>
          </div>
        </div>
      )}

      {/* Applications Modal */}
      {showApplyModal && selectedJob && (
        <div style={{ position: 'fixed', top: 0, left: 0, right: 0, bottom: 0, backgroundColor: 'rgba(0,0,0,0.6)', display: 'flex', justifyContent: 'center', alignItems: 'center', zIndex: 1000, padding: '24px' }}>
          <div className="glass-panel animate-fade-in" style={{ width: '100%', maxWidth: '480px', padding: '36px' }}>
            <h3 style={{ fontSize: '1.3rem', fontWeight: 700, fontFamily: 'var(--font-display)', marginBottom: '12px' }}>Apply for Job</h3>
            <p style={{ color: 'var(--text-muted)', fontSize: '0.9rem', marginBottom: '24px' }}>
              You are applying for the role of <strong>{selectedJob.jobTitle}</strong> at <strong>{selectedJob.companyName}</strong>.
            </p>
            <form onSubmit={handleApply}>
              <div className="form-group">
                <label className="form-label">Your Email</label>
                <input type="text" readOnly value={userEmail} className="form-input" style={{ opacity: 0.6 }} />
              </div>
              
              <div style={{ display: 'flex', gap: '12px', marginTop: '24px' }}>
                <button type="button" onClick={() => setShowApplyModal(false)} className="btn btn-secondary" style={{ flex: 1 }}>Cancel</button>
                <button type="submit" className="btn btn-primary" style={{ flex: 1 }}>Submit Application</button>
              </div>
            </form>
          </div>
        </div>
      )}
    </div>
  );
}

export default App;
