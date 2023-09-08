
package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.SacrificeTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.ThatcherHumanToken;
import mage.target.targetpointer.FixedTargets;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 *
 * @author North
 */
public final class ThatcherRevolt extends CardImpl {

    public ThatcherRevolt(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{R}");

        // Create three 1/1 red Human creature tokens with haste. Sacrifice those tokens at the beginning of the next end step.
        this.getSpellAbility().addEffect(new ThatcherRevoltEffect());
    }

    private ThatcherRevolt(final ThatcherRevolt card) {
        super(card);
    }

    @Override
    public ThatcherRevolt copy() {
        return new ThatcherRevolt(this);
    }
}

class ThatcherRevoltEffect extends OneShotEffect {

    public ThatcherRevoltEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "Create three 1/1 red Human creature tokens with haste. Sacrifice those tokens at the beginning of the next end step";
    }

    private ThatcherRevoltEffect(final ThatcherRevoltEffect effect) {
        super(effect);
    }

    @Override
    public ThatcherRevoltEffect copy() {
        return new ThatcherRevoltEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        ThatcherHumanToken token = new ThatcherHumanToken();
        token.putOntoBattlefield(3, game, source, source.getControllerId());
        List<Permanent> toSacrifice = new ArrayList<>();
        for (UUID tokenId : token.getLastAddedTokenIds()) {
            Permanent tokenPermanent = game.getPermanent(tokenId);
            if (tokenPermanent != null) {
                toSacrifice.add(tokenPermanent);
            }
        }
        SacrificeTargetEffect sacrificeEffect = new SacrificeTargetEffect();
        sacrificeEffect.setTargetPointer(new FixedTargets(toSacrifice, game));
        game.addDelayedTriggeredAbility(new AtTheBeginOfNextEndStepDelayedTriggeredAbility(sacrificeEffect), source);
        return true;
    }
}
