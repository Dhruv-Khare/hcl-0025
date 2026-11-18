import { useEffect, useState, useMemo } from "react";
import api from "./api";
import AuthForm from "./AuthForm";
import PostForm from "./PostForm";

const App = () => {
  const [user, setUser] = useState(localStorage.getItem("username"));
  const [token, setToken] = useState(localStorage.getItem("token"));
  const [posts, setPosts] = useState([]);
  const [loading, setLoading] = useState(true);
  const [searchQuery, setSearchQuery] = useState("");
  const [showTokenModal, setShowTokenModal] = useState(false);
  const [copied, setCopied] = useState(false);

  const loadPosts = async () => {
    setLoading(true);
    try {
      const { data } = await api.get("/posts");
      setPosts(Array.isArray(data) ? data : []);
    } catch (err) {
      console.error("Failed to fetch posts:", err);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    loadPosts();

    const handleAuthExpired = () => {
      setUser(null);
      setToken(null);
    };

    window.addEventListener("auth-expired", handleAuthExpired);
    return () => window.removeEventListener("auth-expired", handleAuthExpired);
  }, []);

  const handleLogin = (username) => {
    setUser(username);
    setToken(localStorage.getItem("token"));
    loadPosts();
  };

  const logout = () => {
    localStorage.removeItem("token");
    localStorage.removeItem("username");
    setUser(null);
    setToken(null);
  };

  const copyToken = () => {
    if (token) {
      navigator.clipboard.writeText(token);
      setCopied(true);
      setTimeout(() => setCopied(false), 2000);
    }
  };

  // Decode JWT payload for visualization
  const decodedPayload = useMemo(() => {
    if (!token) return null;
    try {
      const parts = token.split(".");
      if (parts.length >= 2) {
        const payload = JSON.parse(atob(parts[1]));
        return payload;
      }
    } catch {
      return null;
    }
    return null;
  }, [token]);

  // Filter posts based on search query
  const filteredPosts = useMemo(() => {
    if (!searchQuery.trim()) return posts;
    const q = searchQuery.toLowerCase();
    return posts.filter(
      (p) =>
        p.title?.toLowerCase().includes(q) ||
        p.content?.toLowerCase().includes(q) ||
        p.author?.toLowerCase().includes(q)
    );
  }, [posts, searchQuery]);

  return (
    <div className="min-h-screen bg-slate-100/70 text-slate-900 flex flex-col font-sans">
      {/* Top Navigation */}
      <header className="sticky top-0 z-30 bg-white/80 backdrop-blur-md border-b border-slate-200">
        <div className="max-w-6xl mx-auto px-4 sm:px-6 h-16 flex items-center justify-between">
          {/* Logo & Brand */}
          <div className="flex items-center gap-3">
            <div className="w-9 h-9 rounded-xl bg-gradient-to-tr from-indigo-600 to-violet-500 flex items-center justify-center text-white shadow-sm shadow-indigo-200">
              <svg
                className="w-5 h-5"
                fill="none"
                viewBox="0 0 24 24"
                stroke="currentColor"
              >
                <path
                  strokeLinecap="round"
                  strokeLinejoin="round"
                  strokeWidth="2"
                  d="M12 15v2m-6 4h12a2 2 0 002-2v-6a2 2 0 00-2-2H6a2 2 0 00-2 2v6a2 2 0 002 2zm10-10V7a4 4 0 00-8 0v4h8z"
                />
              </svg>
            </div>
            <div>
              <span className="font-bold text-slate-800 text-lg leading-tight tracking-tight flex items-center gap-1.5">
                JWT Blog Demo
                <span className="text-[10px] font-semibold uppercase px-1.5 py-0.5 rounded bg-indigo-50 text-indigo-700 border border-indigo-200/60">
                  v1.0
                </span>
              </span>
              <p className="text-[11px] text-slate-500 -mt-0.5 hidden sm:block">
                Spring Boot & React Stateless Architecture
              </p>
            </div>
          </div>

          {/* Quick Links & User Profile */}
          <div className="flex items-center gap-2 sm:gap-3">
            <a
              href="http://localhost:8080/swagger-ui/index.html"
              target="_blank"
              rel="noreferrer"
              className="hidden md:inline-flex items-center gap-1.5 px-3 py-1.5 text-xs font-medium text-slate-600 hover:text-indigo-600 hover:bg-slate-50 rounded-lg border border-slate-200 transition-colors"
            >
              <svg
                className="w-3.5 h-3.5 text-slate-400"
                fill="none"
                viewBox="0 0 24 24"
                stroke="currentColor"
              >
                <path
                  strokeLinecap="round"
                  strokeLinejoin="round"
                  strokeWidth="2"
                  d="M9 12h6m-6 4h6m2 5H7a2 2 0 01-2-2V5a2 2 0 012-2h5.586a1 1 0 01.707.293l5.414 5.414a1 1 0 01.293.707V19a2 2 0 01-2 2z"
                />
              </svg>
              Swagger Docs
            </a>

            <a
              href="http://localhost:8080/h2-console"
              target="_blank"
              rel="noreferrer"
              className="hidden lg:inline-flex items-center gap-1.5 px-3 py-1.5 text-xs font-medium text-slate-600 hover:text-indigo-600 hover:bg-slate-50 rounded-lg border border-slate-200 transition-colors"
            >
              <svg
                className="w-3.5 h-3.5 text-slate-400"
                fill="none"
                viewBox="0 0 24 24"
                stroke="currentColor"
              >
                <path
                  strokeLinecap="round"
                  strokeLinejoin="round"
                  strokeWidth="2"
                  d="M4 7v10c0 2.21 3.582 4 8 4s8-1.79 8-4V7M4 7c0 2.21 3.582 4 8 4s8-1.79 8-4M4 7c0-2.21 3.582-4 8-4s8 1.79 8 4m0 5c0 2.21-3.582 4-8 4s-8-1.79-8-4"
                />
              </svg>
              H2 Console
            </a>

            {user ? (
              <div className="flex items-center gap-2 pl-2 border-l border-slate-200">
                <div
                  onClick={() => setShowTokenModal(true)}
                  title="Click to view active JWT"
                  className="flex items-center gap-2 bg-slate-100 hover:bg-indigo-50 px-2.5 py-1.5 rounded-full border border-slate-200/80 cursor-pointer transition-colors"
                >
                  <div className="w-6 h-6 rounded-full bg-indigo-600 text-white text-xs font-bold flex items-center justify-center uppercase">
                    {user[0]}
                  </div>
                  <span className="text-xs font-medium text-slate-700 max-w-[100px] truncate">
                    @{user}
                  </span>
                  <span className="w-1.5 h-1.5 rounded-full bg-emerald-500"></span>
                </div>

                <button
                  onClick={logout}
                  className="text-xs font-medium text-rose-600 hover:text-rose-700 hover:bg-rose-50 px-2.5 py-1.5 rounded-lg border border-transparent transition-colors cursor-pointer"
                >
                  Logout
                </button>
              </div>
            ) : (
              <div className="flex items-center gap-2">
                <span className="inline-flex items-center gap-1.5 text-xs text-slate-500 bg-slate-100 px-2.5 py-1 rounded-full border border-slate-200">
                  <span className="w-1.5 h-1.5 rounded-full bg-amber-500"></span>
                  Guest Mode
                </span>
              </div>
            )}
          </div>
        </div>
      </header>

      {/* Hero Banner */}
      <section className="bg-gradient-to-b from-white to-slate-100 border-b border-slate-200/80 py-8 px-4 sm:px-6">
        <div className="max-w-6xl mx-auto flex flex-col md:flex-row items-center justify-between gap-6">
          <div className="max-w-2xl text-center md:text-left">
            <div className="inline-flex items-center gap-2 px-3 py-1 rounded-full text-xs font-semibold bg-indigo-50 text-indigo-700 border border-indigo-200/70 mb-3">
              <span className="w-2 h-2 rounded-full bg-indigo-600 animate-ping"></span>
              Secure JWT Full-Stack Training Demo
            </div>
            <h1 className="text-2xl sm:text-3xl lg:text-4xl font-extrabold text-slate-900 tracking-tight leading-tight">
              Stateless Authentication with{" "}
              <span className="text-transparent bg-clip-text bg-gradient-to-r from-indigo-600 to-violet-600">
                Spring Boot & React
              </span>
            </h1>
            <p className="mt-2 text-sm sm:text-base text-slate-600 leading-relaxed">
              Experience seamless user authentication and protected resource
              authorization using JSON Web Tokens (JWT) signed by Spring Security.
            </p>
          </div>

          {/* Quick Architecture Badges */}
          <div className="flex flex-wrap md:flex-col gap-2 shrink-0">
            <div className="flex items-center gap-2 bg-white px-3 py-2 rounded-xl border border-slate-200/80 shadow-xs text-xs font-medium text-slate-700">
              <span className="w-2 h-2 rounded-full bg-emerald-500"></span>
              <span>REST API:</span>
              <code className="text-indigo-600 bg-slate-50 px-1.5 py-0.5 rounded font-mono text-[11px]">
                :8080/api/v1
              </code>
            </div>
            <div className="flex items-center gap-2 bg-white px-3 py-2 rounded-xl border border-slate-200/80 shadow-xs text-xs font-medium text-slate-700">
              <span className="w-2 h-2 rounded-full bg-indigo-500"></span>
              <span>Authorization:</span>
              <code className="text-slate-600 bg-slate-50 px-1.5 py-0.5 rounded font-mono text-[11px]">
                Bearer &lt;token&gt;
              </code>
            </div>
          </div>
        </div>
      </section>

      {/* Main Content Grid */}
      <main className="flex-1 max-w-6xl w-full mx-auto px-4 sm:px-6 py-8">
        <div className="grid grid-cols-1 lg:grid-cols-12 gap-8 items-start">
          {/* Left Column: Form & Session Card (5 cols) */}
          <div className="lg:col-span-5 space-y-6">
            {user ? (
              <>
                <PostForm onCreated={loadPosts} currentUser={user} />

                {/* JWT Inspector Card */}
                <div className="bg-white rounded-2xl shadow-sm border border-slate-200/80 p-5">
                  <div className="flex items-center justify-between mb-3">
                    <h4 className="text-xs font-bold uppercase tracking-wider text-slate-500 flex items-center gap-1.5">
                      <svg
                        className="w-4 h-4 text-indigo-600"
                        fill="none"
                        viewBox="0 0 24 24"
                        stroke="currentColor"
                      >
                        <path
                          strokeLinecap="round"
                          strokeLinejoin="round"
                          strokeWidth="2"
                          d="M15 7a2 2 0 012 2m4 0a6 6 0 01-7.743 5.743L11 17H9v2H7v2H4a1 1 0 01-1-1v-2.586a1 1 0 01.293-.707l5.964-5.964A6 6 0 1121 9z"
                        />
                      </svg>
                      Active JWT Bearer Token
                    </h4>
                    <button
                      type="button"
                      onClick={copyToken}
                      className="text-xs font-semibold text-indigo-600 hover:text-indigo-800 cursor-pointer"
                    >
                      {copied ? "Copied!" : "Copy"}
                    </button>
                  </div>

                  <div className="p-2.5 bg-slate-900 rounded-lg text-emerald-400 font-mono text-[11px] break-all max-h-24 overflow-y-auto leading-relaxed border border-slate-800">
                    {token || "No active token"}
                  </div>

                  {decodedPayload && (
                    <div className="mt-3 pt-3 border-t border-slate-100 text-[11px] text-slate-500 space-y-1">
                      <div className="flex justify-between">
                        <span>Subject (Username):</span>
                        <span className="font-semibold text-slate-700">
                          {decodedPayload.sub || user}
                        </span>
                      </div>
                      {decodedPayload.exp && (
                        <div className="flex justify-between">
                          <span>Expires at:</span>
                          <span className="font-semibold text-slate-700">
                            {new Date(decodedPayload.exp * 1000).toLocaleTimeString()}
                          </span>
                        </div>
                      )}
                    </div>
                  )}
                </div>
              </>
            ) : (
              <>
                <AuthForm onLogin={handleLogin} />

                {/* Workflow Explainer Card */}
                <div className="bg-white rounded-2xl shadow-sm border border-slate-200/80 p-5 space-y-3">
                  <h4 className="text-xs font-bold uppercase tracking-wider text-slate-500">
                    How JWT Security Works
                  </h4>
                  <ul className="text-xs text-slate-600 space-y-2.5">
                    <li className="flex items-start gap-2">
                      <span className="w-5 h-5 rounded-full bg-indigo-50 text-indigo-600 font-bold flex items-center justify-center shrink-0 text-[10px]">
                        1
                      </span>
                      <span>
                        <strong>Register or Log In:</strong> Backend validates
                        credentials with BCrypt and issues a signed JWT token.
                      </span>
                    </li>
                    <li className="flex items-start gap-2">
                      <span className="w-5 h-5 rounded-full bg-indigo-50 text-indigo-600 font-bold flex items-center justify-center shrink-0 text-[10px]">
                        2
                      </span>
                      <span>
                        <strong>Bearer Authorization:</strong> Subsequent HTTP
                        requests automatically include the token in the headers.
                      </span>
                    </li>
                    <li className="flex items-start gap-2">
                      <span className="w-5 h-5 rounded-full bg-indigo-50 text-indigo-600 font-bold flex items-center justify-center shrink-0 text-[10px]">
                        3
                      </span>
                      <span>
                        <strong>Publish Articles:</strong> Post creation is restricted
                        to authenticated users, while all visitors can read posts.
                      </span>
                    </li>
                  </ul>
                </div>
              </>
            )}
          </div>

          {/* Right Column: Posts Feed (7 cols) */}
          <div className="lg:col-span-7 space-y-4">
            {/* Feed Header */}
            <div className="bg-white rounded-2xl shadow-sm border border-slate-200/80 p-5">
              <div className="flex flex-col sm:flex-row sm:items-center justify-between gap-3">
                <div className="flex items-center gap-2.5">
                  <h2 className="text-lg font-bold text-slate-900">
                    Published Posts
                  </h2>
                  <span className="px-2 py-0.5 rounded-full text-xs font-semibold bg-indigo-50 text-indigo-700 border border-indigo-200/60">
                    {posts.length} {posts.length === 1 ? "Article" : "Articles"}
                  </span>
                </div>

                <div className="flex items-center gap-2">
                  {/* Search Filter */}
                  <div className="relative flex-1 sm:w-48">
                    <input
                      type="text"
                      placeholder="Filter posts..."
                      value={searchQuery}
                      onChange={(e) => setSearchQuery(e.target.value)}
                      className="w-full pl-8 pr-2.5 py-1.5 text-xs bg-slate-50 border border-slate-200 rounded-lg focus:outline-none focus:ring-2 focus:ring-indigo-500 focus:bg-white transition-all"
                    />
                    <svg
                      className="w-3.5 h-3.5 text-slate-400 absolute left-2.5 top-1/2 -translate-y-1/2"
                      fill="none"
                      viewBox="0 0 24 24"
                      stroke="currentColor"
                    >
                      <path
                        strokeLinecap="round"
                        strokeLinejoin="round"
                        strokeWidth="2"
                        d="M21 21l-6-6m2-5a7 7 0 11-14 0 7 7 0 0114 0z"
                      />
                    </svg>
                  </div>

                  {/* Refresh Button */}
                  <button
                    type="button"
                    onClick={loadPosts}
                    disabled={loading}
                    title="Reload posts"
                    className="p-1.5 rounded-lg border border-slate-200 text-slate-600 hover:text-indigo-600 hover:bg-slate-50 transition-colors cursor-pointer disabled:opacity-50"
                  >
                    <svg
                      className={`w-4 h-4 ${loading ? "animate-spin text-indigo-600" : ""}`}
                      fill="none"
                      viewBox="0 0 24 24"
                      stroke="currentColor"
                    >
                      <path
                        strokeLinecap="round"
                        strokeLinejoin="round"
                        strokeWidth="2"
                        d="M4 4v5h.582m15.356 2A8.001 8.001 0 004.582 9m0 0H9m11 11v-5h-.581m0 0a8.003 8.003 0 01-15.357-2m15.357 2H15"
                      />
                    </svg>
                  </button>
                </div>
              </div>
            </div>

            {/* Posts List */}
            {loading && posts.length === 0 ? (
              <div className="bg-white rounded-2xl shadow-sm border border-slate-200/80 p-12 text-center">
                <svg
                  className="w-8 h-8 animate-spin text-indigo-600 mx-auto mb-3"
                  viewBox="0 0 24 24"
                  fill="none"
                >
                  <circle
                    className="opacity-25"
                    cx="12"
                    cy="12"
                    r="10"
                    stroke="currentColor"
                    strokeWidth="4"
                  />
                  <path
                    className="opacity-75"
                    fill="currentColor"
                    d="M4 12a8 8 0 018-8v4a4 4 0 00-4 4H4z"
                  />
                </svg>
                <p className="text-sm text-slate-500 font-medium">
                  Loading articles from database...
                </p>
              </div>
            ) : filteredPosts.length === 0 ? (
              <div className="bg-white rounded-2xl shadow-sm border border-slate-200/80 p-12 text-center">
                <div className="w-14 h-14 rounded-2xl bg-indigo-50 text-indigo-600 flex items-center justify-center mx-auto mb-3.5">
                  <svg
                    className="w-7 h-7"
                    fill="none"
                    viewBox="0 0 24 24"
                    stroke="currentColor"
                  >
                    <path
                      strokeLinecap="round"
                      strokeLinejoin="round"
                      strokeWidth="2"
                      d="M19 20H5a2 2 0 01-2-2V6a2 2 0 012-2h10a2 2 0 012 2v1m2 13a2 2 0 01-2-2V7m2 13a2 2 0 002-2V9a2 2 0 00-2-2h-2m-4-3H9M7 16h6M7 8h6v4H7V8z"
                    />
                  </svg>
                </div>
                <h3 className="text-base font-bold text-slate-800">
                  {searchQuery ? "No matching posts found" : "No posts yet"}
                </h3>
                <p className="text-xs text-slate-500 mt-1 max-w-sm mx-auto">
                  {searchQuery
                    ? `No articles match "${searchQuery}". Try a different keyword or clear the search.`
                    : user
                    ? "You are logged in! Be the first to share an article using the form on the left."
                    : "Create an account or sign in to write the first article on this board."}
                </p>
              </div>
            ) : (
              <div className="space-y-3.5">
                {filteredPosts.map((p) => (
                  <article
                    key={p.id}
                    className="bg-white rounded-2xl shadow-sm border border-slate-200/80 p-6 hover:shadow-md transition-shadow duration-200"
                  >
                    <div className="flex items-start justify-between gap-4 mb-3">
                      <h3 className="text-lg font-bold text-slate-900 leading-snug">
                        {p.title}
                      </h3>
                      <span className="text-[11px] font-mono text-slate-400 bg-slate-50 px-2 py-0.5 rounded border border-slate-100 shrink-0">
                        #{p.id}
                      </span>
                    </div>

                    <p className="text-sm text-slate-700 leading-relaxed whitespace-pre-line mb-4">
                      {p.content}
                    </p>

                    <div className="flex items-center justify-between pt-3.5 border-t border-slate-100 text-xs text-slate-500">
                      <div className="flex items-center gap-2">
                        <div className="w-5 h-5 rounded-full bg-gradient-to-tr from-indigo-500 to-violet-500 text-white font-bold text-[10px] flex items-center justify-center uppercase">
                          {p.author ? p.author[0] : "U"}
                        </div>
                        <span className="font-semibold text-slate-700">
                          @{p.author || "anonymous"}
                        </span>
                      </div>

                      <time
                        dateTime={p.createdAt}
                        className="text-slate-400 text-[11px]"
                      >
                        {p.createdAt
                          ? new Date(p.createdAt).toLocaleDateString(undefined, {
                              year: "numeric",
                              month: "short",
                              day: "numeric",
                              hour: "2-digit",
                              minute: "2-digit",
                            })
                          : "Recently"}
                      </time>
                    </div>
                  </article>
                ))}
              </div>
            )}
          </div>
        </div>
      </main>

      {/* Token Modal */}
      {showTokenModal && (
        <div className="fixed inset-0 z-50 flex items-center justify-center bg-slate-900/50 backdrop-blur-xs p-4">
          <div className="bg-white rounded-2xl shadow-xl max-w-lg w-full p-6 border border-slate-200 animate-in fade-in zoom-in-95 duration-150">
            <div className="flex items-center justify-between mb-4">
              <h3 className="text-base font-bold text-slate-800 flex items-center gap-2">
                <svg
                  className="w-5 h-5 text-indigo-600"
                  fill="none"
                  viewBox="0 0 24 24"
                  stroke="currentColor"
                >
                  <path
                    strokeLinecap="round"
                    strokeLinejoin="round"
                    strokeWidth="2"
                    d="M12 15v2m-6 4h12a2 2 0 002-2v-6a2 2 0 00-2-2H6a2 2 0 00-2 2v6a2 2 0 002 2zm10-10V7a4 4 0 00-8 0v4h8z"
                  />
                </svg>
                JWT Bearer Token Details
              </h3>
              <button
                onClick={() => setShowTokenModal(false)}
                className="text-slate-400 hover:text-slate-600 p-1 rounded-lg"
              >
                ✕
              </button>
            </div>

            <div className="space-y-3">
              <div>
                <label className="text-xs font-semibold text-slate-500 uppercase tracking-wider block mb-1">
                  Raw Token
                </label>
                <div className="p-3 bg-slate-900 rounded-lg text-emerald-400 font-mono text-xs break-all max-h-36 overflow-y-auto">
                  {token}
                </div>
              </div>

              {decodedPayload && (
                <div>
                  <label className="text-xs font-semibold text-slate-500 uppercase tracking-wider block mb-1">
                    Decoded Payload (Claims)
                  </label>
                  <pre className="p-3 bg-slate-100 rounded-lg text-slate-700 font-mono text-xs overflow-x-auto">
                    {JSON.stringify(decodedPayload, null, 2)}
                  </pre>
                </div>
              )}
            </div>

            <div className="mt-5 pt-3 border-t border-slate-100 flex justify-end gap-2">
              <button
                onClick={copyToken}
                className="px-4 py-2 bg-indigo-600 text-white rounded-lg text-xs font-semibold hover:bg-indigo-700 transition-colors"
              >
                {copied ? "Copied!" : "Copy Token"}
              </button>
              <button
                onClick={() => setShowTokenModal(false)}
                className="px-4 py-2 bg-slate-100 text-slate-700 rounded-lg text-xs font-semibold hover:bg-slate-200 transition-colors"
              >
                Close
              </button>
            </div>
          </div>
        </div>
      )}

      {/* Footer */}
      <footer className="mt-auto border-t border-slate-200/80 bg-white py-6 px-4 sm:px-6">
        <div className="max-w-6xl mx-auto flex flex-col sm:flex-row items-center justify-between gap-4 text-xs text-slate-500">
          <p>© 2026 JWT Demo Application. Built for HCL Training Module.</p>
          <div className="flex items-center gap-4">
            <span className="flex items-center gap-1.5">
              <span className="w-2 h-2 rounded-full bg-emerald-500"></span>
              Spring Boot 4.1.2
            </span>
            <span className="flex items-center gap-1.5">
              <span className="w-2 h-2 rounded-full bg-indigo-500"></span>
              React 19 + Vite
            </span>
            <span className="flex items-center gap-1.5">
              <span className="w-2 h-2 rounded-full bg-cyan-500"></span>
              Tailwind CSS
            </span>
          </div>
        </div>
      </footer>
    </div>
  );
};

export default App;
