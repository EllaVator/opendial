// =================================================================                                                                   
// Copyright (C) 2011-2015 Pierre Lison (plison@ifi.uio.no)

// Permission is hereby granted, free of charge, to any person 
// obtaining a copy of this software and associated documentation 
// files (the "Software"), to deal in the Software without restriction, 
// including without limitation the rights to use, copy, modify, merge, 
// publish, distribute, sublicense, and/or sell copies of the Software, 
// and to permit persons to whom the Software is furnished to do so, 
// subject to the following conditions:

// The above copyright notice and this permission notice shall be 
// included in all copies or substantial portions of the Software.

// THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, 
// EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
// MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. 
// IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY 
// CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, 
// TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE 
// SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
// =================================================================                                                                   

package opendial.bn.distribs;

import java.util.Set;

import opendial.bn.values.Value;
import opendial.datastructs.Assignment;

/**
 * Representation of a conditional probability distribution P(X | Y1,...Ym), where X
 * is the "head" random variable for the distribution, and Y1,...Ym are the
 * conditional variables
 * 
 * @author Pierre Lison (plison@ifi.uio.no)
 *
 */
public interface ProbDistribution {

	/**
	 * Returns the name of the random variable
	 * 
	 * @return the name of the random variable
	 */
	public String getVariable();

	/**
	 * Returns the probability P(head|condition), if any is specified. Else, returns
	 * 0.0f.
	 * 
	 * @param condition the conditional assignment for Y1,..., Ym
	 * @param head the value for the random variable
	 * @return the associated probability, if one exists.
	 * @throws RuntimeException if the probability could not be extracted
	 */
	public double getProb(Assignment condition, Value head) throws RuntimeException;

	/**
	 * Returns the (unconditional) probability distribution associated with the
	 * conditional assignment provided as argument.
	 * 
	 * @param condition the conditional assignment on Y1,...Ym
	 * @return the independent probability distribution on X.
	 * @throws RuntimeException if the unconditional distribution could not be extracted
	 */
	public IndependentProbDistribution getProbDistrib(Assignment condition)
			throws RuntimeException;

	/**
	 * Returns a sample value for the distribution given a particular conditional
	 * assignment.
	 * 
	 * @param condition the conditional assignment for Y1,...,Ym
	 * @return the sampled values for the random variable
	 * @throws RuntimeException if no value(s) could be sampled
	 */
	public Value sample(Assignment condition) throws RuntimeException;

	/**
	 * Returns the set of possible values for the distribution. If the distribution
	 * is continuous, the method returns a discretised set.
	 * 
	 * @throws RuntimeException if the values could not be extracted
	 */
	public Set<Value> getValues() throws RuntimeException;

	/**
	 * Returns a new probability distribution that is the posterior of the current
	 * distribution, given the conditional assignment as argument.
	 * 
	 * @param condition an assignment of values to (a subset of) the conditional
	 *            variables
	 * @return the posterior distribution
	 * @throws RuntimeException if the posterior could not be computed.
	 */
	public abstract ProbDistribution getPosterior(Assignment condition)
			throws RuntimeException;

	/**
	 * Prunes values whose frequency in the distribution is lower than the given
	 * threshold.
	 * 
	 * @param threshold the threshold to apply for the pruning
	 * @return true if at least one value has been removed, false otherwise
	 */
	public boolean pruneValues(double threshold);

	/**
	 * Creates a copy of the probability distribution
	 * 
	 * @return the copy
	 */
	public ProbDistribution copy();

	/**
	 * Changes the variable name in the distribution
	 * 
	 * @param oldId the old variable label
	 * @param newId the new variable label
	 */
	public void modifyVariableId(String oldId, String newId);

}
