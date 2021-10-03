package mage.cards.h;

import mage.abilities.Ability;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.SacrificeTargetEffect;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TimingRule;
import mage.game.Game;
import mage.game.permanent.token.HungryForMoreToken;
import mage.game.permanent.token.Token;
import mage.target.targetpointer.FixedTargets;

import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author TheElk801
 */
public final class HungryForMore extends CardImpl {

    public HungryForMore(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{B}{R}");

        // Create a 3/1 black and red Vampire creature token with trample, lifelink, and haste. Sacrifice it at the beginning of the next end step.
        this.getSpellAbility().addEffect(new HungryForMoreEffect());

        // Flashback {1}{B}{R}
        this.addAbility(new FlashbackAbility(this, new ManaCostsImpl<>("{1}{B}{R}")));
    }

    private HungryForMore(final HungryForMore card) {
        super(card);
    }

    @Override
    public HungryForMore copy() {
        return new HungryForMore(this);
    }
}

class HungryForMoreEffect extends OneShotEffect {

    HungryForMoreEffect() {
        super(Outcome.Benefit);
        staticText = "create a 3/1 black and red Vampire creature token with trample, " +
                "lifelink, and haste. Sacrifice it at the beginning of the next end step";
    }

    private HungryForMoreEffect(final HungryForMoreEffect effect) {
        super(effect);
    }

    @Override
    public HungryForMoreEffect copy() {
        return new HungryForMoreEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Token token = new HungryForMoreToken();
        token.putOntoBattlefield(1, game, source, source.getControllerId());
        game.addDelayedTriggeredAbility(new AtTheBeginOfNextEndStepDelayedTriggeredAbility(
                new SacrificeTargetEffect().setTargetPointer(new FixedTargets(
                        token.getLastAddedTokenIds()
                                .stream()
                                .map(game::getPermanent)
                                .collect(Collectors.toList()),
                        game
                )).setText("sacrifice that token")
        ), source);
        return true;
    }
}
