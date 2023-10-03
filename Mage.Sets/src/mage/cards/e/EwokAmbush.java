
package mage.cards.e;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.EwokToken;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author Styxo
 */
public final class EwokAmbush extends CardImpl {

    public EwokAmbush(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{2}{G}");

        // Create two 1/1 green Ewok creature tokens. Those tokens gain haste until end of turn. 
        this.getSpellAbility().addEffect(new EwokAmbushCreateTokenEffect());
    }

    private EwokAmbush(final EwokAmbush card) {
        super(card);
    }

    @Override
    public EwokAmbush copy() {
        return new EwokAmbush(this);
    }
}

class EwokAmbushCreateTokenEffect extends OneShotEffect {

    public EwokAmbushCreateTokenEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "Create two 1/1 green Ewok creature tokens. Those tokens gain haste until end of turn";
    }

    private EwokAmbushCreateTokenEffect(final EwokAmbushCreateTokenEffect effect) {
        super(effect);
    }

    @Override
    public EwokAmbushCreateTokenEffect copy() {
        return new EwokAmbushCreateTokenEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            CreateTokenEffect effect = new CreateTokenEffect(new EwokToken(), 2);
            effect.apply(game, source);
            for (UUID tokenId : effect.getLastAddedTokenIds()) {
                Permanent token = game.getPermanent(tokenId);
                if (token != null) {
                    ContinuousEffect continuousEffect = new GainAbilityTargetEffect(HasteAbility.getInstance(), Duration.EndOfTurn);
                    continuousEffect.setTargetPointer(new FixedTarget(tokenId));
                    game.addEffect(continuousEffect, source);
                }
            }

            return true;
        }
        return false;
    }
}
