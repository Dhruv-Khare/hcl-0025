import { useActionState, useRef } from "react";
import SubmitButton from "./SubmitButton";
import api from "./api";

const PostForm = ({ onCreated, currentUser }) => {
  const formRef = useRef(null);

  const submitAction = async (prev, formData) => {
    const title = formData.get("title")?.trim();
    const content = formData.get("content")?.trim();

    if (!title || !content) {
      return {
        error: "Both title and content are required to publish a post.",
        msg: "",
        title: title || "",
        content: content || "",
      };
    }

    try {
      await api.post("/posts", { title, content });
      await onCreated?.();
      formRef.current?.reset();

      return {
        error: null,
        msg: "Post published successfully!",
        title: "",
        content: "",
      };
    } catch (error) {
      return {
        error:
          error.response?.data?.error ||
          error.response?.data?.message ||
          "Failed to create post. Please ensure you are logged in and try again.",
        msg: "",
        title,
        content,
      };
    }
  };

  const [state, formAction, isPending] = useActionState(submitAction, {
    error: null,
    msg: "",
    title: "",
    content: "",
  });

  return (
    <div className="bg-white rounded-2xl shadow-sm border border-slate-200/80 p-6 md:p-7">
      <div className="flex items-center justify-between mb-5">
        <div>
          <h3 className="text-xl font-bold text-slate-800 flex items-center gap-2">
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
                d="M11 5H6a2 2 0 00-2 2v11a2 2 0 002 2h11a2 2 0 002-2v-5m-1.414-9.414a2 2 0 112.828 2.828L11.828 15H9v-2.828l8.586-8.586z"
              />
            </svg>
            Publish New Article
          </h3>
          <p className="text-xs text-slate-500 mt-1">
            Author: <span className="font-semibold text-indigo-600">@{currentUser}</span> (verified via JWT)
          </p>
        </div>
        <span className="inline-flex items-center gap-1 px-2.5 py-1 rounded-full text-xs font-medium bg-emerald-50 text-emerald-700 border border-emerald-200">
          <span className="w-1.5 h-1.5 rounded-full bg-emerald-500 animate-pulse"></span>
          Authenticated
        </span>
      </div>

      <form ref={formRef} action={formAction} className="space-y-4">
        {/* Title */}
        <div>
          <label
            htmlFor="title"
            className="block text-xs font-semibold uppercase tracking-wider text-slate-600 mb-1.5"
          >
            Post Title
          </label>
          <input
            type="text"
            name="title"
            id="title"
            placeholder="e.g. Understanding Stateless Authentication with Spring Security"
            defaultValue={state.title}
            required
            disabled={isPending}
            className="w-full px-3.5 py-2.5 bg-slate-50 border border-slate-300 rounded-lg text-sm text-slate-800 placeholder-slate-400 focus:outline-none focus:ring-2 focus:ring-indigo-500 focus:bg-white transition-all disabled:opacity-60"
          />
        </div>

        {/* Content */}
        <div>
          <label
            htmlFor="content"
            className="block text-xs font-semibold uppercase tracking-wider text-slate-600 mb-1.5"
          >
            Content
          </label>
          <textarea
            name="content"
            id="content"
            placeholder="Share your thoughts, tutorial, or insights..."
            rows={5}
            defaultValue={state.content}
            required
            disabled={isPending}
            className="w-full px-3.5 py-2.5 bg-slate-50 border border-slate-300 rounded-lg text-sm text-slate-800 placeholder-slate-400 focus:outline-none focus:ring-2 focus:ring-indigo-500 focus:bg-white transition-all resize-y disabled:opacity-60"
          ></textarea>
        </div>

        {/* Feedback Messages */}
        {state.msg && (
          <div
            role="status"
            className="p-3 rounded-lg bg-emerald-50 border border-emerald-200 text-emerald-800 text-xs flex items-center gap-2"
          >
            <svg
              className="w-4 h-4 text-emerald-600 shrink-0"
              fill="currentColor"
              viewBox="0 0 20 20"
            >
              <path
                fillRule="evenodd"
                d="M10 18a8 8 0 100-16 8 8 0 000 16zm3.707-9.293a1 1 0 00-1.414-1.414L9 10.586 7.707 9.293a1 1 0 00-1.414 1.414l2 2a1 1 0 001.414 0l4-4z"
                clipRule="evenodd"
              />
            </svg>
            <span>{state.msg}</span>
          </div>
        )}

        {state.error && (
          <div
            role="status"
            className="p-3 rounded-lg bg-rose-50 border border-rose-200 text-rose-800 text-xs flex items-center gap-2"
          >
            <svg
              className="w-4 h-4 text-rose-600 shrink-0"
              fill="currentColor"
              viewBox="0 0 20 20"
            >
              <path
                fillRule="evenodd"
                d="M18 10a8 8 0 11-16 0 8 8 0 0116 0zm-7 4a1 1 0 11-2 0 1 1 0 012 0zm-1-9a1 1 0 00-1 1v4a1 1 0 102 0V6a1 1 0 00-1-1z"
                clipRule="evenodd"
              />
            </svg>
            <span>{state.error}</span>
          </div>
        )}

        {/* Submit */}
        <div className="pt-2">
          <SubmitButton
            label="Publish Post"
            pendingLabel="Publishing Securely..."
          />
        </div>
      </form>
    </div>
  );
};

export default PostForm;
