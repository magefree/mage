package mage.cards.o;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.abilities.effects.common.EntersBattlefieldWithXCountersEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class OchreJelly extends CardImpl {

    public OchreJelly(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{X}{G}");

        this.subtype.add(SubType.OOZE);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Ochre Jelly enters the battlefield with X +1/+1 counters on it.
        this.addAbility(new EntersBattlefieldAbility(new EntersBattlefieldWithXCountersEffect(CounterType.P1P1.createInstance())));

        // Split — When Ochre Jelly dies, if it had two or more +1/+1 counters on it, create a token that's a copy of it at the beginning of the next end step. That token enters the battlefield with half that many +1/+1 counters on it, rounded down.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new DiesSourceTriggeredAbility(new CreateDelayedTriggeredAbilityEffect(
                        new AtTheBeginOfNextEndStepDelayedTriggeredAbility(new OchreJellyEffect())
                )), OchreJellyCondition.instance, CardUtil.italicizeWithEmDash("Split")
                + "When {this} dies, if it had two or more +1/+1 counters on it, "
                + "create a token that's a copy of it at the beginning of the next end step. "
                + "The token enters the battlefield with half that many +1/+1 counters on it, rounded down."
        ));
    }

    private OchreJelly(final OchreJelly card) {
        super(card);
    }

    @Override
    public OchreJelly copy() {
        return new OchreJelly(this);
    }
}

enum OchreJellyCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = (Permanent) source.getEffects().get(0).getValue("permanentLeftBattlefield");
        return permanent != null && permanent.getCounters(game).getCount(CounterType.P1P1) >= 2;
    }
}

class OchreJellyEffect extends OneShotEffect {

    OchreJellyEffect() {
        super(Outcome.Benefit);
        staticText = "create a token that's a copy of {this}";
    }

    private OchreJellyEffect(final OchreJellyEffect effect) {
        super(effect);
    }

    @Override
    public OchreJellyEffect copy() {
        return new OchreJellyEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = (Permanent) getValue("permanentLeftBattlefield");
        if (permanent == null) {
            return false;
        }
        final int counters = permanent.getCounters(game).getCount(CounterType.P1P1) / 2;
        CreateTokenCopyTargetEffect effect = new CreateTokenCopyTargetEffect(CounterType.P1P1, counters);
        effect.setSavedPermanent(permanent);
        effect.apply(game, source);
        return true;
    }
}
