import { useFormStatus } from "react-dom";

const SubmitButton = ({
  label = "Submit",
  pendingLabel = "Submitting...",
  className = "",
}) => {
  const { pending } = useFormStatus();

  return (
    <button
      type="submit"
      disabled={pending}
      className={`w-full flex items-center justify-center gap-2
        px-5 py-2.5 rounded-lg font-medium text-white text-sm
        shadow-sm transition-all duration-200 cursor-pointer
        focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500
        ${
          pending
            ? "bg-indigo-400 cursor-not-allowed opacity-80"
            : "bg-indigo-600 hover:bg-indigo-700 active:scale-[0.99] hover:shadow"
        } ${className}`}
    >
      {pending && (
        <svg
          className="w-4 h-4 animate-spin text-white"
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
      )}

      <span>{pending ? pendingLabel : label}</span>
    </button>
  );
};

export default SubmitButton;
