DDM
===
Distributed decision-making system with Jade and Weka


DDM is a framework that combines intelligent agents and Artificial Intelligence traditional algorithms such as classifiers. The central idea of this project is to create a multi-agent system that allows to compare different views into a single one.

Two types of agents:
 - Manager
 - Classifier

Manager Agent processes the dataSets and sends the training data and the data to classify to every classifier instance.
Classifier agent, uses Weka to classify the input data using algorithms such as decision tree, closest neightbour or neuronal network and returns the classification result to the Manager. The manager then creates a decision based on the input from every agent and writes the output with the initial data for comparison purposes.

![](https://raw.github.com/JordiCorbilla/DDM/master/images/diagram.png)


