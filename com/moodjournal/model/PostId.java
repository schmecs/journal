package com.moodjournal.model;

	public class PostId {
		private int count = 0; 
		private int postId;

		public void PostId() {
	    	count++;
	    	postId = count; 
		}

		@Override
		public String toString() {
		return Integer.toString(postId);
		}
	}