package mage.abilities.costs;

/**
 * @author jayDi85
 */
public interface MinMaxVariableCost extends VariableCost {

    int getMinX();

    void setMinX(int minX);

    int getMaxX();

    void setMaxX(int maxX);
}
