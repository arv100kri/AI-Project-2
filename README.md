6601proj2
=========

Recognition of Unistroke Gesture Sequences

Our code is extended from the $1 Unistroke Recognizer codebase: http://depts.washington.edu/aimgroup/proj/dollar/

To be precise, we downloaded and modified the dollar.html and dollar.js from that website (available to us via BSD license). Thus, a diff of our files against
the dollar.html and dollar.js files from the website will output all the code that we developed.

The following routines in dollar.js are our routines:

1. DTWForDistanceSegmentation
3. LearnMatch
4. DynamicTimeWarp
5. FindClosestPoint
6. DTWForDistance
7. optimizeDistance
8. GetStrokesByName
9. GetGestures
10. EuclideanDistanceFunc
11. DrawCorners
12. CopyPointArray
13. CopyArrayPart
14. CopyArrayUntilEnd
15. TranslateUnistroke
16. ResultSorterFunction
17. UniformScaleTo

Additionally, the following methods are not used in our final segmentation routine, and can be considered "construction dust" (but are written by us):

1. multiRecognize
2. strokeSimilarity
3. SegmentationAnalysis
4. MinGestureLength
5. FindAllPossibleMatches
6. FindGestureMatchesInSequence
7. IncrementalSearch
8. flattenResults
9. FindGestures
10. OptimizeWindowsize
11. AddNewResults
12. ThreshholdFilter
13. DTWByStroke
14. DTWSequence
15. DTW
16. ScoreTarget
17. StrokeMatchTest
18. ConcatUnistrokes
19. ClearCanvas
20. DrawStroke
21. DescendingResultSorterFunction
22. CopyResultSeq


Additionally, we modified some functions of the $1 Unistroke Recognizer codebase:

1. AddGesture

Extension of $1 Recognizer

The project's actual source files are in the dtw folder. The HTML file is the UI for the project