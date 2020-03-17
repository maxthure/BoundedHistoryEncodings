package results;

import java.util.HashMap;
import java.util.HashSet;

/**
 * This class represents a normal form for answers where sets of answer terms are mapped to sets of variables
 */
public class DataNF extends HashMap<HashSet<Variable>, HashSet<HashSet<AnswerTerm>>> implements AnswerTerm {
}
