package mage.abilities.keyword;

import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;

/**
 *
 * @author Styxo
 */
public class BountyAbility extends DiesCreatureTriggeredAbility {

    private static final String staticTriggerPhrase = "<i>Bounty</i> &mdash; ";
    private static final FilterCreaturePermanent bountyCounterFilter = new FilterCreaturePermanent("creature an opponent controls with a bounty counter on it");

    static {
        bountyCounterFilter.add(TargetController.OPPONENT.getControllerPredicate());
        bountyCounterFilter.add(CounterType.BOUNTY.getPredicate());
    }

    public BountyAbility(Effect effect) {
        this(effect, false);
    }

    public BountyAbility(Effect effect, boolean optional) {
        this(effect, optional, false);
    }

    public BountyAbility(Effect effect, boolean optional, boolean setTargetPointer) {
        super(effect, optional, bountyCounterFilter, setTargetPointer);
    }

    public BountyAbility(final BountyAbility ability) {
        super(ability);
    }

    @Override
    public BountyAbility copy() {
        return new BountyAbility(this);
    }

    @Override
    public String getStaticTriggerPhrase() {
        return staticTriggerPhrase;
    }
}
