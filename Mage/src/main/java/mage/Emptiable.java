package mage;

import mage.constants.ManaType;

/**
 * @author TheElk801
 */
public interface Emptiable {

    public void add(ManaType manaType, int amount);

    public void clear(ManaType manaType);

    public int get(final ManaType manaType);
}
