
package mage.cards.w;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.delayed.AtTheBeginOfNextCleanupDelayedTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.Token;
import mage.game.permanent.token.WaylayToken;
import mage.target.targetpointer.FixedTargets;

/**
 *
 * @author TheElk801
 */
public final class Waylay extends CardImpl {

    public Waylay(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{W}");

        // Create three 2/2 white Knight creature tokens. Exile them at the beginning of the next cleanup step.
        this.getSpellAbility().addEffect(new WaylayEffect());
    }

    private Waylay(final Waylay card) {
        super(card);
    }

    @Override
    public Waylay copy() {
        return new Waylay(this);
    }
}

class WaylayEffect extends OneShotEffect {

    public WaylayEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "Create three 2/2 white Knight creature tokens. Exile them at the beginning of the next cleanup step.";
    }

    private WaylayEffect(final WaylayEffect effect) {
        super(effect);
    }

    @Override
    public WaylayEffect copy() {
        return new WaylayEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Token token = new WaylayToken();
        token.putOntoBattlefield(3, game, source, source.getControllerId());
        List<Permanent> toExile = new ArrayList<>();
        for (UUID tokenId : token.getLastAddedTokenIds()) {
            Permanent tokenPermanent = game.getPermanent(tokenId);
            if (tokenPermanent != null) {
                toExile.add(tokenPermanent);
            }
        }
        Effect effect = new ExileTargetEffect();
        effect.setTargetPointer(new FixedTargets(toExile, game));
        game.addDelayedTriggeredAbility(new AtTheBeginOfNextCleanupDelayedTriggeredAbility(effect), source);
        return true;
    }
}
