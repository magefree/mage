
package mage.cards.e;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetCardInGraveyard;

/**
 *
 * @author fireshoes
 */
public final class ExtractFromDarkness extends CardImpl {

    public ExtractFromDarkness(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{3}{U}{B}");

        // Each player puts the top two cards of their library into their graveyard.
        this.getSpellAbility().addEffect(new ExtractFromDarknessMillEffect());
        // Then put a creature card from a graveyard onto the battlefield under your control.
        this.getSpellAbility().addEffect(new ExtractFromDarknessReturnFromGraveyardToBattlefieldEffect());
    }

    public ExtractFromDarkness(final ExtractFromDarkness card) {
        super(card);
    }

    @Override
    public ExtractFromDarkness copy() {
        return new ExtractFromDarkness(this);
    }
}

class ExtractFromDarknessMillEffect extends OneShotEffect {

    ExtractFromDarknessMillEffect() {
        super(Outcome.Detriment);
        staticText = "Each player puts the top two cards of their library into their graveyard";
    }

    ExtractFromDarknessMillEffect(final ExtractFromDarknessMillEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (UUID playerId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
            Player player = game.getPlayer(playerId);
            if (player != null) {
                player.moveCards(player.getLibrary().getTopCards(game, 2), Zone.GRAVEYARD, source, game);
            }
        }
        return true;
    }

    @Override
    public ExtractFromDarknessMillEffect copy() {
        return new ExtractFromDarknessMillEffect(this);
    }
}

class ExtractFromDarknessReturnFromGraveyardToBattlefieldEffect extends OneShotEffect {

    public ExtractFromDarknessReturnFromGraveyardToBattlefieldEffect() {
        super(Outcome.PutCreatureInPlay);
        staticText = "Put a creature card from a graveyard onto the battlefield under your control";
    }

    public ExtractFromDarknessReturnFromGraveyardToBattlefieldEffect(final ExtractFromDarknessReturnFromGraveyardToBattlefieldEffect effect) {
        super(effect);
    }

    @Override
    public ExtractFromDarknessReturnFromGraveyardToBattlefieldEffect copy() {
        return new ExtractFromDarknessReturnFromGraveyardToBattlefieldEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Target target = new TargetCardInGraveyard(new FilterCreatureCard());
            target.setNotTarget(true);
            if (target.canChoose(source.getSourceId(), source.getControllerId(), game)
                    && controller.chooseTarget(outcome, target, source, game)) {
                return controller.moveCards(game.getCard(target.getFirstTarget()), Zone.BATTLEFIELD, source, game);
            }
            return true;
        }
        return false;
    }
}
