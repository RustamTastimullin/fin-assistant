package com.host.finassistant.dto;

public class Result<T, E> {

	private final boolean hasResult;
	private final T result;
	private final E errorResult;

	private Result(T result, E errorResult, boolean hasResult) {
		this.hasResult = hasResult;
		if (hasResult) {
			this.result = result;
			this.errorResult = null;
		} else {
			this.errorResult = errorResult;
			this.result = null;
		}

	}

	public static <T, E> Result<T, E> withResult(T result) {
		return new Result(result, null, true);
	}

	public static <T, E> Result<T, E> withErrorResult(E alternativeResult) {
		return new Result(null, alternativeResult, false);
	}

	public boolean hasResult() {
		return this.hasResult;
	}

	public T getResult() {
		return this.result;
	}

	public E getErrorResult() {
		return this.errorResult;
	}

	public boolean equals(Object o) {
		if (o == this) {
			return true;
		} else if (!(o instanceof Result)) {
			return false;
		} else {
			Result<?, ?> other = (Result) o;
			if (!other.canEqual(this)) {
				return false;
			} else if (this.hasResult != other.hasResult) {
				return false;
			} else {
				Object this$result = this.getResult();
				Object other$result = other.getResult();
				if (this$result == null) {
					if (other$result != null) {
						return false;
					}
				} else if (!this$result.equals(other$result)) {
					return false;
				}

				Object this$errorResult = this.getErrorResult();
				Object other$errorResult = other.getErrorResult();
				if (this$errorResult == null) {
					return other$errorResult == null;
				} else {
					return this$errorResult.equals(other$errorResult);
				}
			}
		}
	}

	protected boolean canEqual(Object other) {
		return other instanceof Result;
	}

	public int hashCode() {
		int PRIME = 1;
		int result = 1;
		result = result * 59 + (this.hasResult ? 79 : 97);
		Object $result = this.getResult();
		result = result * 59 + ($result == null ? 43 : $result.hashCode());
		Object $errorResult = this.getErrorResult();
		result = result * 59 + ($errorResult == null ? 43 : $errorResult.hashCode());
		return result;
	}
}
