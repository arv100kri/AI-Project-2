6601proj2
=========

Recognition of Continuous Mouse Gesture Sequences

Code built upon project from https://code.google.com/p/mouse-gesture-recognition-java-hidden-markov-model/ written by https://code.google.com/u/104502199478319091201/

Changes from original Java codebase:

src/com/gt/db:
  -> ObjectIOFileDataBase.java: Added cross-platform file I/O and added file filter to ignore version control directories (e.g., .svn).
  -> TrainingTestingDataFiles.java: Cross-platform file I/O

src/com/gt/gesture/features:
  -> GestureFeatureExtractor.java: Removed extraneous console logging statements
  -> RawFeatureExtractor.java:  Added total time taken to produce RawFeature (for segmentation routine)

src/com/gt/gesture/mouseCapture
  -> DataCapturePanel.java: removed extraneous console logging statements

src/com/gt/gesture/proxy
  -> GestureLikelihoodContainer.java: Added new class to capture gesture recognition metrics
 -> OperationMediator.java: 1) Use GestureLikelihoodContainer class to rank order HMM matches, 2) added iterativeSegmentation() routine and related methods: rawFeatureSplit, sliceFeature, scanForGesture(), class DurationGestureInfoContainer, calculateTrainingGestureDurationStats() for metrics, 

src/com/gt/hmm/classify/vq
  -> Codebook.java: Removed extraneous console logging statements
  -> CodeBookDictionary.java: Removed extraneous console logging statements

src/com/gt/UI
  -> MainApp.java: 1) Added Swing worker threads to generate codebook and HMMs in the background; 2) Modified recognize button action to call iterative segmentation routine and display the best match of gestures
