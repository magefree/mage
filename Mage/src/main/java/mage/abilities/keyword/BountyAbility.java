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
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.filter.predicate.permanent.CounterPredicate;

/**
 *
 * @author Styxo
 */
public class BountyAbility extends DiesCreatureTriggeredAbility {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature an opponent controls with a bounty counter on it");

    static {
        filter.add(new ControllerPredicate(TargetController.OPPONENT));
        filter.add(new CounterPredicate(CounterType.BOUNTY));
    }

    public BountyAbility(Effect effect) {
        super(effect, false, filter);
    }

    public BountyAbility(Effect effect, boolean optional) {
        super(effect, optional, filter);
    }

    public BountyAbility(Effect effect, boolean optional, boolean setTargetPointer) {
        super(effect, optional, filter, setTargetPointer);
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
