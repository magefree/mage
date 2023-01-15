package mage.cards.u;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfCombatTriggeredAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.effects.common.SacrificeEffect;
import mage.abilities.effects.common.SacrificeTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.token.PhyrexianHorrorToken;
import mage.game.permanent.token.Token;
import mage.target.targetpointer.FixedTarget;
import mage.target.targetpointer.FixedTargets;

/**
 * @author TheElk801
 */
public final class UrabrasksForge extends CardImpl {

    public UrabrasksForge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}{R}");

        // At the beginning of combat on your turn, put an oil counter on Urabrask's Forge, then create an X/1 red Phyrexian Horror creature token with trample and haste, where X is the number of oil counters on Urabrask's Forge. Sacrifice that token at the beginning of the next end step.
        Ability ability = new BeginningOfCombatTriggeredAbility(
                new AddCountersSourceEffect(CounterType.OIL.createInstance()), TargetController.YOU, false
        );
        ability.addEffect(new UrabrasksForgeEffect());
        this.addAbility(ability);
    }

    private UrabrasksForge(final UrabrasksForge card) {
        super(card);
    }

    @Override
    public UrabrasksForge copy() {
        return new UrabrasksForge(this);
    }
}

class UrabrasksForgeEffect extends OneShotEffect {

    UrabrasksForgeEffect() {
        super(Outcome.Benefit);
        staticText = ", then create an X/1 red Phyrexian Horror creature token with trample and haste, " +
                "where X is the number of oil counters on {this}. " +
                "Sacrifice that token at the beginning of the next end step";
    }

    private UrabrasksForgeEffect(final UrabrasksForgeEffect effect) {
        super(effect);
    }

    @Override
    public UrabrasksForgeEffect copy() {
        return new UrabrasksForgeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int amount = Optional
                .ofNullable(source.getSourcePermanentOrLKI(game))
                .filter(Objects::nonNull)
                .map(permanent -> permanent.getCounters(game))
                .map(counters -> counters.getCount(CounterType.OIL))
                .orElse(0);
        Token token = new PhyrexianHorrorToken(amount);
        token.putOntoBattlefield(1, game, source);
        game.addDelayedTriggeredAbility(new AtTheBeginOfNextEndStepDelayedTriggeredAbility(
                new SacrificeTargetEffect().setText("sacrifice it")
                        .setTargetPointer(new FixedTargets(token, game))
        ), source);
        return true;
    }
}
