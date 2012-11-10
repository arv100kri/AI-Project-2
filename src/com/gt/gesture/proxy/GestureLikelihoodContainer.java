package com.gt.gesture.proxy;

public class GestureLikelihoodContainer implements Comparable<GestureLikelihoodContainer> {

	private Double likelihood;
	String gesture;
	
	public GestureLikelihoodContainer(Double prob, String name) {
		this.likelihood = prob;
		this.gesture = name;
	}
	
	@Override
	public int compareTo(GestureLikelihoodContainer other) {
		return -this.likelihood.compareTo(other.likelihood); //return - in order to sort descending (see Array.sort Javadocs)
	}
	
	public Double getLikelihood() {
		return this.likelihood;
	}
	
	public String getGesture() {
		return this.gesture;
	}
	
	public String toString() {
		return this.gesture + ": " + this.likelihood;
	}
	
}