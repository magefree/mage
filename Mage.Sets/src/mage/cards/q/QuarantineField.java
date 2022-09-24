
package mage.cards.q;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.delayed.OnLeaveReturnExiledToBattlefieldAbility;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.EntersBattlefieldWithXCountersEffect;
import mage.abilities.effects.common.ExileUntilSourceLeavesEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.filter.common.FilterNonlandPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.targetadjustment.TargetAdjuster;

import java.util.UUID;

/**
 * @author awjackson
 */
public final class QuarantineField extends CardImpl {

    public QuarantineField(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{X}{X}{W}{W}");

        // Quarantine Field enters the battlefield with X isolation counters on it.
        this.addAbility(new EntersBattlefieldAbility(new EntersBattlefieldWithXCountersEffect(CounterType.ISOLATION.createInstance())));

        // When Quarantine Field enters the battlefield, for each isolation counter on it, exile up to one target nonland permanent an opponent controls until Quarantine Field leaves the battlefield.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ExileUntilSourceLeavesEffect()
                .setText("for each isolation counter on it, exile up to one target nonland permanent an opponent controls until {this} leaves the battlefield")
        );
        ability.addEffect(new CreateDelayedTriggeredAbilityEffect(new OnLeaveReturnExiledToBattlefieldAbility()));
        ability.setTargetAdjuster(QuarantineFieldAdjuster.instance);
        this.addAbility(ability);
    }

    private QuarantineField(final QuarantineField card) {
        super(card);
    }

    @Override
    public QuarantineField copy() {
        return new QuarantineField(this);
    }
}

enum QuarantineFieldAdjuster implements TargetAdjuster {
    instance;

    @Override
    public void adjustTargets(Ability ability, Game game) {
        Permanent sourceObject = game.getPermanent(ability.getSourceId());
        if (sourceObject != null) {
            int counters = sourceObject.getCounters(game).getCount(CounterType.ISOLATION);
            FilterNonlandPermanent filter = new FilterNonlandPermanent("nonland permanent" + (counters > 1 ? "s" : "") + " an opponent controls");
            filter.add(TargetController.OPPONENT.getControllerPredicate());
            ability.addTarget(new TargetPermanent(0, counters, filter));
        }
    }
}
