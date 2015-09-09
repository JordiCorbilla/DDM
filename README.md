DDM
===
Distributed decision-making system with Jade and Weka

[![Downloads](https://img.shields.io/badge/downloads-100-blue.svg)](https://app.box.com/s/q55d26162yrmsviu9259) [![Stable Release](https://img.shields.io/badge/version-1.0-blue.svg)](https://app.box.com/s/q55d26162yrmsviu9259) [![License](https://img.shields.io/badge/license-MIT-blue.svg)](https://app.box.com/s/q55d26162yrmsviu9259) [![Java version](https://img.shields.io/badge/java-7-red.svg)](https://app.box.com/s/q55d26162yrmsviu9259) [![Weka version](https://img.shields.io/badge/weka-v3.7.10-red.svg)](https://app.box.com/s/q55d26162yrmsviu9259) [![Jade version](https://img.shields.io/badge/jade-v4.3.1-red.svg)](https://app.box.com/s/q55d26162yrmsviu9259) [![Protege version](https://img.shields.io/badge/protege-v43.4.5-red.svg)](https://app.box.com/s/q55d26162yrmsviu9259)

DDM is a framework that combines intelligent agents and Artificial Intelligence traditional algorithms such as classifiers. The central idea of this project is to create a multi-agent system that allows to compare different views into a single one.

Two types of agents:
 - Manager
 - Classifier

Manager Agent processes the dataSets and sends the training data and the data to classify to every classifier instance.

Classifier agent, uses Weka to classify the input data using algorithms such as decision tree (J48), nearest neightbour (IBk) or neuronal networks (MLP) and returns the classification result to the Manager. The manager then creates a decision based on the input from every agent and writes the output with the initial data for comparison purposes.

Diagram below displays the architecture:
![](https://raw.github.com/JordiCorbilla/DDM/master/images/diagram.png)

Technical documentation can be found here: (*Only in Catalan)
  - [DDM Document](http://openaccess.uoc.edu/webapps/o2/bitstream/10609/32761/6/u1032608TFG0614mem%c3%b2ria.pdf)
  - [Bachelor's Thesys presentation](http://openaccess.uoc.edu/webapps/o2/handle/10609/32761)
