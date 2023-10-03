
package mage.cards.t;

import java.util.UUID;
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
import mage.game.permanent.token.TidalWaveWallToken;
import mage.game.permanent.token.Token;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author nigelzor
 */
public final class TidalWave extends CardImpl {

    public TidalWave(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{U}");

        // Create a 5/5 blue Wall creature token with defender. Sacrifice it at the beginning of the next end step.
        this.getSpellAbility().addEffect(new TidalWaveEffect());
    }

    private TidalWave(final TidalWave card) {
        super(card);
    }

    @Override
    public TidalWave copy() {
        return new TidalWave(this);
    }
}

class TidalWaveEffect extends OneShotEffect {

    public TidalWaveEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "Create a 5/5 blue Wall creature token with defender. Sacrifice it at the beginning of the next end step.";
    }

    private TidalWaveEffect(final TidalWaveEffect effect) {
        super(effect);
    }

    @Override
    public TidalWaveEffect copy() {
        return new TidalWaveEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Token token = new TidalWaveWallToken();
        if (token.putOntoBattlefield(1, game, source, source.getControllerId())) {
            for (UUID tokenId : token.getLastAddedTokenIds()) {
                Permanent tokenPermanent = game.getPermanent(tokenId);
                if (tokenPermanent != null) {
                    SacrificeTargetEffect sacrificeEffect = new SacrificeTargetEffect();
                    sacrificeEffect.setTargetPointer(new FixedTarget(tokenPermanent, game));
                    game.addDelayedTriggeredAbility(new AtTheBeginOfNextEndStepDelayedTriggeredAbility(sacrificeEffect), source);
                }
            }
            return true;
        }
        return false;
    }
}
