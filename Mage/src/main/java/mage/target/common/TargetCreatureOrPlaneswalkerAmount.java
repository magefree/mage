package mage.target.common;

import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreatureOrPlaneswalkerPermanent;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class TargetCreatureOrPlaneswalkerAmount extends TargetPermanentAmount {

    private static final FilterCreatureOrPlaneswalkerPermanent defaultFilter
            = new FilterCreatureOrPlaneswalkerPermanent("target creatures and/or planeswalkers");

    public TargetCreatureOrPlaneswalkerAmount(int amount, int minNumberOfTargets) {
        super(amount, minNumberOfTargets, defaultFilter);
    }

    public TargetCreatureOrPlaneswalkerAmount(int amount, int minNumberOfTargets, int maxNumberOfTargets) {
        super(amount, minNumberOfTargets, maxNumberOfTargets, defaultFilter);
    }

    public TargetCreatureOrPlaneswalkerAmount(int amount, int minNumberOfTargets, FilterPermanent filter) {
        super(amount, minNumberOfTargets, filter);
    }

    private TargetCreatureOrPlaneswalkerAmount(final TargetCreatureOrPlaneswalkerAmount target) {
        super(target);
    }

    @Override
    public TargetCreatureOrPlaneswalkerAmount copy() {
        return new TargetCreatureOrPlaneswalkerAmount(this);
    }
}
