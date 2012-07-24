package mage.client.components.layout;

import java.awt.*;
import java.util.*;

/**
 *  The <code>RelativeLayout</code> class is a layout manager that
 *  lays out a container's components on the specified X or Y axis.
 *
 *	Components can be layed out at their preferred size or at a
 *  relative size. When relative sizing is used the component must be added
 *  to the container using a relative size constraint, which is simply a
 *  Float value.
 *
 *  The space available for relative sized components is determined by
 *  subtracting the preferred size of the other components from the space
 *  available in the container. Each component is then assigned a size
 *  based on its relative size value. For example:
 *
 *  container.add(component1, new Float(1));
 *  container.add(component2, new Float(2));
 *
 *  There is a total of 3 relative units. If the container has 300 pixels
 *  of space available then component1 will get 100 and component2, 200.
 *
 *  It is possible that rounding errors will occur in which case you can
 *  specify a rounding policy to use to allocate the extra pixels.
 *
 *  By defaults components are center aligned on the secondary axis
 *  however this can be changed at the container or component level.
 */
public class RelativeLayout implements LayoutManager2, java.io.Serializable
{
	//  Used in the constructor
	public final static int X_AXIS = 0;
	public final static int Y_AXIS = 1;

	//  See	setAlignment() method
	public final static float LEADING = 0.0f;
	public final static float CENTER = 0.5f;
	public final static float TRAILING = 1.0f;
	public final static float COMPONENT = -1.0f;

	//  See setRoundingPolicy() method
	public final static int DO_NOTHING = 0;
	public final static int FIRST = 1;
	public final static int LAST = 2;
	public final static int LARGEST = 3;
	public final static int EQUAL = 4;

	private final static int MINIMUM = 0;
	private final static int PREFERRED = 1;

	private HashMap<Component, Float> constraints = new HashMap<Component, Float>();

	/**
	 *  The axis of the Components within the Container.
	 */
	private int axis;

	/**
	 *  The alignment of the Components on the other axis of the Container.
	 *  For X-AXIS this would refer to the Y alignemt.
	 *  For Y-AXIS this would refer to the X alignment.
	 */
	private float alignment = CENTER;

	/**
	 *  This is the gap (in pixels) which specifies the space between components
	 *  It can be changed at any time and should be a non-negative integer.
	 */
	private int gap;

	/**
	 *  The gap (in pixels) used before the leading component and after the
	 *  trailing component.
	 *  It can be changed at any time and should be a non-negative integer.
	 */
	private int borderGap;

	//  Fill space available for relative components
	private boolean fill = false;

	//  Gap to prevent the component from completely filling the space available
	private int fillGap;

	//  Specify the rounding policy when rounding problems happen.
	private int roundingPolicy = LARGEST;

	/**
	 * Creates a relative layout with the components layed out on the X-Axis
	 *  using the default gap
	 */
	public RelativeLayout()
	{
		this(X_AXIS, 0);
	}

	/**
	 * Creates a relative layout with the components layed out on the specified
	 *  axis using the default gap
	 * <p>
	 * @param	 axis  X-AXIS or Y_AXIS
	 */
	public RelativeLayout(int axis)
	{
		this(axis, 0);
	}

	/**
	 * Creates a relative layout with the components layed out on the specified
	 *  axis using the specfied gap
	 * <p>
	 * All <code>RelativeLayout</code> constructors defer to this one.
	 * @param	 axis  X-AXIS or Y_AXIS
	 * @param	 gap   the gap
	 */
	public RelativeLayout(int axis, int gap)
	{
		setAxis( axis );
		setGap( gap );
		setBorderGap( gap );
	}

	/**
	 *  Gets the layout axis.
	 *  @return	   the layout axis
	 */
	public int getAxis()
	{
		return axis;
	}

	/**
	 *  Sets the layout axis
	 *  @param		axis   the layout axis
	 */
	public void setAxis(int axis)
	{
		if (axis != X_AXIS
		&&  axis != Y_AXIS)
			throw new IllegalArgumentException("invalid axis specified");

		this.axis = axis;
	}

	/**
	 *  Gets the gap between components.
	 *  @return	   the gap between components
	 */
	public int getGap()
	{
		return gap;
	}

	/**
	 *  Sets the gap between components to the specified value.
	 *  @param		gap   the gap between components
	 */
	public void setGap(int gap)
	{
		this.gap = gap < 0 ? 0 : gap;
	}

	/**
	 *  Gets the initial gap. This gap is used before the leading component
	 *  and after the trailing component.
	 *
	 *  @return	   the leading/trailing gap
	 */
	public int getBorderGap()
	{
		return borderGap;
	}

	/**
	 *  Sets the initial gap. This gap is used before the leading component
	 *  and after the trailing component. The default is set to the gap.
	 *
	 *  @param borderGap  the leading/trailing gap
	 */
	public void setBorderGap(int borderGap)
	{
		this.borderGap = borderGap < 0 ? 0 : borderGap;
	}

	/**
	 *  Gets the alignment of the components on the opposite axis.
	 *  @return	   the alignment
	 */
	public float getAlignment()
	{
		return alignment;
	}

	/*
	 *  Set the alignment of the component on the opposite axis.
	 *
	 *  For X-AXIS this would refer to the Y alignemt.
	 *  For Y-AXIS this would refer to the X alignment.
	 *
	 *  Must be between 0.0 and 1.0, or -1. Values can be specified using:
	 *
	 *  RelativeLayout.LEADING
	 *  RelativeLayout.CENTER
	 *  RelativeLayout.TRAILING
	 *  RelativeLayout.COMPONENT - the getAlignemntX/Y method for the
	 *							 opposite axis will be used
	 */
	public void setAlignment(float alignment)
	{
		this.alignment = alignment > 1.0f ? 1.0f : alignment < 0.0f ? -1.0f : alignment;
	}

	/**
	 *  Gets the fill property for the component size on the opposite edge.
	 *  @return	   the fill property
	 */
	public boolean isFill()
	{
		return fill;
	}

	/**
	 *  Change size of relative components to fill the space available
	 *  For X-AXIS aligned components the height will be filled.
	 *  For Y-AXIS aligned components the width will be filled.
	 */
	public void setFill(boolean fill)
	{
		this.fill = fill;
	}

	/**
	 *  Gets the fill gap amount.
	 *  @return	   the fill gap value
	 */
	public int getFillGap()
	{
		return fillGap;
	}

	/**
	 *  Specify the number of pixels by which the fill size is decreased when
	 *  setFill(true) has been specified.
	 */
	public void setFillGap(int fillGap)
	{
		this.fillGap = fillGap;
	}

	/**
	 *  Gets the rounding policy.
	 *  @return	   the rounding policy
	 */
	public int getRoundingPolicy()
	{
		return roundingPolicy;
	}

	/**
	 *  Specify the rounding policy to be used when all the avialable pixels
	 *  have not been allocated to a component.
	 *
	 *  DO_NOTHING
	 *  FIRST - extra pixels added to the first relative component
	 *  LAST - extra pixels added to the last relative component
	 *  LARGEST (default) - extra pixels added to the larger relative component
	 *  EQUAL - a single pixel is added to each relative component
	 *		  (until pixels are used up)
	 */
	public void setRoundingPolicy(int roundingPolicy)
	{
		this.roundingPolicy = roundingPolicy;
	}

	/**
	 *  Gets the constraints for the specified component.
	 *
	 *  @param   component the component to be queried
	 *  @return  the constraint for the specified component, or null
	 *           if component is null or is not present in this layout
     */
    public Float getConstraints(Component component)
    {
    	return (Float)constraints.get(component);
    }

	/**
	 *  Not supported
	 */
	public void addLayoutComponent(String name, Component component) {}

	 /*
	  *	Keep track of any specified constraint for the component.
	  */
	public void addLayoutComponent(Component component, Object constraint)
	{
		if (constraint != null)
			if (constraint instanceof Float)
			{
				constraints.put(component, (Float)constraint);
			}
			else
				throw new IllegalArgumentException("Constraint parameter must be of type Float");
	}

	/**
	 * Removes the specified component from the layout.
	 * @param comp the component to be removed
	 */
	public void removeLayoutComponent(Component comp) {}

	/**
	 * Determines the preferred size of the container argument using
	 * this column layout.
	 * <p>
	 * The preferred width of a column layout is the largest preferred
	 * width of each column in the container, plus the horizontal padding
	 * times the number of columns minus one,
	 * plus the left and right insets of the target container.
	 * <p>
	 * The preferred height of a column layout is the largest preferred
	 * height of each row in the container, plus the vertical padding
	 *  times the number of rows minus one,
	 * plus the top and bottom insets of the target container.
	 *
	 * @param	 target   the container in which to do the layout
	 * @return	the preferred dimensions to lay out the
	 *					  subcomponents of the specified container
	 * @see	   java.awt.RelativeLayout#minimumLayoutSize
	 * @see	   java.awt.Container#getPreferredSize()
	 */
	public Dimension preferredLayoutSize(Container parent)
	{
		synchronized (parent.getTreeLock())
		{
			return getLayoutSize(parent, PREFERRED);
		}
	}

	/**
	 * Determines the minimum size of the container argument using this
	 * column layout.
	 * <p>
	 * The minimum width of a grid layout is the largest minimum width
	 * of each column in the container, plus the horizontal padding
	 * times the number of columns minus one,
	 * plus the left and right insets of the target container.
	 * <p>
	 * The minimum height of a column layout is the largest minimum height
	 * of each row in the container, plus the vertical padding
	 * times the number of rows minus one,
	 * plus the top and bottom insets of the target container.
	 *
	 * @param	 target   the container in which to do the layout
	 * @return	 the minimum dimensions needed to lay out the
	 *			 subcomponents of the specified container
	 * @see		 java.awt.RelativeLayout#preferredLayoutSize
	 * @see		 java.awt.Container#doLayout
	 */
	public Dimension minimumLayoutSize(Container parent)
	{
		synchronized (parent.getTreeLock())
		{
			return getLayoutSize(parent, MINIMUM);
		}
	}

	/**
	 * Lays out the specified container using this layout.
	 * <p>
	 * This method reshapes the components in the specified target
	 * container in order to satisfy the constraints of the
	 * <code>RelativeLayout</code> object.
	 * <p>
	 * The grid layout manager determines the size of individual
	 * components by dividing the free space in the container into
	 * equal-sized portions according to the number of rows and columns
	 * in the layout. The container's free space equals the container's
	 * size minus any insets and any specified horizontal or vertical
	 * gap. All components in a grid layout are given the same size.
	 *
	 * @param	target  the container in which to do the layout
	 * @see		java.awt.Container
	 * @see		java.awt.Container#doLayout
	 */
	public void layoutContainer(Container parent)
	{
		synchronized (parent.getTreeLock())
		{
			if (axis == X_AXIS)
				layoutContainerHorizontally(parent);
			else
				layoutContainerVertically(parent);
		}
	}

	/*
	 *  Lay out all the components in the Container along the X-Axis
	 */
	private void layoutContainerHorizontally(Container parent)
	{
		int components = parent.getComponentCount();
		int visibleComponents = getVisibleComponents( parent );

		if (components == 0) return;

		//  Determine space available for components using relative sizing

		float relativeTotal = 0.0f;
		Insets insets = parent.getInsets();
		int spaceAvailable = parent.getSize().width
						   - insets.left
						   - insets.right
						   - ((visibleComponents - 1) * gap)
						   - (2 * borderGap);

		for (int i = 0 ; i < components ; i++)
		{
			Component component = parent.getComponent(i);

			if (! component.isVisible()) continue;

			Float constraint = constraints.get(component);

			if (constraint == null)
			{
				Dimension d = component.getPreferredSize();
				spaceAvailable -= d.width;
			}
			else
			{
				relativeTotal += constraint.doubleValue();
			}
		}

		//  Allocate space to each component using relative sizing

		int[] relativeSpace = allocateRelativeSpace(parent, spaceAvailable, relativeTotal);

		//  Position each component in the container

		int x = insets.left + borderGap;
		int y = insets.top;
		int insetGap = insets.top + insets.bottom;
		int parentHeight = parent.getSize().height - insetGap;

		for (int i = 0 ; i < components ; i++)
		{
			Component component = parent.getComponent(i);

			if (! component.isVisible()) continue;

			if (i > 0)
				x += gap;

			Dimension d = component.getPreferredSize();

			if (fill)
				d.height = parentHeight - fillGap;

			Float constraint = constraints.get(component);

			if (constraint == null)
			{
				component.setSize( d );
				int locationY = getLocationY(component, parentHeight) + y;
				component.setLocation(x, locationY);
				x += d.width;
			}
			else
			{
				int width = relativeSpace[i];
				component.setSize(width, d.height);
				int locationY = getLocationY(component, parentHeight) + y;
				component.setLocation(x, locationY);
				x += width;
			}
		}
	}

	/*
	 *  Align the component on the Y-Axis
	 */
	private int getLocationY(Component component, int height)
	{
		//  Use the Container alignment policy

		float alignmentY = alignment;

		//  Override with the Component alignment

		if (alignmentY == COMPONENT)
			alignmentY = component.getAlignmentY();

		float y = (height - component.getSize().height) * alignmentY;
		return (int)y;
	}

	/*
	 *  Lay out all the components in the Container along the Y-Axis
	 */
	private void layoutContainerVertically(Container parent)
	{
		int components = parent.getComponentCount();
		int visibleComponents = getVisibleComponents( parent );

		if (components == 0) return;

		//  Determine space available for components using relative sizing

		float relativeTotal = 0.0f;
		Insets insets = parent.getInsets();
		int spaceAvailable = parent.getSize().height
						   - insets.top
						   - insets.bottom
						   - ((visibleComponents - 1) * gap)
						   - (2 * borderGap);

		for (int i = 0 ; i < components ; i++)
		{
			Component component = parent.getComponent(i);

			if (! component.isVisible()) continue;

			Float constraint = constraints.get(component);

			if (constraint == null)
			{
				Dimension d = component.getPreferredSize();
				spaceAvailable -= d.height;
			}
			else
			{
				relativeTotal += constraint.doubleValue();
			}
		}

		//  Allocate space to each component using relative sizing

		int[] relativeSpace = allocateRelativeSpace(parent, spaceAvailable, relativeTotal);

		//  Position each component in the container

		int x = insets.left;
		int y = insets.top + borderGap;
		int insetGap = insets.left + insets.right;
		int parentWidth = parent.getSize().width - insetGap;

		for (int i = 0 ; i < components ; i++)
		{
			Component component = parent.getComponent(i);

			if (! component.isVisible()) continue;

			if (i > 0)
				y += gap;

			Dimension d = component.getPreferredSize();

			if (fill)
				d.width = parentWidth - fillGap;

			Float constraint = constraints.get(component);

			if (constraint == null)
			{
				component.setSize( d );
				int locationX = getLocationX(component, parentWidth) + x;
				component.setLocation(locationX, y);
				y += d.height;
			}
			else
			{
				int height = relativeSpace[i];
				component.setSize(d.width, height);
				int locationX = getLocationX(component, parentWidth) + x;
				component.setLocation(locationX, y);
				y += height;
			}

		}
	}

	/*
	 *  Align the component on the X-Axis
	 */
	private int getLocationX(Component component, int width)
	{
		//  Use the Container alignment policy

		float alignmentX = alignment;

		//  Override with the Component alignment

		if (alignmentX == COMPONENT)
			alignmentX = component.getAlignmentX();

		float x = (width - component.getSize().width) * alignmentX;
		return (int)x;
	}

	/*
	 *  Allocate the space available to each component using relative sizing
	 */
	private int[] allocateRelativeSpace(Container parent, int spaceAvailable, float relativeTotal)
	{
		int spaceUsed = 0;
		int components = parent.getComponentCount();
		int[] relativeSpace = new int[components];

		for (int i = 0 ; i < components ; i++)
		{
			relativeSpace[i] = 0;

			if (relativeTotal > 0 && spaceAvailable > 0)
			{
				Component component = parent.getComponent(i);
				Float constraint = constraints.get(component);

				if (constraint != null)
				{
					int space = (int)(spaceAvailable * constraint.floatValue() / relativeTotal);
					relativeSpace[i] = space;
					spaceUsed += space;
				}
			}
		}

		int spaceRemaining = spaceAvailable - spaceUsed;

		if (relativeTotal > 0 && spaceRemaining > 0)
			adjustForRounding(relativeSpace, spaceRemaining);

		return relativeSpace;
	}

	/*
	 *  Because of rounding, all the space has not been allocated
	 *  Override this method to create a custom rounding policy
	 */
	protected void adjustForRounding(int[] relativeSpace, int spaceRemaining)
	{
		switch(roundingPolicy)
		{
			case DO_NOTHING:
				break;
			case FIRST:
				adjustFirst(relativeSpace, spaceRemaining);
				break;
			case LAST:
				adjustLast(relativeSpace, spaceRemaining);
				break;
			case LARGEST:
				adjustLargest(relativeSpace, spaceRemaining);
				break;
			case EQUAL:
				adjustEqual(relativeSpace, spaceRemaining);
				break;
			default:
				adjustLargest(relativeSpace, spaceRemaining);
		}
	}

	/*
	 *	First component using relative sizing gets all the space
	 */
	private void adjustFirst(int[] relativeSpace, int spaceRemaining)
	{
		for (int i = 0; i < relativeSpace.length; i++)
		{
			if (relativeSpace[i] > 0)
			{
				relativeSpace[i] += spaceRemaining;
				break;
			}
		}
	}

	/*
	 *	Last component using relative sizing gets all the space
	 */
	private void adjustLast(int[] relativeSpace, int spaceRemaining)
	{
		for (int i = relativeSpace.length - 1; i > 0; i--)
		{
			if (relativeSpace[i] > 0)
			{
				relativeSpace[i] += spaceRemaining;
				break;
			}
		}
	}

	/*
	 *	Largest component using relative sizing gets all the space.
	 *  When multiple components are the same size, the last one found is used.
	 */
	private void adjustLargest(int[] relativeSpace, int spaceRemaining)
	{
		int largest = 0;
		int largestSpace = 0;

		for (int i = 0; i < relativeSpace.length; i++)
		{
			int space = relativeSpace[i];

			if (space > 0)
			{
				if (largestSpace < space)
				{
					largestSpace = space;
					largest = i;
				}
			}
		}

		relativeSpace[largest] += spaceRemaining;
	}

	/*
	 *	Each component using relative sizing gets 1 more pixel
	 *  until all the space is used, starting with the first.
	 */
	private void adjustEqual(int[] relativeSpace, int spaceRemaining)
	{
		for (int i = 0; i < relativeSpace.length; i++)
		{
			if (relativeSpace[i] > 0)
			{
				relativeSpace[i]++;
				spaceRemaining--;

				if (spaceRemaining == 0)
					break;
			}
		}
	}

	/*
	 *	Determine the Preferred or Minimum layout size
	 */
	private Dimension getLayoutSize(Container parent, int type)
	{
		int width = 0;
		int height = 0;
		int components = parent.getComponentCount();
		int visibleComponents = getVisibleComponents( parent );

		for (int i = 0 ; i < components ; i++)
		{
			Component component = parent.getComponent(i);

			if (! component.isVisible()) continue;

			Dimension d = getDimension(component, type);

			if (axis == X_AXIS)
			{
				width += d.width;
				height = Math.max(height, d.height);
			}
			else
			{
				width = Math.max(width, d.width);
				height += d.height;
			}
		}

		Insets insets = parent.getInsets();
		int totalGap = ((visibleComponents - 1) * gap) + (2 * borderGap);

		if (axis == X_AXIS)
		{
			width += insets.left + insets.right + totalGap;
			height += insets.top + insets.bottom;
		}
		else
		{
			width += insets.left + insets.right;
			height += insets.top + insets.bottom + totalGap;
		}

		Dimension size = new Dimension(width, height);
		return size;
	}

	private int getVisibleComponents(Container container)
	{
		int visibleComponents = 0;

		for (Component component : container.getComponents())
		{
        	if (component.isVisible())
        		visibleComponents++;
		}

		return visibleComponents;
	}

	private Dimension getDimension(Component component, int type)
	{
		switch (type)
		{
			case PREFERRED: return component.getPreferredSize();
			case MINIMUM:   return component.getMinimumSize();
			default: return new Dimension(0, 0);
		}
	}

	/**
	 * There is no maximum.
	 */
	public Dimension maximumLayoutSize(Container target)
	{
		return new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE);
	}

	/**
	 * Returns the alignment along the x axis.  Use center alignment.
	 */
	public float getLayoutAlignmentX(Container parent)
	{
		return 0.5f;
	}

	/**
	 * Returns the alignment along the y axis.  Use center alignment.
	 */
	public float getLayoutAlignmentY(Container parent)
	{
		return 0.5f;
	}

	/**
	 * Invalidates the layout, indicating that if the layout manager
	 * has cached information it should be discarded.
	 */
	public void invalidateLayout(Container target)
	{
		// remove constraints here?
	}

	/**
	 * Returns the string representation of this column layout's values.
	 * @return	 a string representation of this grid layout
	 */
	public String toString()
	{
		return getClass().getName()
			+ "[axis=" + axis
			+ ",gap=" + gap
			+ "]";
	}
}
