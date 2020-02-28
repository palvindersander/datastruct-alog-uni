package dsa_assignment3;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.nullValue;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

public class reuben
{
	private static final Logger logger        = Logger.getLogger(PLTreeNodeTest.class);

	@Rule
	public Timeout              globalTimeout = new Timeout(2000, TimeUnit.MILLISECONDS);

	@Test
	public void testCheckStudentIdentification()
	{
		assertNotEquals("Please update the studentID field in PLTreeNode.java with your student id number", //
				"MY STUDENT ID", PLTreeNode.getStudentID());
		assertNotEquals("Please update the studentName field in PLTreeNode.java with your name", //
				"MY NAME", PLTreeNode.getStudentName());
	}


	/**
	 * This is not a true JUnit Test method: it merely gives examples of calling
	 * the PLTree methods and obtaining output.
	 * 
	 * As part of this exercise, you should devise your OWN tests to check that
	 * your code is performing as the JavaDoc and the assignment hand-out says it
	 * should. You are strongly advised to use the "Coverage" tool to check for
	 * parts of your code that are not executed as part of your tests. The coverage
	 * tool can be invoked from the "Run" menu item in Eclipse or from the button
	 * beside the "Run" and "Debug" buttons in Eclipse
	 */
	@Test
	public void testPLTree()
	{
		NodeType[] typeList = { NodeType.R, NodeType.P, NodeType.OR, NodeType.TRUE, NodeType.Q, NodeType.NOT, NodeType.AND,
				NodeType.IMPLIES };
		logger.debug("typeList: " + Arrays.toString(typeList));

		PLTreeNodeInterface pltree = PLTreeNode.reversePolishBuilder(typeList);

		assertNotNull("PLTree construction failed when using: " + typeList, pltree);
		logger.debug("Constructed: " + pltree.toStringPrefix());
		logger.debug("Constructed: " + pltree.toStringInfix());
		NodeType[] typeListReturned = pltree.getReversePolish();
		logger.debug("typeListReturned: " + Arrays.toString(typeListReturned));

		Map<NodeType, Boolean> bindings = new HashMap<>();
		bindings.put(NodeType.P, true);
		bindings.put(NodeType.R, false);

		pltree.applyVarBindings(bindings);
		logger.debug(String.format("Applied bindings : %s to get: %s", bindings, pltree.toStringPrefix()));
		logger.debug(String.format("Applied bindings : %s to get: %s", bindings, pltree.toStringInfix()));
		typeListReturned = pltree.getReversePolish();
		logger.debug("typeListReturned: " + Arrays.toString(typeListReturned));

		//		Boolean b = pltree.evaluateConstantSubtrees();
		//		logger.debug(String.format("Evaluate constant subtrees to get: %s", pltree.toStringPrefix()));
		//		logger.debug(String.format("Evaluate constant subtrees to get: %s", pltree.toStringInfix()));

		pltree.replaceImplies();
		logger.debug(String.format("Replace Implies: %s", pltree.toStringPrefix()));
		logger.debug(String.format("Replace Implies: %s", pltree.toStringInfix()));
		typeListReturned = pltree.getReversePolish();
		logger.debug("typeListReturned: " + Arrays.toString(typeListReturned));

		pltree.pushNotDown();
		logger.debug(String.format("pushNotDown: %s", pltree.toStringPrefix()));
		logger.debug(String.format("pushNotDown: %s", pltree.toStringInfix()));
		typeListReturned = pltree.getReversePolish();
		logger.debug("typeListReturned: " + Arrays.toString(typeListReturned));

		pltree.pushOrBelowAnd();
		logger.debug(String.format("pushOrBelowAnd: %s", pltree.toStringPrefix()));
		logger.debug(String.format("pushOrBelowAnd: %s", pltree.toStringInfix()));
		typeListReturned = pltree.getReversePolish();
		logger.debug("typeListReturned: " + Arrays.toString(typeListReturned));

		pltree.makeAndOrRightDeep();
		logger.debug(String.format("makeAndOrRightDeep: %s", pltree.toStringPrefix()));
		logger.debug(String.format("makeAndOrRightDeep: %s", pltree.toStringInfix()));
		typeListReturned = pltree.getReversePolish();
		logger.debug("typeListReturned: " + Arrays.toString(typeListReturned));

		pltree.evaluateConstantSubtrees();
		logger.debug(String.format("Evaluate constant subtrees to get: %s", pltree.toStringPrefix()));
		logger.debug(String.format("Evaluate constant subtrees to get: %s", pltree.toStringInfix()));
		typeListReturned = pltree.getReversePolish();
		logger.debug("typeListReturned: " + Arrays.toString(typeListReturned));

		//==========================

		typeList = new NodeType[] { NodeType.R, NodeType.P, NodeType.IMPLIES, NodeType.S, NodeType.IMPLIES, NodeType.NOT, NodeType.Q,
				NodeType.IMPLIES };
		logger.debug("typeList: " + Arrays.toString(typeList));

		pltree = PLTreeNode.reversePolishBuilder(typeList);
		logger.debug("Constructed: " + pltree.toStringPrefix());
		logger.debug("Constructed: " + pltree.toStringInfix());
		typeListReturned = pltree.getReversePolish();
		logger.debug("typeListReturned: " + Arrays.toString(typeListReturned));

		pltree.reduceToCNF();
		logger.debug(String.format("ReduceToCNF to get: %s", pltree.toStringPrefix()));
		logger.debug(String.format("ReduceToCNF to get: %s", pltree.toStringInfix()));
		typeListReturned = pltree.getReversePolish();
		logger.debug("typeListReturned: " + Arrays.toString(typeListReturned));

		pltree.evaluateConstantSubtrees();
		logger.debug(String.format("Evaluate constant subtrees to get: %s", pltree.toStringPrefix()));
		logger.debug(String.format("Evaluate constant subtrees to get: %s", pltree.toStringInfix()));
		typeListReturned = pltree.getReversePolish();
		logger.debug("typeListReturned: " + Arrays.toString(typeListReturned));

		//==========================

		typeList = new NodeType[] { NodeType.A, NodeType.B, NodeType.AND, NodeType.C, NodeType.OR, NodeType.D, NodeType.OR, NodeType.E,
				NodeType.OR, NodeType.F, NodeType.OR, NodeType.G, NodeType.OR, NodeType.H, NodeType.OR };
		logger.debug("typeList: " + Arrays.toString(typeList));
		pltree = PLTreeNode.reversePolishBuilder(typeList);
		logger.debug("Constructed: " + pltree.toStringPrefix());
		logger.debug("Constructed: " + pltree.toStringInfix());
		typeListReturned = pltree.getReversePolish();
		logger.debug("typeListReturned: " + Arrays.toString(typeListReturned));

		pltree.pushOrBelowAnd();
		logger.debug(String.format("pushOrBelowAnd: %s", pltree.toStringPrefix()));
		logger.debug(String.format("pushOrBelowAnd: %s", pltree.toStringInfix()));
		typeListReturned = pltree.getReversePolish();
		logger.debug("typeListReturned: " + Arrays.toString(typeListReturned));
		
		
		//===========================

		logger.debug("Extra tests of pushOrBelowAnd()");
		typeList = new NodeType[] { NodeType.A, NodeType.B, NodeType.AND, NodeType.C, NodeType.AND, NodeType.D, NodeType.OR, NodeType.E, NodeType.OR};
		logger.debug("typeList: " + Arrays.toString(typeList));
		pltree = PLTreeNode.reversePolishBuilder(typeList);
		logger.debug("Constructed: " + pltree.toStringPrefix());
		logger.debug("Constructed: " + pltree.toStringInfix());
		typeListReturned = pltree.getReversePolish();
		logger.debug("typeListReturned: " + Arrays.toString(typeListReturned));

		pltree.pushOrBelowAnd();
		logger.debug(String.format("pushOrBelowAnd: %s", pltree.toStringPrefix()));
		logger.debug(String.format("pushOrBelowAnd: %s", pltree.toStringInfix()));
		typeListReturned = pltree.getReversePolish();
		logger.debug("typeListReturned: " + Arrays.toString(typeListReturned));

	}
	/*
	 To assist you in writing your tests, the log output from the solution to
	 the exercise for the above code is included below. You may find it useful
	 to copy and paste some of the strings below into some of your tests.
			
     typeList: [R, P, OR, TRUE, Q, NOT, AND, IMPLIES]
     Constructed: implies(or(R,P),and(true,not(Q)))
     Constructed: ((R∨P)→(⊤∧¬Q))
     typeListReturned: [R, P, OR, TRUE, Q, NOT, AND, IMPLIES]
     Applied bindings : {P=true, R=false} to get: implies(or(false,true),and(true,not(Q)))
     Applied bindings : {P=true, R=false} to get: ((⊥∨⊤)→(⊤∧¬Q))
     typeListReturned: [FALSE, TRUE, OR, TRUE, Q, NOT, AND, IMPLIES]
     Replace Implies: or(not(or(false,true)),and(true,not(Q)))
     Replace Implies: (¬(⊥∨⊤)∨(⊤∧¬Q))
     typeListReturned: [FALSE, TRUE, OR, NOT, TRUE, Q, NOT, AND, OR]
     pushNotDown: or(and(not(false),not(true)),and(true,not(Q)))
     pushNotDown: ((¬⊥∧¬⊤)∨(⊤∧¬Q))
     typeListReturned: [FALSE, NOT, TRUE, NOT, AND, TRUE, Q, NOT, AND, OR]
     pushOrBelowAnd: and(and(or(not(false),true),or(not(false),not(Q))),and(or(not(true),true),or(not(true),not(Q))))
     pushOrBelowAnd: (((¬⊥∨⊤)∧(¬⊥∨¬Q))∧((¬⊤∨⊤)∧(¬⊤∨¬Q)))
     typeListReturned: [FALSE, NOT, TRUE, OR, FALSE, NOT, Q, NOT, OR, AND, TRUE, NOT, TRUE, OR, TRUE, NOT, Q, NOT, OR, AND, AND]
     makeAndOrRightDeep: and(or(not(false),true),and(or(not(false),not(Q)),and(or(not(true),true),or(not(true),not(Q)))))
     makeAndOrRightDeep: ((¬⊥∨⊤)∧((¬⊥∨¬Q)∧((¬⊤∨⊤)∧(¬⊤∨¬Q))))
     typeListReturned: [FALSE, NOT, TRUE, OR, FALSE, NOT, Q, NOT, OR, TRUE, NOT, TRUE, OR, TRUE, NOT, Q, NOT, OR, AND, AND, AND]
     Evaluate constant subtrees to get: not(Q)
     Evaluate constant subtrees to get: ¬Q
     typeListReturned: [Q, NOT]
     typeList: [R, P, IMPLIES, S, IMPLIES, NOT, Q, IMPLIES]
     Constructed: implies(not(implies(implies(R,P),S)),Q)
     Constructed: (¬((R→P)→S)→Q)
     typeListReturned: [R, P, IMPLIES, S, IMPLIES, NOT, Q, IMPLIES]
     ReduceToCNF to get: and(or(R,or(S,Q)),or(not(P),or(S,Q)))
     ReduceToCNF to get: ((R∨(S∨Q))∧(¬P∨(S∨Q)))
     typeListReturned: [R, S, Q, OR, OR, P, NOT, S, Q, OR, OR, AND]
     Evaluate constant subtrees to get: and(or(R,or(S,Q)),or(not(P),or(S,Q)))
     Evaluate constant subtrees to get: ((R∨(S∨Q))∧(¬P∨(S∨Q)))
     typeListReturned: [R, S, Q, OR, OR, P, NOT, S, Q, OR, OR, AND]
     typeList: [A, B, AND, C, OR, D, OR, E, OR, F, OR, G, OR, H, OR]
     Constructed: or(or(or(or(or(or(and(A,B),C),D),E),F),G),H)
     Constructed: (((((((A∧B)∨C)∨D)∨E)∨F)∨G)∨H)
     typeListReturned: [A, B, AND, C, OR, D, OR, E, OR, F, OR, G, OR, H, OR]
     pushOrBelowAnd: and(or(or(or(or(or(or(A,C),D),E),F),G),H),or(or(or(or(or(or(B,C),D),E),F),G),H))
     pushOrBelowAnd: (((((((A∨C)∨D)∨E)∨F)∨G)∨H)∧((((((B∨C)∨D)∨E)∨F)∨G)∨H))
     typeListReturned: [A, C, OR, D, OR, E, OR, F, OR, G, OR, H, OR, B, C, OR, D, OR, E, OR, F, OR, G, OR, H, OR, AND]
     Extra tests of pushOrBelowAnd()
     typeList: [A, B, AND, C, AND, D, OR, E, OR]
     Constructed: or(or(and(and(A,B),C),D),E)
     Constructed: ((((A∧B)∧C)∨D)∨E)
     typeListReturned: [A, B, AND, C, AND, D, OR, E, OR]
     pushOrBelowAnd: and(and(or(or(A,D),E),or(or(B,D),E)),or(or(C,D),E))
     pushOrBelowAnd: ((((A∨D)∨E)∧((B∨D)∨E))∧((C∨D)∨E))
     typeListReturned: [A, D, OR, E, OR, B, D, OR, E, OR, AND, C, D, OR, E, OR, AND]


	 */
	
	// The following are simple examples using the first results of the sample output above.

	@Test
	public void testPLTreeConstruction()
	{
		NodeType[] typeList = { NodeType.R, NodeType.P, NodeType.OR, NodeType.TRUE, NodeType.Q, NodeType.NOT, NodeType.AND,	NodeType.IMPLIES };

		PLTreeNodeInterface pltree = PLTreeNode.reversePolishBuilder(typeList);

		assertNotNull("PLTree construction failed when using: " + typeList, pltree);
		
		NodeType[] newTypeList = pltree.getReversePolish();
		
		// The hamcrest matcher that follows generates better error messages automatically when comparing arrays than assertArrayEquals().
		// Try uncommenting the following line to see what the error message looks like if the arrays don't match
		//		newTypeList[1] = NodeType.A;
		assertThat(newTypeList, equalTo(typeList));
	}	

	@Test
	public void testPLTreeToPrefixString() {
		NodeType[] typeList = { NodeType.R, NodeType.P, NodeType.OR, NodeType.TRUE, NodeType.Q, 
				NodeType.NOT, NodeType.AND,	NodeType.IMPLIES };

		PLTreeNodeInterface pltree = PLTreeNode.reversePolishBuilder(typeList);

		// Always check that the tree generated is not null before any other tests:
		// if it is, it means that you have an error in your input typeList
		
		assertNotNull("PLTree construction failed when using: " + typeList, pltree);
		
		// Again, the Hamcrest matcher generates suitable error messages automatically:
		assertThat(pltree.toStringPrefix(), equalTo("implies(or(R,P),and(true,not(Q)))"));		
	}
	
    @Test
    public void testPLTreeToInfixString() {
	NodeType[] typeList = { NodeType.R, NodeType.P, NodeType.OR, NodeType.TRUE, NodeType.Q, NodeType.NOT,
		NodeType.AND, NodeType.IMPLIES };

	PLTreeNodeInterface pltree = PLTreeNode.reversePolishBuilder(typeList);

	assertNotNull("PLTree construction failed when using: " + typeList, pltree);

	assertThat(pltree.toStringPrefix(), equalTo("implies(or(R,P),and(true,not(Q)))"));
    }

    @Test
    public void testPLTreeApplyVarBindings() {
	Map<NodeType, Boolean> bindings = new HashMap<NodeType, Boolean>();
	bindings.put(NodeType.P, true);
	bindings.put(NodeType.R, false);

	NodeType[] typeList = { NodeType.R, NodeType.P, NodeType.OR, NodeType.TRUE, NodeType.Q, NodeType.NOT,
		NodeType.AND, NodeType.IMPLIES };

	PLTreeNodeInterface pltree = PLTreeNode.reversePolishBuilder(typeList);

	assertNotNull("PLTree construction failed when using: " + typeList, pltree);

	pltree.applyVarBindings(bindings);

	assertThat(pltree.toStringPrefix(), equalTo("implies(or(false,true),and(true,not(Q)))"));

    }

    @Test
    public void testPLTreeReplaceImplies1() {
	Map<NodeType, Boolean> bindings = new HashMap<NodeType, Boolean>();
	bindings.put(NodeType.P, true);
	bindings.put(NodeType.R, false);
	NodeType[] typeList = { NodeType.R, NodeType.P, NodeType.OR, NodeType.TRUE, NodeType.Q, NodeType.NOT,
		NodeType.AND, NodeType.IMPLIES };

	PLTreeNodeInterface pltree = PLTreeNode.reversePolishBuilder(typeList);

	assertNotNull("PLTree construction failed when using: " + typeList, pltree);

	pltree.applyVarBindings(bindings);
	pltree.replaceImplies();

	assertThat(pltree.toStringPrefix(), equalTo("or(not(or(false,true)),and(true,not(Q)))"));

    }

    @Test
    public void testPLTreeReplaceImplies2() {
    	Map<NodeType, Boolean> bindings = new HashMap<NodeType, Boolean>();
    	bindings.put(NodeType.P, true);
    	bindings.put(NodeType.R, false);
    	NodeType[] typeList = { NodeType.A, NodeType.B, NodeType.IMPLIES, NodeType.C, NodeType.AND, NodeType.D, NodeType.E,
    		NodeType.IMPLIES, NodeType.IMPLIES};

    	PLTreeNodeInterface pltree = PLTreeNode.reversePolishBuilder(typeList);

    	assertNotNull("PLTree construction failed when using: " + typeList, pltree);

    	pltree.applyVarBindings(bindings);
    	pltree.replaceImplies();

    	assertThat(pltree.toStringPrefix(), equalTo("or(not(and(or(not(A),B),C)),or(not(D),E))"));

        }
    
    @Test
    public void testPLTreePushNotDown1() {
	NodeType[] typeList = { NodeType.P, NodeType.Q, NodeType.OR, NodeType.NOT };

	PLTreeNodeInterface pltree = PLTreeNode.reversePolishBuilder(typeList);

	assertNotNull("PLTree construction failed when using: " + typeList, pltree);

	pltree.pushNotDown();

	assertThat(pltree.toStringPrefix(), equalTo("and(not(P),not(Q))"));
    }

    @Test
    public void testPLTreePushNotDown2() {
	NodeType[] typeList = { NodeType.P, NodeType.NOT, NodeType.NOT, NodeType.Q, NodeType.AND };

	PLTreeNodeInterface pltree = PLTreeNode.reversePolishBuilder(typeList);

	assertNotNull("PLTree construction failed when using: " + typeList, pltree);

	pltree.pushNotDown();

	assertThat(pltree.toStringPrefix(), equalTo("and(P,Q)"));
    }

    @Test
    public void testPLTreePushNotDown3() {
	NodeType[] typeList = { NodeType.P, NodeType.NOT, NodeType.Q, NodeType.AND, NodeType.NOT };

	PLTreeNodeInterface pltree = PLTreeNode.reversePolishBuilder(typeList);

	assertNotNull("PLTree construction failed when using: " + typeList, pltree);

	pltree.pushNotDown();

	assertThat(pltree.toStringPrefix(), equalTo("or(P,not(Q))"));
    }

    @Test
    public void testPLTreePushNotDown4() {
	NodeType[] typeList = { NodeType.P, NodeType.Q, NodeType.AND, NodeType.NOT, NodeType.NOT };

	PLTreeNodeInterface pltree = PLTreeNode.reversePolishBuilder(typeList);

	assertNotNull("PLTree construction failed when using: " + typeList, pltree);

	pltree.pushNotDown();

	assertThat(pltree.toStringPrefix(), equalTo("and(P,Q)"));
    }
    
    @Test
    public void testPLTreePushNotDown5() {
	NodeType[] typeList = { NodeType.P, NodeType.NOT, NodeType.NOT, NodeType.NOT, NodeType.NOT, NodeType.NOT};

	PLTreeNodeInterface pltree = PLTreeNode.reversePolishBuilder(typeList);

	assertNotNull("PLTree construction failed when using: " + typeList, pltree);

	pltree.pushNotDown();

	assertThat(pltree.toStringPrefix(), equalTo("not(P)"));
    }
    
    @Test
    public void testPLTreePushNotDown6() {
	NodeType[] typeList = { NodeType.P, NodeType.NOT, NodeType.Q, NodeType.NOT, NodeType.AND, 
			NodeType.NOT, NodeType.R, NodeType.NOT, NodeType.S, NodeType.NOT, NodeType.OR, 
			NodeType.NOT, NodeType.AND};

	PLTreeNodeInterface pltree = PLTreeNode.reversePolishBuilder(typeList);

	assertNotNull("PLTree construction failed when using: " + typeList, pltree);

	pltree.pushNotDown();

	assertThat(pltree.toStringPrefix(), equalTo("and(or(P,Q),and(R,S))"));
    }
    
    @Test
    public void testPLTreepushOrBelowAnd1()  {
    	NodeType[] typeList = { NodeType.P, NodeType.Q, NodeType.AND, NodeType.R, NodeType.OR };

    	PLTreeNodeInterface pltree = PLTreeNode.reversePolishBuilder(typeList);

    	assertNotNull("PLTree construction failed when using: " + typeList, pltree);

    	pltree.pushOrBelowAnd();

    	assertThat(pltree.toStringPrefix(), equalTo("and(or(P,R),or(Q,R))"));
        }
    
    @Test
    public void testPLTreepushOrBelowAnd2()  {
    	NodeType[] typeList = { NodeType.P, NodeType.Q, NodeType.R, NodeType.AND, NodeType.OR };

    	PLTreeNodeInterface pltree = PLTreeNode.reversePolishBuilder(typeList);

    	assertNotNull("PLTree construction failed when using: " + typeList, pltree);

    	pltree.pushOrBelowAnd();

    	assertThat(pltree.toStringPrefix(), equalTo("and(or(P,Q),or(P,R))"));
        }
    
    @Test
    public void testPLTreepushOrBelowAnd3()  {
    	NodeType[] typeList = { NodeType.P, NodeType.Q, NodeType.AND, NodeType.R, NodeType.OR, 
    			NodeType.S, NodeType.OR };

    	PLTreeNodeInterface pltree = PLTreeNode.reversePolishBuilder(typeList);

    	assertNotNull("PLTree construction failed when using: " + typeList, pltree);

    	pltree.pushOrBelowAnd();

    	assertThat(pltree.toStringPrefix(), equalTo("and(or(or(P,R),S),or(or(Q,R),S))"));
        }
    
    @Test
    public void testPLTreepushOrBelowAnd4()  {
    	NodeType[] typeList = { NodeType.P, NodeType.Q, NodeType.AND, NodeType.R, NodeType.AND, NodeType.S, NodeType.OR };

    	PLTreeNodeInterface pltree = PLTreeNode.reversePolishBuilder(typeList);

    	assertNotNull("PLTree construction failed when using: " + typeList, pltree);

    	pltree.pushOrBelowAnd();

    	assertThat(pltree.toStringPrefix(), equalTo("and(and(or(P,S),or(Q,S)),or(R,S))"));
        }
    
    @Test
    public void testPLTreepushOrBelowAnd5()  {
    	NodeType[] typeList = { NodeType.S, NodeType.P, NodeType.Q, NodeType.AND, NodeType.R, NodeType.OR, NodeType.OR };

    	PLTreeNodeInterface pltree = PLTreeNode.reversePolishBuilder(typeList);

    	assertNotNull("PLTree construction failed when using: " + typeList, pltree);

    	pltree.pushOrBelowAnd();

    	assertThat(pltree.toStringPrefix(), equalTo("and(or(S,or(P,R)),or(S,or(Q,R)))"));
        }
    
    @Test
    public void testPLTreepushOrBelowAnd6()  {
    	NodeType[] typeList = { NodeType.A, NodeType.B, NodeType.AND, NodeType.C, NodeType.D, 
    			NodeType.AND, NodeType.OR };

    	PLTreeNodeInterface pltree = PLTreeNode.reversePolishBuilder(typeList);

    	assertNotNull("PLTree construction failed when using: " + typeList, pltree);

    	pltree.pushOrBelowAnd();

    	assertThat(pltree.toStringPrefix(), equalTo("and(and(or(A,C),or(A,D)),and(or(B,C),or(B,D)))"));
        }
    
    @Test
    public void testPLTreepushOrBelowAnd7()  {
    	NodeType[] typeList = { NodeType.A, NodeType.B, NodeType.AND, NodeType.C, NodeType.NOT, NodeType.OR, NodeType.D, NodeType.NOT, NodeType.OR, 
    			NodeType.E, NodeType.OR };

    	PLTreeNodeInterface pltree = PLTreeNode.reversePolishBuilder(typeList);

    	assertNotNull("PLTree construction failed when using: " + typeList, pltree);

    	pltree.pushOrBelowAnd();

    	assertThat(pltree.toStringPrefix(), equalTo("and(or(or(or(A,not(C)),not(D)),E),or(or(or(B,not(C)),not(D)),E))"));
        }
    
    @Test
    public void testPLTreepushOrBelowAnd8()  {
    	NodeType[] typeList = { NodeType.A, NodeType.B, NodeType.AND, NodeType.C, NodeType.OR, NodeType.D, NodeType.E, NodeType.AND, NodeType.OR, 
    			NodeType.F, NodeType.OR };

    	PLTreeNodeInterface pltree = PLTreeNode.reversePolishBuilder(typeList);

    	assertNotNull("PLTree construction failed when using: " + typeList, pltree);

    	pltree.pushOrBelowAnd();

    	assertThat(pltree.toStringPrefix(), equalTo("and(and(or(or(or(A,C),D),F),or(or(or(A,C),E),F)),and(or(or(or(B,C),D),F),or(or(or(B,C),E),F)))"));
        }
    
    @Test
    public void testPLTreepushOrBelowAnd9()  {
    	NodeType[] typeList = { NodeType.A, NodeType.B, NodeType.AND, NodeType.C, NodeType.NOT, 
    			NodeType.OR, NodeType.NOT };

    	PLTreeNodeInterface pltree = PLTreeNode.reversePolishBuilder(typeList);

    	assertNotNull("PLTree construction failed when using: " + typeList, pltree);

    	pltree.pushOrBelowAnd();

    	assertThat(pltree.toStringPrefix(), equalTo("not(and(or(A,not(C)),or(B,not(C))))"));
        }
    
    @Test
    public void testPLTreemakeAndOrRightDeep()  {
    	NodeType[] typeList = { NodeType.P, NodeType.Q, NodeType.AND, NodeType.R, NodeType.AND };

    	PLTreeNodeInterface pltree = PLTreeNode.reversePolishBuilder(typeList);

    	assertNotNull("PLTree construction failed when using: " + typeList, pltree);

    	pltree.makeAndOrRightDeep();

    	assertThat(pltree.toStringPrefix(), equalTo("and(P,and(Q,R))"));
        }
    
    @Test
    public void testPLTreemakeAndOrRightDeep2()  {
    	NodeType[] typeList = { NodeType.P, NodeType.Q, NodeType.OR, NodeType.R, NodeType.OR };

    	PLTreeNodeInterface pltree = PLTreeNode.reversePolishBuilder(typeList);

    	assertNotNull("PLTree construction failed when using: " + typeList, pltree);

    	pltree.makeAndOrRightDeep();

    	assertThat(pltree.toStringPrefix(), equalTo("or(P,or(Q,R))"));
        }
    
    
    @Test
    public void testPLTreeToPrefixString2() {
	NodeType[] typeList = { NodeType.R, NodeType.P, NodeType.OR, NodeType.TRUE, NodeType.Q, NodeType.NOT,
		NodeType.AND, NodeType.IMPLIES };

	PLTreeNodeInterface pltree = PLTreeNode.reversePolishBuilder(typeList);

	// Always check that the tree generated is not null before any other tests:
	// if it is, it means that you have an error in your input typeList

	assertNotNull("PLTree construction failed when using: " + typeList, pltree);

	// Again, the Hamcrest matcher generates suitable error messages automatically:
	assertThat(pltree.toStringPrefix(), equalTo("implies(or(R,P),and(true,not(Q)))"));
    }

   

    @Test
    public void testPLTreeApplyVarBindings2() {
	Map<NodeType, Boolean> bindings = new HashMap<NodeType, Boolean>();
	bindings.put(NodeType.P, true);
	bindings.put(NodeType.R, false);

	NodeType[] typeList = { NodeType.R, NodeType.P, NodeType.OR, NodeType.TRUE, NodeType.Q, NodeType.NOT,
		NodeType.AND, NodeType.IMPLIES };

	PLTreeNodeInterface pltree = PLTreeNode.reversePolishBuilder(typeList);

	assertNotNull("PLTree construction failed when using: " + typeList, pltree);

	pltree.applyVarBindings(bindings);

	assertThat(pltree.toStringPrefix(), equalTo("implies(or(false,true),and(true,not(Q)))"));

    }

    @Test
    public void testPLTreeReplaceImplies() {
	Map<NodeType, Boolean> bindings = new HashMap<NodeType, Boolean>();
	bindings.put(NodeType.P, true);
	bindings.put(NodeType.R, false);
	NodeType[] typeList = { NodeType.R, NodeType.P, NodeType.OR, NodeType.TRUE, NodeType.Q, NodeType.NOT,
		NodeType.AND, NodeType.IMPLIES };

	PLTreeNodeInterface pltree = PLTreeNode.reversePolishBuilder(typeList);

	assertNotNull("PLTree construction failed when using: " + typeList, pltree);

	pltree.applyVarBindings(bindings);
	pltree.replaceImplies();

	assertThat(pltree.toStringPrefix(), equalTo("or(not(or(false,true)),and(true,not(Q)))"));

    }

    @Test
    public void testPLTreePushNotDown7() {
	NodeType[] typeList = { NodeType.P, NodeType.Q, NodeType.OR, NodeType.NOT };

	PLTreeNodeInterface pltree = PLTreeNode.reversePolishBuilder(typeList);

	assertNotNull("PLTree construction failed when using: " + typeList, pltree);

	pltree.pushNotDown();

	assertThat(pltree.toStringPrefix(), equalTo("and(not(P),not(Q))"));
    }

    @Test
    public void testPLTreePushNotDown8() {
	NodeType[] typeList = { NodeType.P, NodeType.NOT, NodeType.NOT, NodeType.Q, NodeType.AND };

	PLTreeNodeInterface pltree = PLTreeNode.reversePolishBuilder(typeList);

	assertNotNull("PLTree construction failed when using: " + typeList, pltree);

	pltree.pushNotDown();

	assertThat(pltree.toStringPrefix(), equalTo("and(P,Q)"));
    }

    @Test
    public void testPLTreePushNotDown9() {
	NodeType[] typeList = { NodeType.P, NodeType.NOT, NodeType.Q, NodeType.AND, NodeType.NOT };

	PLTreeNodeInterface pltree = PLTreeNode.reversePolishBuilder(typeList);

	assertNotNull("PLTree construction failed when using: " + typeList, pltree);

	pltree.pushNotDown();

	assertThat(pltree.toStringPrefix(), equalTo("or(P,not(Q))"));
    }

    @Test
    public void testPLTreePushNotDown10() {
	NodeType[] typeList = { NodeType.P, NodeType.Q, NodeType.AND, NodeType.NOT, NodeType.NOT };

	PLTreeNodeInterface pltree = PLTreeNode.reversePolishBuilder(typeList);

	assertNotNull("PLTree construction failed when using: " + typeList, pltree);

	pltree.pushNotDown();

	assertThat(pltree.toStringPrefix(), equalTo("and(P,Q)"));
    }

    @Test
    public void testPLTreePushNotDown11() {
	NodeType[] typeList = { NodeType.P, NodeType.NOT, NodeType.NOT, NodeType.NOT };

	PLTreeNodeInterface pltree = PLTreeNode.reversePolishBuilder(typeList);

	assertNotNull("PLTree construction failed when using: " + typeList, pltree);

	pltree.pushNotDown();

	assertThat(pltree.toStringPrefix(), equalTo("not(P)"));
    }

    @Test
    public void testPLTreePushNotDown12() {
	NodeType[] typeList = { NodeType.P, NodeType.Q, NodeType.AND, NodeType.NOT };

	PLTreeNodeInterface pltree = PLTreeNode.reversePolishBuilder(typeList);

	assertNotNull("PLTree construction failed when using: " + typeList, pltree);

	pltree.pushNotDown();

	assertThat(pltree.toStringPrefix(), equalTo("or(not(P),not(Q))"));
    }

    @Test
    public void testPLTreePushOrBelowAnd1() {
	NodeType[] typeList = { NodeType.P, NodeType.Q, NodeType.AND, NodeType.R, NodeType.OR };
	// NodeType[] typeList = {};

	PLTreeNodeInterface pltree = PLTreeNode.reversePolishBuilder(typeList);

	assertNotNull("PLTree construction failed when using: " + typeList, pltree);

	pltree.pushOrBelowAnd();;

	assertThat(pltree.toStringPrefix(), equalTo("and(or(P,R),or(Q,R))"));
    }

    @Test
    public void testPLTreePushOrBelowAnd2() {
	NodeType[] typeList = { NodeType.A, NodeType.B, NodeType.AND, NodeType.C, NodeType.AND, NodeType.D, NodeType.OR,
		NodeType.E, NodeType.OR };
	// NodeType[] typeList = {};

	PLTreeNodeInterface pltree = PLTreeNode.reversePolishBuilder(typeList);

	assertNotNull("PLTree construction failed when using: " + typeList, pltree);

	pltree.pushOrBelowAnd();

	assertThat(pltree.toStringPrefix(), equalTo("and(and(or(or(A,D),E),or(or(B,D),E)),or(or(C,D),E))"));
    }
    
    @Test
    public void testPLTreeEvaluateConstantSubTrees() {
	NodeType[] typeList = { NodeType.TRUE, NodeType.TRUE, NodeType.AND};

	PLTreeNodeInterface pltree = PLTreeNode.reversePolishBuilder(typeList);

	assertNotNull("PLTree construction failed when using: " + typeList, pltree);

	pltree.evaluateConstantSubtrees();

	assertThat(pltree.toStringPrefix(), equalTo("true"));
    }
    
    @Test
    public void testPLTreeEvaluateConstantSubTrees2() {
	NodeType[] typeList = { NodeType.P, NodeType.FALSE, NodeType.AND};

	PLTreeNodeInterface pltree = PLTreeNode.reversePolishBuilder(typeList);

	assertNotNull("PLTree construction failed when using: " + typeList, pltree);

	pltree.evaluateConstantSubtrees();

	assertThat(pltree.toStringPrefix(), equalTo("false"));
    }

    
    @Test
    public void testPLTreeEvaluateConstantSubTrees3() {
	NodeType[] typeList = { NodeType.P, NodeType.Q, NodeType.AND};

	PLTreeNodeInterface pltree = PLTreeNode.reversePolishBuilder(typeList);

	assertNotNull("PLTree construction failed when using: " + typeList, pltree);

	pltree.evaluateConstantSubtrees();

	assertThat(pltree.toStringPrefix(), equalTo("and(P,Q)"));
    }
    
    @Test
    public void testPLTreeEvaluateConstantSubTrees4() {
	NodeType[] typeList = { NodeType.TRUE, NodeType.FALSE, NodeType.TRUE, NodeType.AND, 
			NodeType.OR, NodeType.TRUE, NodeType.AND, NodeType.P, NodeType.AND};

	PLTreeNodeInterface pltree = PLTreeNode.reversePolishBuilder(typeList);

	assertNotNull("PLTree construction failed when using: " + typeList, pltree);

	pltree.evaluateConstantSubtrees();

	assertThat(pltree.toStringPrefix(), equalTo("P"));
    }
    
    
    @Test
    public void testPLTreeEvaluateConstantSubTrees5() {
	NodeType[] typeList = { NodeType.TRUE, NodeType.FALSE, NodeType.OR, NodeType.TRUE, NodeType.AND, NodeType.NOT};

	PLTreeNodeInterface pltree = PLTreeNode.reversePolishBuilder(typeList);

	assertNotNull("PLTree construction failed when using: " + typeList, pltree);

	pltree.evaluateConstantSubtrees();

	assertThat(pltree.toStringPrefix(), equalTo("false"));
    }
    
    @Test
    public void testPLTreeEvaluateConstantSubTrees6() {
	NodeType[] typeList = { NodeType.P, NodeType.TRUE, NodeType.TRUE, NodeType.AND, NodeType.OR};

	PLTreeNodeInterface pltree = PLTreeNode.reversePolishBuilder(typeList);

	assertNotNull("PLTree construction failed when using: " + typeList, pltree);

	pltree.evaluateConstantSubtrees();

	assertThat(pltree.toStringPrefix(), equalTo("true"));
    }
    
    @Test
    public void testPLTreeEvaluateConstantSubTrees7() {
	NodeType[] typeList = { NodeType.P, NodeType.Q, NodeType.AND};

	PLTreeNodeInterface pltree = PLTreeNode.reversePolishBuilder(typeList);

	assertNotNull("PLTree construction failed when using: " + typeList, pltree);

	pltree.evaluateConstantSubtrees();

	assertThat(pltree.evaluateConstantSubtrees(), equalTo(null));
    }
    
    @Test
    public void testPLTreeEvaluateConstantSubTrees8() {
	NodeType[] typeList = { NodeType.FALSE, NodeType.TRUE, NodeType.NOT, NodeType.AND, NodeType.NOT};

	PLTreeNodeInterface pltree = PLTreeNode.reversePolishBuilder(typeList);
	
	//logger.debug("Constructed: " + pltree.toStringPrefix());

	assertNotNull("PLTree construction failed when using: " + typeList, pltree);

	pltree.evaluateConstantSubtrees();

	assertThat(pltree.toStringPrefix(), equalTo("true"));
	assertThat(pltree.evaluateConstantSubtrees(), equalTo(true));
    }
    
    @Test
    public void testPLTreeEvaluateConstantSubTrees9() {
	NodeType[] typeList = { NodeType.TRUE, NodeType.Q, NodeType.NOT, NodeType.AND};

	PLTreeNodeInterface pltree = PLTreeNode.reversePolishBuilder(typeList);

	assertNotNull("PLTree construction failed when using: " + typeList, pltree);

	pltree.evaluateConstantSubtrees();

	assertThat(pltree.toStringPrefix(), equalTo("not(Q)"));
    }

    @Test
    public void testPLTreeEvaluateConstantSubTrees10() {
	NodeType[] typeList = { NodeType.P, NodeType.Q, NodeType.AND, NodeType.FALSE, NodeType.IMPLIES};

	PLTreeNodeInterface pltree = PLTreeNode.reversePolishBuilder(typeList);

	assertNotNull("PLTree construction failed when using: " + typeList, pltree);
	
	pltree.replaceImplies();

	pltree.evaluateConstantSubtrees();

	assertThat(pltree.toStringPrefix(), equalTo("not(and(P,Q))"));
	assertThat(pltree.evaluateConstantSubtrees(), equalTo(null));
    }
    
    @Test
    public void testPLTreePushNotDown20() {
	NodeType[] typeList = { NodeType.P, NodeType.Q, NodeType.OR, NodeType.NOT };

	PLTreeNodeInterface pltree = PLTreeNode.reversePolishBuilder(typeList);

	assertNotNull("PLTree construction failed when using: " + typeList, pltree);

	pltree.pushNotDown();

	assertThat(pltree.toStringPrefix(), equalTo("and(not(P),not(Q))"));
    }

    @Test
    public void testPLTreePushNotDown21() {
	NodeType[] typeList = { NodeType.P, NodeType.NOT, NodeType.NOT, NodeType.Q, NodeType.AND };

	PLTreeNodeInterface pltree = PLTreeNode.reversePolishBuilder(typeList);

	assertNotNull("PLTree construction failed when using: " + typeList, pltree);

	pltree.pushNotDown();

	assertThat(pltree.toStringPrefix(), equalTo("and(P,Q)"));
    }

    @Test
    public void testPLTreePushNotDown22() {
	NodeType[] typeList = { NodeType.P, NodeType.NOT, NodeType.Q, NodeType.AND, NodeType.NOT };

	PLTreeNodeInterface pltree = PLTreeNode.reversePolishBuilder(typeList);

	assertNotNull("PLTree construction failed when using: " + typeList, pltree);

	pltree.pushNotDown();

	assertThat(pltree.toStringPrefix(), equalTo("or(P,not(Q))"));
    }

    @Test
    public void testPLTreePushNotDown23() {
	NodeType[] typeList = { NodeType.P, NodeType.Q, NodeType.AND, NodeType.NOT, NodeType.NOT };

	PLTreeNodeInterface pltree = PLTreeNode.reversePolishBuilder(typeList);

	assertNotNull("PLTree construction failed when using: " + typeList, pltree);

	pltree.pushNotDown();

	assertThat(pltree.toStringPrefix(), equalTo("and(P,Q)"));
    }

    @Test
    public void testPLTreePushNotDown24() {
	NodeType[] typeList = { NodeType.P, NodeType.NOT, NodeType.NOT, NodeType.NOT };

	PLTreeNodeInterface pltree = PLTreeNode.reversePolishBuilder(typeList);

	assertNotNull("PLTree construction failed when using: " + typeList, pltree);

	pltree.pushNotDown();

	assertThat(pltree.toStringPrefix(), equalTo("not(P)"));
    }

    @Test
    public void testPLTreePushNotDown25() {
	NodeType[] typeList = { NodeType.P, NodeType.Q, NodeType.AND, NodeType.NOT };

	PLTreeNodeInterface pltree = PLTreeNode.reversePolishBuilder(typeList);

	assertNotNull("PLTree construction failed when using: " + typeList, pltree);

	pltree.pushNotDown();

	assertThat(pltree.toStringPrefix(), equalTo("or(not(P),not(Q))"));
    }

    @Test
    public void testPLTreePushOrBelowAnd10() {
	NodeType[] typeList = { NodeType.P, NodeType.Q, NodeType.AND, NodeType.R, NodeType.OR };
	// NodeType[] typeList = {};

	PLTreeNodeInterface pltree = PLTreeNode.reversePolishBuilder(typeList);

	assertNotNull("PLTree construction failed when using: " + typeList, pltree);

	pltree.pushOrBelowAnd();

	assertThat(pltree.toStringPrefix(), equalTo("and(or(P,R),or(Q,R))"));
    }

    @Test
    public void testPLTreePushOrBelowAnd11() {
	NodeType[] typeList = { NodeType.A, NodeType.B, NodeType.AND, NodeType.C, NodeType.AND, NodeType.D, NodeType.OR,
		NodeType.E, NodeType.OR };
	// NodeType[] typeList = {};

	PLTreeNodeInterface pltree = PLTreeNode.reversePolishBuilder(typeList);

	assertNotNull("PLTree construction failed when using: " + typeList, pltree);

	pltree.pushOrBelowAnd();

	assertThat(pltree.toStringPrefix(), equalTo("and(and(or(or(A,D),E),or(or(B,D),E)),or(or(C,D),E))"));
    }

    @Test
    public void testPLTreePushOrBelowAnd3() {
	NodeType[] typeList = { NodeType.A, NodeType.B, NodeType.AND, NodeType.C, NodeType.OR, NodeType.D, NodeType.OR,
		NodeType.E, NodeType.OR, NodeType.F, NodeType.OR, NodeType.G, NodeType.OR, NodeType.H, NodeType.OR };
	// NodeType[] typeList = {};

	PLTreeNodeInterface pltree = PLTreeNode.reversePolishBuilder(typeList);

	assertNotNull("PLTree construction failed when using: " + typeList, pltree);

	pltree.pushOrBelowAnd();

	assertThat(pltree.toStringPrefix(),
		equalTo("and(or(or(or(or(or(or(A,C),D),E),F),G),H),or(or(or(or(or(or(B,C),D),E),F),G),H))"));
    }

    @Test
    public void testPLTreeMakeAnOrRightDeep1() {
	NodeType[] typeList = { NodeType.FALSE, NodeType.NOT, NodeType.TRUE, NodeType.OR, NodeType.FALSE, NodeType.NOT,
		NodeType.Q, NodeType.NOT, NodeType.OR, NodeType.AND, NodeType.TRUE, NodeType.NOT, NodeType.TRUE,
		NodeType.OR, NodeType.TRUE, NodeType.NOT, NodeType.Q, NodeType.NOT, NodeType.OR, NodeType.AND,
		NodeType.AND };
	// NodeType[] typeList = {};

	PLTreeNodeInterface pltree = PLTreeNode.reversePolishBuilder(typeList);

	assertNotNull("PLTree construction failed when using: " + typeList, pltree);

	pltree.makeAndOrRightDeep();

	assertThat(pltree.toStringPrefix(), equalTo(
		"and(or(not(false),true),and(or(not(false),not(Q)),and(or(not(true),true),or(not(true),not(Q)))))"));
    }

    @Test
    public void testPLTreeMakeAnOrRightDeep2() {
	NodeType[] typeList = { NodeType.P, NodeType.Q, NodeType.OR, NodeType.R, NodeType.OR };
	// NodeType[] typeList = {};

	PLTreeNodeInterface pltree = PLTreeNode.reversePolishBuilder(typeList);

	assertNotNull("PLTree construction failed when using: " + typeList, pltree);

	pltree.makeAndOrRightDeep();

	assertThat(pltree.toStringPrefix(), equalTo("or(P,or(Q,R))"));
    }

    @Test
    public void testPLTreeMakeAnOrRightDeep3() {
	NodeType[] typeList = { NodeType.P, NodeType.Q, NodeType.AND, NodeType.R, NodeType.AND };
	// NodeType[] typeList = {};

	PLTreeNodeInterface pltree = PLTreeNode.reversePolishBuilder(typeList);

	assertNotNull("PLTree construction failed when using: " + typeList, pltree);

	pltree.makeAndOrRightDeep();

	assertThat(pltree.toStringPrefix(), equalTo("and(P,and(Q,R))"));
    }

    @Test
    public void testPLTreeMakeAnOrRightDeep4() {
	NodeType[] typeList = { NodeType.P, NodeType.Q, NodeType.AND, NodeType.R, NodeType.AND, NodeType.NOT };
	// NodeType[] typeList = {};

	PLTreeNodeInterface pltree = PLTreeNode.reversePolishBuilder(typeList);

	assertNotNull("PLTree construction failed when using: " + typeList, pltree);

	pltree.makeAndOrRightDeep();

	assertThat(pltree.toStringPrefix(), equalTo("not(and(P,and(Q,R)))"));
    }

    @Test
    public void testPLTreeEvaluateConstantSubtreets1() {
	NodeType[] typeList = { NodeType.TRUE, NodeType.TRUE, NodeType.AND };
	// NodeType[] typeList = {};

	PLTreeNodeInterface pltree = PLTreeNode.reversePolishBuilder(typeList);

	assertNotNull("PLTree construction failed when using: " + typeList, pltree);

	pltree.evaluateConstantSubtrees();

	assertThat(pltree.toStringPrefix(), equalTo("true"));
    }

    @Test
    public void testPLTreeEvaluateConstantSubtreets2() {
	NodeType[] typeList = { NodeType.P, NodeType.FALSE, NodeType.AND };
	// NodeType[] typeList = {};

	PLTreeNodeInterface pltree = PLTreeNode.reversePolishBuilder(typeList);

	assertNotNull("PLTree construction failed when using: " + typeList, pltree);

	pltree.evaluateConstantSubtrees();

	assertThat(pltree.toStringPrefix(), equalTo("false"));
    }

    @Test
    public void testPLTreeEvaluateConstantSubtreets3() {
	NodeType[] typeList = { NodeType.TRUE, NodeType.P, NodeType.AND };
	// NodeType[] typeList = {};

	PLTreeNodeInterface pltree = PLTreeNode.reversePolishBuilder(typeList);

	assertNotNull("PLTree construction failed when using: " + typeList, pltree);

	pltree.evaluateConstantSubtrees();

	assertThat(pltree.toStringPrefix(), equalTo("P"));
    }

    @Test
    public void testPLTreeEvaluateConstantSubtreets4() {
	NodeType[] typeList = { NodeType.Q, NodeType.P, NodeType.AND };
	// NodeType[] typeList = {};

	PLTreeNodeInterface pltree = PLTreeNode.reversePolishBuilder(typeList);

	assertNotNull("PLTree construction failed when using: " + typeList, pltree);

	pltree.evaluateConstantSubtrees();

	assertThat(pltree.toStringPrefix(), equalTo("and(Q,P)"));
    }

    //
    @Test
    public void testPLTreeEvaluateConstantSubtreets5() {
	NodeType[] typeList = { NodeType.FALSE, NodeType.NOT, NodeType.TRUE, NodeType.OR, NodeType.FALSE, NodeType.NOT,
		NodeType.Q, NodeType.NOT, NodeType.OR, NodeType.TRUE, NodeType.NOT, NodeType.TRUE, NodeType.OR,
		NodeType.TRUE, NodeType.NOT, NodeType.Q, NodeType.NOT, NodeType.OR, NodeType.AND, NodeType.AND,
		NodeType.AND };
	// NodeType[] typeList = {};

	PLTreeNodeInterface pltree = PLTreeNode.reversePolishBuilder(typeList);

	assertNotNull("PLTree construction failed when using: " + typeList, pltree);

	pltree.evaluateConstantSubtrees();

	assertThat(pltree.toStringPrefix(), equalTo("not(Q)"));
    }
}
