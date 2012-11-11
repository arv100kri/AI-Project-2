/*
  Please feel free to use/modify this class. 
  If you give me credit by keeping this information or
  by sending me an email before using it or by reporting bugs , i will be happy.
  Email : gtiwari333@gmail.com,
  Blog : http://ganeshtiwaridotcomdotnp.blogspot.com/ 
 */
package com.gt.gesture.proxy;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.gt.db.DBMode;
import com.gt.db.Database;
import com.gt.db.Model;
import com.gt.db.ObjectIOFileDataBase;
import com.gt.gesture.features.GestureFeature;
import com.gt.gesture.features.GestureFeatureExtractor;
import com.gt.gesture.features.RawFeature;
import com.gt.hmm.HiddenMarkov;
import com.gt.hmm.classify.vq.Codebook;
import com.gt.hmm.classify.vq.Points;

/**
 * A mediator class to access all HMM/VQ algorithms<br>
 * 
 * @author Ganesh
 * 
 */
public class OperationMediator {
	private GestureFeatureExtractor featureExtractor;
	private Database database;
	private List<GestureFeature> allFeaturesList;
	private Codebook codebook;
	// FIXME: what is appropriate value for these
	private final int NUM_STATES = 4;
	// FIXME: same in Codebook.java class
	private final int NUM_SYMBOLS = 64;

	public OperationMediator() {
		database = new ObjectIOFileDataBase();
		allFeaturesList = new ArrayList<GestureFeature>();

	}

	public boolean saveCaptured(RawFeature rf, String gestName) {
		boolean operationSuccess = false;
		database.setMode(DBMode.TRAINDATA);
		database.saveModel(rf, gestName);
		operationSuccess = true;
		return operationSuccess;
	}

	public boolean verify(String gesture, RawFeature rf) {
		GestureLikelihoodContainer[] rankOrder = recognizeGesture(rf);
		String bestMatched = rankOrder[0].gesture;
		System.out.println(Arrays.toString(rankOrder));
		return (gesture.equalsIgnoreCase(bestMatched));
	}

	public String[] readRegGestureModels() {
		database.setMode(DBMode.HMM_MODEL);
		return (database.getRegisteredModelNames());
	}

	public String[] readTrainData1D() {
		database.setMode(DBMode.TRAINDATA);
		return (database.getRegisteredModelNames());

	}

	public GestureLikelihoodContainer[] iterativeSegmentation(RawFeature rf) {
		/*
		 * In our approach, we first split the multi-gesture data stream
		 * temporally into N sub-streams (each corresponding to an individual
		 * gesture) of identical duration equal to the mean duration of gestures
		 * in the training set. The duration of each sub-stream can be
		 * conceptualized as a “window” overlaid on the complete data stream; N
		 * sub-streams corre- spond to N windows. After configuring the initial
		 * “windowset", we calculate a “window set score", which is the mean of
		 * all the individual window scores; each window score, in turn, is
		 * equal to the maximum of the probabilities of a match between the
		 * window data and each trained HMM. We subsequently iteratively
		 * increase the window set score by independently increasing or
		 * decreasing the duration of each window by a delta based on the time
		 * variance of ges- tures in the training set. If the window score
		 * increased between iterations k and k-1, we repeat the previous ac-
		 * tion; otherwise, we try the alternate action. Our routine terminates
		 * when the delta change reaches zero.
		 */
		double totalGestureDuration = rf.getTotalTime();
		System.out.println("Total gesture duration: " + totalGestureDuration);
		double[] stats = calculateTrainingGestureDurationStats();
		double mean = stats[0];
		double std = stats[1];
		System.out.println("Mean gesture duration: " + mean + ", std dev: " + std);
//		int initial_estimate_gesture_count = (int)Math.floor(rf.getTotalTime() / duration);
		List<GestureLikelihoodContainer> gestureData = new ArrayList<GestureLikelihoodContainer>();
		
		double[] durations = new double[] { mean - 3*std, mean-2*std, mean-std, mean, mean+std, mean+2*std, mean+3*std }; //99.7% of observations fall within 3 stddev of mean for normal curve


		
		boolean continueItr = false;
		RawFeature curFeature = rf;
		if (curFeature.getTotalTime() >= (mean - 3*std)) {
			continueItr = true;
		}
//		boolean possibleGesture = false;
//		if (Math.abs((curFeature.getTotalTime() - mean)) < 3*std) { possibleGesture = true; }
//		if ((curFeature != null) && (possibleGesture)) {
//			continueItr = true;
//		}

		while (continueItr) {
			DurationGestureInfoContainer cont = scanForGesture(durations, curFeature);
			curFeature = cont.getRemainder();
			gestureData.add(cont.getGestureInfo());
			continueItr = false;
//			possibleGesture = false;
			if (curFeature != null) {
				if (curFeature.getTotalTime() >= (mean - 3*std)) {
//					possibleGesture = true;
					continueItr = true;
				}
//				if (Math.abs((curFeature.getTotalTime() - 3*std)) < mean) { possibleGesture = true; }
//				if (possibleGesture) {
//					continueItr = true;
//				}
			} else {
				continueItr = false;
			}
			
		}
		
		return gestureData.toArray(new GestureLikelihoodContainer[0]);
		
//		RawFeature[] rfSplit = rawFeatureSplit(meanDuration, rf);
		//filter data... throw out RawFeatures that are clearly not a real gesture
//		for (RawFeature feature : rfSplit) {
//			if (feature.)
//		}
//		GestureLikelihoodContainer[] gestureSeqGuess = new GestureLikelihoodContainer[rfSplit.length];
		//determine "best" duration for each individual gesture
		
//		for (int i = 0; i < rfSplit.length; i++) {
//			GestureLikelihoodContainer[] allHMMProbs = recognizeGesture(rfSplit[i]);
//			//0th index contains best guess...
//			gestureSeqGuess[i] = allHMMProbs[0];
//			//print out top three choices
//			int count = 3;
//			GestureLikelihoodContainer[] topThree = Arrays.copyOf(allHMMProbs, count);
//			System.out.println("Best guess for gesture " + i + ": " + Arrays.toString(topThree));
//		}
//		double score = windowScore(gestureSeqGuess);
//		System.out.println(score);
//		return gestureSeqGuess;
	}
	
	/*
	private double windowScore(GestureLikelihoodContainer[] probs) {
		double meanProbs = 0.0d;
		int count = probs.length;
		for (int i = 0; i < count; i++) {
			double prob = probs[i].getLikelihood();
			meanProbs += prob;
		}
		meanProbs = meanProbs / count;
		return meanProbs;
	}
	*/
	
	private RawFeature[] rawFeatureSplit(double duration, RawFeature feature) {
		List<RawFeature> features = new ArrayList<RawFeature>();
		//assign data points from original feature to each sub-feature in the sequence
		boolean done = false;
		while (!done) {
			RawFeature[] slices = sliceFeature(duration, feature);
			if (slices == null) {
				done = true;
			} else if (slices[1] == null) {
				features.add(slices[0]);
				done = true;
			} else {
				features.add(slices[0]);
				feature = slices[1];
			}
		}
		return features.toArray(new RawFeature[0]);
	}
	
	private RawFeature[] sliceFeature(double targetTime, RawFeature orig) {
		double[] timePoints = orig.getCurTime();
		Point[] drawPoints = orig.getDrawPoint();
		int count = timePoints.length;
		double total_duration = timePoints[count-1] - timePoints[0];
		if (total_duration < targetTime) {
			System.out.println("Gesture duration = " + total_duration);
			return new RawFeature[] { orig, null };
		}
		
		double begin_time = timePoints[0];
		int index = 0;
		for (int i = 0; i < count; i++) {
			double cur = timePoints[i];
			double timeDiff = cur - begin_time;
			if (timeDiff > targetTime) {
				index = i;
				System.out.println("Gesture duration = " + timeDiff);
				break;
			}
		}

		int numRemainingPoints = timePoints.length - index;
		if (numRemainingPoints < GestureFeatureExtractor.SAMPLE_PER_FRAME) { //need enough data points for at least one sample
			double[] newRFTimePoints = Arrays.copyOf(timePoints, index);
			Point[] newRFDrawPoints = Arrays.copyOf(drawPoints, index);
			
			RawFeature newRF = new RawFeature(newRFTimePoints, newRFDrawPoints);
			return new RawFeature[] { newRF, null };
		} else {
		double[] newRFTimePoints = Arrays.copyOf(timePoints, index);
		double[] remainderRFTimePoints = Arrays.copyOfRange(timePoints, index, timePoints.length);
		Point[] newRFDrawPoints = Arrays.copyOf(drawPoints, index);
		Point[] remainderRFDrawPoints = Arrays.copyOfRange(drawPoints, index, drawPoints.length);
		
		RawFeature newRF = new RawFeature(newRFTimePoints, newRFDrawPoints);
		RawFeature remainderRF = new RawFeature(remainderRFTimePoints, remainderRFDrawPoints);
		return new RawFeature[] { newRF, remainderRF };
		}
	}
	
	public DurationGestureInfoContainer scanForGesture(double[] durations, RawFeature feature) {
		
		for (int i = 0; i < durations.length; i++) {
			if (durations[i] < 0) { durations[i] = 0; }
		}
		List<RawFeature[]> gestureOptions = new ArrayList<RawFeature[]>();
		for (int i = 0; i < durations.length; i++) {
			RawFeature[] rfSplit = sliceFeature(durations[i], feature);
			gestureOptions.add(rfSplit);
		}
		
		Map<String, Integer> gestureVotes = new HashMap<String, Integer>();
		Map<Integer, GestureLikelihoodContainer[]> durationLikelihoods = new HashMap<Integer, GestureLikelihoodContainer[]>();
		for (int i = 0; i < durations.length; i++) {
			//recognize gesture if not null
			RawFeature gesture = gestureOptions.get(i)[0];
			if (gesture == null) {
				 break;
			}
			GestureLikelihoodContainer[] allHMMProbs = recognizeGesture(gesture);
			System.out.println("duration: " + durations[i] + ", HMM probs: " + Arrays.toString(allHMMProbs));
			int topGesturesToCount = allHMMProbs.length < 5 ? allHMMProbs.length : 5;
			durationLikelihoods.put(i, Arrays.copyOf(allHMMProbs, topGesturesToCount));
			
			//add votes for top five guesses
			for (int j = 0; j < topGesturesToCount; j++) {
				GestureLikelihoodContainer cur = allHMMProbs[j];
				Integer entry = gestureVotes.get(cur.getGesture());
				if (entry != null) {
					int weight = (int)Math.pow(topGesturesToCount - j, 2);
					int newScore = entry + weight;
					gestureVotes.put(cur.getGesture(), newScore);
				} else {
					int score = topGesturesToCount - j;
					gestureVotes.put(cur.getGesture(), score);
				}
			}
			
			//0th index contains best guess...
//			gestureSeqGuess[i] = allHMMProbs[0];
			//print out top three choices
//			int count = 3;
//			GestureLikelihoodContainer[] topThree = Arrays.copyOf(allHMMProbs, count);
//			System.out.println("Best guess for gesture " + i + ": " + Arrays.toString(topThree));
		}
		
		//review votes and pick top gesture
		Set<String> gestureNames = gestureVotes.keySet();
		Iterator<String> gestureNamesItr = gestureNames.iterator();
		int highestVoteCount = 0;
		String topGesture = null;
		while (gestureNamesItr.hasNext()) {
			String curGestureName = gestureNamesItr.next();
			Integer numVotes = gestureVotes.get(curGestureName);
			if (numVotes > highestVoteCount) {
				highestVoteCount = numVotes;
				topGesture = curGestureName;
			}
		}
		
		//then find duration that corresponds to the top gesture
		double bestProbDiffNormalized = Double.NEGATIVE_INFINITY;
		int durationNumber = -1;
		GestureLikelihoodContainer topGestureInfo = null;
		for (int i = 0; i < durations.length; i++) {
			GestureLikelihoodContainer[] topGesturesForDuration = durationLikelihoods.get(i);
			for (int j = 0; j < topGesturesForDuration.length; j++) {
				GestureLikelihoodContainer probOfGesture = topGesturesForDuration[j]; 
				String curGesture = probOfGesture.getGesture();
				Double prob = probOfGesture.getLikelihood();
				Double probNext = (j+1 < topGesturesForDuration.length) ? topGesturesForDuration[j+1].getLikelihood() : null;
				Double probDiff = (probNext != null) ? (Math.abs((prob - probNext)/(prob+probNext)) ): null;
				if ((curGesture.equals(topGesture)) && ((probDiff != null) && (probDiff > bestProbDiffNormalized))) {
					bestProbDiffNormalized = probDiff;
					durationNumber = i;
					topGestureInfo = probOfGesture;
				}
			}
		}
		
		//now we know the duration and gesture for the best match
		double optimalDuration = durations[durationNumber];
		RawFeature remainder = gestureOptions.get(durationNumber)[1];
		DurationGestureInfoContainer cont = new DurationGestureInfoContainer(topGestureInfo, optimalDuration, remainder);
		return cont;
	}
	static class DurationGestureInfoContainer {
		private GestureLikelihoodContainer gestureInfo;
		private double duration;
		private RawFeature remainingFeature;
		
		public DurationGestureInfoContainer(GestureLikelihoodContainer gestureInfo, double duration, RawFeature left) {
			this.gestureInfo = gestureInfo;
			this.duration = duration;
			this.remainingFeature = left;
		}
		
		public double getDuration() {
			return this.duration;
		}
		
		public GestureLikelihoodContainer getGestureInfo() {
			return this.gestureInfo;
		}
		
		public RawFeature getRemainder() {
			return this.remainingFeature;
		}
	}
	private double[] calculateTrainingGestureDurationStats() {
		database.setMode(DBMode.TRAINDATA);
		Model[][] regModels = database.readAllDataofCurrentMode();
		int numGestures = regModels.length;
		double[] gestureMeanDurations = new double[regModels.length];
		double[] gestureStdDev= new double[regModels.length];
		for (int i = 0; i < numGestures; i++) {
			double sumDuration = 0.0d;
			for (int j = 0; j < regModels[i].length; j++) {
				RawFeature rf = (RawFeature) regModels[i][j];
				double totalTime = rf.getTotalTime();
				sumDuration += totalTime;
			}
			gestureMeanDurations[i] = sumDuration / regModels[i].length;
		}

		//calc std dev for each gesture as well
		for (int i = 0; i < numGestures; i++) {
			double sumVariances = 0.0d;
			for (int j = 0; j < regModels[i].length; j++) {
				RawFeature rf = (RawFeature) regModels[i][j];
				double totalTime = rf.getTotalTime();
				double diffFromMean = (totalTime - gestureMeanDurations[i]);
				double variance = Math.pow(diffFromMean, 2);
				sumVariances += variance;
			}
			gestureStdDev[i] = Math.sqrt(sumVariances / regModels[i].length);
		}
		
		double overallMeanDuration = 0.0d;
		for (int i = 0; i < numGestures; i++) {
			overallMeanDuration += gestureMeanDurations[i];
		}
		overallMeanDuration = overallMeanDuration / gestureMeanDurations.length;
		
		double overallStdDev = 0.0d;
		for (int i = 0; i < numGestures; i++) {
			overallStdDev += gestureStdDev[i];
		}
		overallStdDev = overallStdDev / numGestures;
		
		
		//calculate std dev too
//		double[] variances = new double[numGestures];
//		for (int i = 0; i < numGestures; i++) {
//			double diffFromMean = (gestureMeanDurations[i] - overallMeanDuration);
//			variances[i] = Math.pow(diffFromMean, 2);
//		}
//		
//		double sumVariances = 0.0d;
//		for (int i = 0; i < numGestures; i++) {
//			sumVariances += variances[i];
//		}
//		double variance = sumVariances / numGestures;
//		
//		double stdDev = Math.sqrt(variance);
		
		return new double[] { overallMeanDuration, overallStdDev };
	}

	/**
	 * First quantizes the feature vector against codebook, and then runs
	 * viterbi decoding
	 * 
	 * @param rf
	 * @return
	 */
	public GestureLikelihoodContainer[] recognizeGesture(RawFeature rf) {
		codebook = new Codebook();
		GestureFeature[] gestureFeatures = getFeature(rf);
		Points[] pts = getPointsFromFeatureVector(gestureFeatures);
		int[] quantized = codebook.quantize(pts);
		String[] regGestures = readRegGestureModels();
		HiddenMarkov[] hmms = new HiddenMarkov[regGestures.length];
		// read hmms
		for (int i = 0; i < hmms.length; i++) {
			hmms[i] = new HiddenMarkov(regGestures[i]);
		}
		// find likelihood by viterbi decoding of quantized seq
		Double[] likelihoods = new Double[regGestures.length];
		for (int j = 0; j < likelihoods.length; j++) {
			likelihoods[j] = hmms[j].viterbi(quantized);
			// System.out.println("Gesture: " + regGestures[j] + ", prob: "
			// + likelihoods[j]);
		}
		// find the largest likelihood
		double highest = Double.NEGATIVE_INFINITY;
		int wordIndex = -1;
		for (int j = 0; j < regGestures.length; j++) {
			if (likelihoods[j] > highest) {
				highest = likelihoods[j];
				wordIndex = j;
			}
		}
		// sort likelihoods to rank-order gestures
		GestureLikelihoodContainer[] rankOrder = new GestureLikelihoodContainer[regGestures.length];
		for (int i = 0; i < regGestures.length; i++) {
			rankOrder[i] = new GestureLikelihoodContainer(likelihoods[i],
					regGestures[i]);
		}

		Arrays.sort(rankOrder);

		return rankOrder;
		// best matched

		// return regGestures[wordIndex];
	}

	private GestureFeature[] getFeature(RawFeature rf) {
		featureExtractor = new GestureFeatureExtractor(rf);
		return featureExtractor.getExtractedFeature();
	}

	private Points[] getPointsFromFeatureVector(GestureFeature[] gestureFeatures) {
		// get Points object from all feature vector
		Points pts[] = new Points[gestureFeatures.length];
		for (int j = 0; j < gestureFeatures.length; j++) {
			pts[j] = new Points(gestureFeatures[j].getFeatureVector());
		}
		return pts;
	}

	/**
	 * Generates codebook by clustering all features in training set raw data
	 * 
	 * @return
	 */
	public boolean generateCodeBook() {
		boolean operationSuccess = false;
		database.setMode(DBMode.TRAINDATA);
		Model[][] regModels = database.readAllDataofCurrentMode();
		int totalFrames = 0;
		allFeaturesList.clear();
		// extract single list of all features
		for (int i = 0; i < regModels.length; i++) {
			for (int j = 0; j < regModels[i].length; j++) {
				GestureFeature[] gf = getFeature((RawFeature) regModels[i][j]);
				for (int k = 0; k < gf.length; k++) {
					allFeaturesList.add(gf[k]);
					totalFrames++;
				}
			}

		}//
			// single array from list
		GestureFeature[] allFeaturesArr = new GestureFeature[totalFrames];
		allFeaturesArr = allFeaturesList.toArray(new GestureFeature[0]);
		// clustering is done automatically after callng constructor
		Codebook cbk = new Codebook(getPointsFromFeatureVector(allFeaturesArr));
		cbk.saveToFile();
		operationSuccess = true;
		return operationSuccess;

	}

	/**
	 * Performs hmm train for all training data set.<br>
	 * First quantizes the feature vector against codebook, and then train
	 * baum-welch
	 * 
	 * @return
	 */
	public boolean trainHMM() {
		boolean operationSuccess = false;
		codebook = new Codebook();
		database.setMode(DBMode.TRAINDATA);
		Model[][] regModels = database.readAllDataofCurrentMode();
		String[] gestName = database.getRegisteredModelNames();
		int quantizedSeq[][];
		HiddenMarkov mkv = new HiddenMarkov(NUM_STATES, NUM_SYMBOLS);
		// for each gesture
		for (int i = 0; i < regModels.length; i++) {
			operationSuccess = false;
			// for each train sample of current gesture
			quantizedSeq = new int[regModels[i].length][];
			for (int j = 0; j < regModels[i].length; j++) {
				GestureFeature[] gf = getFeature((RawFeature) regModels[i][j]);
				Points[] pts = getPointsFromFeatureVector(gf);
				quantizedSeq[j] = codebook.quantize(pts);
			}
			mkv.setTrainSeq(quantizedSeq);
			System.out.println("Training HMM " + gestName[i]);
			mkv.train();
			System.out.println("Saving HMM " + gestName[i]);
			mkv.save(gestName[i]);
			operationSuccess = true;
		}
		return operationSuccess;
	}
}
