
package mage.cards.l;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.delayed.AtTheBeginOfNextUpkeepDelayedTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;

/**
 *
 * @author TheElk801
 */
public final class LatNamsLegacy extends CardImpl {

    public LatNamsLegacy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U}");

        // Shuffle a card from your hand into your library. If you do, draw two cards at the beginning of the next turn's upkeep.
        this.getSpellAbility().addEffect(new LatNamsLegacyEffect());
    }

    private LatNamsLegacy(final LatNamsLegacy card) {
        super(card);
    }

    @Override
    public LatNamsLegacy copy() {
        return new LatNamsLegacy(this);
    }
}

class LatNamsLegacyEffect extends OneShotEffect {

    public LatNamsLegacyEffect() {
        super(Outcome.DrawCard);
        staticText = "Shuffle a card from your hand into your library. If you do, draw two cards at the beginning of the next turn's upkeep";
    }

    public LatNamsLegacyEffect(LatNamsLegacyEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null && !controller.getHand().isEmpty()) {
            TargetCard target = new TargetCard(Zone.HAND, new FilterCard("card to shuffle into your library"));
            controller.choose(Outcome.Detriment, controller.getHand(), target, source, game);
            Card card = controller.getHand().get(target.getFirstTarget(), game);
            if (card != null) {
                boolean successful = controller.moveCards(card, Zone.LIBRARY, source, game);
                controller.shuffleLibrary(source, game);
                if (successful) {
                    new CreateDelayedTriggeredAbilityEffect(
                            new AtTheBeginOfNextUpkeepDelayedTriggeredAbility(
                                    new DrawCardSourceControllerEffect(2)
                            ), false
                    ).apply(game, source);
                }
            }
            return true;

        }
        return true;
    }

    @Override
    public LatNamsLegacyEffect copy() {
        return new LatNamsLegacyEffect(this);
    }

}
