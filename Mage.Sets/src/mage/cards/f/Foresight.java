
package mage.cards.f;

import java.util.List;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.delayed.AtTheBeginOfNextUpkeepDelayedTriggeredAbility;
import mage.abilities.effects.SearchEffect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author TheElk801
 */
public final class Foresight extends CardImpl {

    public Foresight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{U}");

        // Search your library for three cards, exile them, then shuffle your library.
        this.getSpellAbility().addEffect(new ForesightEffect());

        // Draw a card at the beginning of the next turn's upkeep.
        this.getSpellAbility().addEffect(new CreateDelayedTriggeredAbilityEffect(new AtTheBeginOfNextUpkeepDelayedTriggeredAbility(new DrawCardSourceControllerEffect(1)), false));
    }

    public Foresight(final Foresight card) {
        super(card);
    }

    @Override
    public Foresight copy() {
        return new Foresight(this);
    }
}

class ForesightEffect extends SearchEffect {

    ForesightEffect() {
        super(new TargetCardInLibrary(3, new FilterCard()), Outcome.Benefit);
        staticText = "Search your library for three cards, exile them, then shuffle your library.";
    }

    ForesightEffect(final ForesightEffect effect) {
        super(effect);
    }

    @Override
    public ForesightEffect copy() {
        return new ForesightEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player.searchLibrary(target, game)) {
            for (UUID targetId : getTargets()) {
                Card card = player.getLibrary().getCard(targetId, game);
                if (card != null) {
                    card.moveToExile(null, null, targetId, game);
                }
            }
            return true;
        }
        player.shuffleLibrary(source, game);
        return false;
    }

    public List<UUID> getTargets() {
        return target.getTargets();
    }

}
