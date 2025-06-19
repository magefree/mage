package mage.target.common;

import mage.filter.StaticFilters;
import mage.target.TargetPermanent;

/**
 * @author LevelX2
 */
public class TargetCreatureOrPlaneswalker extends TargetPermanent {

    public TargetCreatureOrPlaneswalker() {
        this(1);
    }

    public TargetCreatureOrPlaneswalker(int numTargets) {
        this(numTargets, numTargets);
    }

    public TargetCreatureOrPlaneswalker(int minNumTargets, int maxNumTargets) {
        super(minNumTargets, maxNumTargets, StaticFilters.FILTER_PERMANENT_CREATURE_OR_PLANESWALKER, false);
    }

    protected TargetCreatureOrPlaneswalker(final TargetCreatureOrPlaneswalker target) {
        super(target);
    }

    @Override
    public TargetCreatureOrPlaneswalker copy() {
        return new TargetCreatureOrPlaneswalker(this);
    }
}
