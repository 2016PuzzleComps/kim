package rushhour.evaluation;

import rushhour.core.*;

public interface Evaluator {
    /**
     * not scaled
     */
    public double eval(Board b);

    /**
     * scaled
     */
    //public int getScore(Board b);
}