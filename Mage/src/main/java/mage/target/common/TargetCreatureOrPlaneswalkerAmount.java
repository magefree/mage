package mage.target.common;

import mage.abilities.dynamicvalue.DynamicValue;
import mage.filter.common.FilterCreatureOrPlaneswalkerPermanent;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class TargetCreatureOrPlaneswalkerAmount extends TargetPermanentAmount {

    private static final FilterCreatureOrPlaneswalkerPermanent defaultFilter
            = new FilterCreatureOrPlaneswalkerPermanent();

    public TargetCreatureOrPlaneswalkerAmount(int amount) {
        super(amount, defaultFilter);
    }

    public TargetCreatureOrPlaneswalkerAmount(DynamicValue amount) {
        super(amount, defaultFilter);
    }

    public TargetCreatureOrPlaneswalkerAmount(int amount, FilterCreatureOrPlaneswalkerPermanent filter) {
        super(amount, filter);
    }

    public TargetCreatureOrPlaneswalkerAmount(DynamicValue amount, FilterCreatureOrPlaneswalkerPermanent filter) {
        super(amount, filter);
    }

    private TargetCreatureOrPlaneswalkerAmount(final TargetCreatureOrPlaneswalkerAmount target) {
        super(target);
    }

    @Override
    public TargetCreatureOrPlaneswalkerAmount copy() {
        return new TargetCreatureOrPlaneswalkerAmount(this);
    }
}
