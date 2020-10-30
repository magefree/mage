/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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

    private static final FilterCreaturePermanent bountyCounterFilter = new FilterCreaturePermanent("creature an opponent controls with a bounty counter on it");

    static {
        bountyCounterFilter.add(TargetController.OPPONENT.getControllerPredicate());
        bountyCounterFilter.add(CounterType.BOUNTY.getPredicate());
    }

    public BountyAbility(Effect effect) {
        super(effect, false, bountyCounterFilter);
    }

    public BountyAbility(Effect effect, boolean optional) {
        super(effect, optional, bountyCounterFilter);
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
    public String getRule() {
        return "<i>Bounty</i> &mdash; " + super.getRule();
    }

}
