package mage.target.common;

import mage.filter.common.FilterCreatureOrPlaneswalkerPermanent;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class TargetCreatureOrPlaneswalkerAmount extends TargetPermanentAmount {

    private static final FilterCreatureOrPlaneswalkerPermanent defaultFilter
            = new FilterCreatureOrPlaneswalkerPermanent("target creatures and/or planeswalkers");

    /**
     * <b>IMPORTANT</b>: Use more specific constructor if {@code amount} is not always the same number!<br>
     * {@code minNumberOfTargets} defaults to zero for {@code amount} > 3, otherwise to one, in line with typical templating.<br>
     * {@code maxNumberOfTargets} defaults to {@code amount}.<br>
     * {@code filter} defaults to all creature and planeswalker permanents. ("target creatures and/or planeswalkers")
     *
     * @see TargetCreatureOrPlaneswalkerAmount#TargetCreatureOrPlaneswalkerAmount(int, int, int, FilterCreatureOrPlaneswalkerPermanent)
     */
    public TargetCreatureOrPlaneswalkerAmount(int amount) {
        this(amount, defaultFilter);
    }

    /**
     * <b>IMPORTANT</b>: Use more specific constructor if {@code amount} is not always the same number!<br>
     * {@code minNumberOfTargets} defaults to zero for {@code amount} > 3, otherwise to one, in line with typical templating.<br>
     * {@code maxNumberOfTargets} defaults to {@code amount}.
     *
     * @see TargetCreatureOrPlaneswalkerAmount#TargetCreatureOrPlaneswalkerAmount(int, int, int, FilterCreatureOrPlaneswalkerPermanent)
     */
    public TargetCreatureOrPlaneswalkerAmount(int amount, FilterCreatureOrPlaneswalkerPermanent filter) {
        this(amount, amount > 3 ? 0 : 1, amount, filter);
    }

    /**
     * {@code filter} defaults to all creature and planeswalker permanents. ("target creatures and/or planeswalkers")
     *
     * @see TargetCreatureOrPlaneswalkerAmount#TargetCreatureOrPlaneswalkerAmount(int, int, int, FilterCreatureOrPlaneswalkerPermanent)
     */
    public TargetCreatureOrPlaneswalkerAmount(int amount, int minNumberOfTargets, int maxNumberOfTargets) {
        this(amount, minNumberOfTargets, maxNumberOfTargets, defaultFilter);
    }

    /**
     * @param amount             Amount of stuff (e.g. damage, counters) to distribute.
     * @param minNumberOfTargets Minimum number of targets.
     * @param maxNumberOfTargets Maximum number of targets. If no lower max is needed, set to {@code amount}.
     * @param filter             Filter for creatures and/or planeswalkers that something will be distributed amongst.
     */
    public TargetCreatureOrPlaneswalkerAmount(int amount, int minNumberOfTargets, int maxNumberOfTargets, FilterCreatureOrPlaneswalkerPermanent filter) {
        super(amount, minNumberOfTargets, maxNumberOfTargets, filter);
    }

    private TargetCreatureOrPlaneswalkerAmount(final TargetCreatureOrPlaneswalkerAmount target) {
        super(target);
    }

    @Override
    public TargetCreatureOrPlaneswalkerAmount copy() {
        return new TargetCreatureOrPlaneswalkerAmount(this);
    }
}
