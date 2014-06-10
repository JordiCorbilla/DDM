// Distributed Decision making system framework 
// Copyright (c) 2014, Jordi Coll Corbilla
// All rights reserved.
//
// Redistribution and use in source and binary forms, with or without
// modification, are permitted provided that the following conditions are met:
//
// - Redistributions of source code must retain the above copyright notice,
// this list of conditions and the following disclaimer.
// - Redistributions in binary form must reproduce the above copyright notice,
// this list of conditions and the following disclaimer in the documentation
// and/or other materials provided with the distribution.
// - Neither the name of this library nor the names of its contributors may be
// used to endorse or promote products derived from this software without
// specific prior written permission.
//
// THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
// AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
// IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
// ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
// LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
// CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
// SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
// INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
// CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
// ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
// POSSIBILITY OF SUCH DAMAGE.

package ddm.factory.classifiers;

import java.util.Random;

public class ClassifierInstanceFactory {

	public static ClassifierInstance buildClassifier(ClassifierType type) {
		ClassifierInstance classifier = null;
		switch (type) {
		case J48DecisionTree:
			classifier = new J48DecisionTreeClassifier();
			break;
		case KNearestNeighbour:
			classifier = new KNearestNeighbourClassifier();
			break;
		case NeuronalNetwork:
			classifier = new NeuronalNetworkClassifier();
			break;
		default:
			break;
		}
		return classifier;
	}

	public static ClassifierInstance giveMeAClassifier() {
		Random randomGenerator = new Random();
		int randomInt = randomGenerator.nextInt(2);
		ClassifierInstance classifier = null;
		switch (randomInt) {
		case 0:
			classifier = new J48DecisionTreeClassifier();
			break;
		case 1:
			classifier = new KNearestNeighbourClassifier();
			break;
		case 2:
			classifier = new NeuronalNetworkClassifier();
			break;
		}
		return classifier;
	}
}
