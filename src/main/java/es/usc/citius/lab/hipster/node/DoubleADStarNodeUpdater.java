/*
 * Copyright 2013 Centro de Investigación en Tecnoloxías da Información (CITIUS).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package es.usc.citius.lab.hipster.node;

import es.usc.citius.lab.hipster.function.CostFunction;
import es.usc.citius.lab.hipster.function.HeuristicFunction;
import java.util.Map;

/**
 *
 * @author Adrián González Sieira <adrian.gonzalez@usc.es>
 * @since 01-04-2013
 * @version 1.0
 */
public class DoubleADStarNodeUpdater<S> implements ADStarNodeUpdater<S, ADStarNode<S>>{

    private final CostFunction<S, Double> costFunction;
    private final HeuristicFunction<S, Double> heuristicFunction;
    private Double epsilon;

    public DoubleADStarNodeUpdater(CostFunction<S, Double> costFunction, HeuristicFunction<S, Double> heuristicFunction, Double epsilon) {
        this.costFunction = costFunction;
        this.heuristicFunction = heuristicFunction;
        this.epsilon = epsilon;
    }

    public boolean updateConsistent(ADStarNode<S> node, ADStarNode<S> parent, Transition<S> transition) {
        Double accumulatedCost = parent.getG() + this.costFunction.evaluate(transition);
        if(node.g > accumulatedCost){
            node.previousNode = parent;
            node.g = accumulatedCost;
            node.state = transition;
            node.key = new ADStarNode.Key(node.g, node.v, this.heuristicFunction.estimate(transition.to()), this.epsilon);
            return true;
        }
        return false;
    }

    public boolean updateInconsistent(ADStarNode<S> node, Map<Transition<S>, ADStarNode<S>> predecessorsNodes) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void setMaxV(ADStarNode<S> node) {
        node.setV(Double.POSITIVE_INFINITY);
    }

    
}