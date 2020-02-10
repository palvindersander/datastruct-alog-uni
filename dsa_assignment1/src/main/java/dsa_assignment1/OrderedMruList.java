package dsa_assignment1;

public class OrderedMruList<E extends Comparable<E>> implements OrderedMruListInterface<E>
{
	/**
	 * The head field is an <code>MLNode</code> object, where the
	 * <code>next1</code> and <code>prev1</code> pointers are for the circular Ordered list,
	 * and the <code>next2</code> and <code>prev2</code> are for the circular MRU list.
	 * It always contains the value <code>Null</code>.
	 * These lists are considered empty if there is no
	 * <b>other</b> <code>MLNode</code> object on the lists other
	 * than the <code>head</code> node itself
	 * 
	 */
	MLNodeInterface<E>	head	= new MLNode<E>(null);

	public OrderedMruList()
	{
	}
	
	public boolean isEmptyOrdered()
	{
		/* WRITE THIS CODE */
		return head.getNext1() == head;
	}

	public boolean isEmptyMru()
	{
		/* WRITE THIS CODE */
		return head.getNext2() == head;
	}

	public OrderedMruListInterface<E> touch(MLNodeInterface<E> target)
	{
		/* WRITE THIS CODE */
		target.addAfter2(head);
		return this;
	}
	
	public MLNodeInterface<E> getFirstMru()
	{
		/* WRITE THIS CODE */
		if (this.isEmptyMru()) {
			return null;
		} else {
			return head.getNext2();
		}
	}
	
	public MLNodeInterface<E> getFirstOrdered()
	{
		/* WRITE THIS CODE */
		if (this.isEmptyOrdered()) {
			return null;
		} else {
			return head.getNext1();
		}
	}
	
	public MLNodeInterface<E> getNextOrdered(MLNodeInterface<E> current)
	{
		/* WRITE THIS CODE */
		return current.getNext1();
	}

	public MLNodeInterface<E> getNextMru(MLNodeInterface<E> current)
	{
		/* WRITE THIS CODE */
		return current.getNext2();
	}

	public OrderedMruListInterface<E> remove(MLNodeInterface<E> target)
	{
		/* WRITE THIS CODE */
		target.remove1();
		target.remove2();
		return this;
	}
	
	public OrderedMruListInterface<E> add(E element)
	{
		/* WRITE THIS CODE */
		MLNode<E> newNode =  new MLNode<E>(element);
		this.touch(newNode);
		if (!this.isEmptyOrdered()) {
			newNode.addAfter1(head);
			while (true) {
				if (newNode.getNext1() == this.head) {
					break;
				} else if ( element.compareTo(newNode.getNext1().getElement()) > 0) {
					newNode.addAfter1(newNode.getNext1());
				} else {
					break;
				}
			}
			
		} else {
			newNode.addAfter1(head);
		}
		return this;
	}
}
