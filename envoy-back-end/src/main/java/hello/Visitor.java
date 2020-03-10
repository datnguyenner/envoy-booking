package hello;

import java.io.Serializable;

public class Visitor implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String fn;
	private String ln;
	private String notes;

	private long signOutTime;

	public Visitor(String fn, String ln, String notes, long signOutTime) {
		this.fn = fn;
		this.ln = ln;
		this.notes = notes;
		this.signOutTime = signOutTime;
	}

	public String getFn() {
		return fn;
	}

	public void setFn(String fn) {
		this.fn = fn;
	}

	public void setLn(String ln) {
		this.ln = ln;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public void setSignOutTime(long signOutTime) {
		this.signOutTime = signOutTime;
	}

	public String getLn() {
		return ln;
	}

	public String getNotes() {
		return notes;
	}

	public long getSignOutTime() {
		return signOutTime;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fn == null) ? 0 : fn.hashCode());
		result = prime * result + ((ln == null) ? 0 : ln.hashCode());
		result = prime * result + ((notes == null) ? 0 : notes.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Visitor other = (Visitor) obj;
		if (fn == null) {
			if (other.fn != null)
				return false;
		} else if (!fn.equals(other.fn))
			return false;
		if (ln == null) {
			if (other.ln != null)
				return false;
		} else if (!ln.equals(other.ln))
			return false;
		if (notes == null) {
			if (other.notes != null)
				return false;
		} else if (!notes.equals(other.notes))
			return false;
		return true;
	}
	

}
