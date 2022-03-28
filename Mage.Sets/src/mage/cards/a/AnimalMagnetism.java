package mage.cards.a;

import java.util.Set;
import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetCard;
import mage.target.common.TargetOpponent;

/**
 *
 * @author fenhl
 */
public final class AnimalMagnetism extends CardImpl {

    public AnimalMagnetism(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{G}");

        // Reveal the top five cards of your library. An opponent chooses a creature card from among them. Put that card onto the battlefield and the rest into your graveyard.
        this.getSpellAbility().addEffect(new AnimalMagnetismEffect());
    }

    private AnimalMagnetism(final AnimalMagnetism card) {
        super(card);
    }

    public AnimalMagnetism copy() {
        return new AnimalMagnetism(this);
    }
}

class AnimalMagnetismEffect extends OneShotEffect {

    public AnimalMagnetismEffect() {
        super(Outcome.Benefit);
        this.staticText = "Reveal the top five cards of your library. An opponent chooses a creature card from among them. Put that card onto the battlefield and the rest into your graveyard";
    }

    public AnimalMagnetismEffect(final AnimalMagnetismEffect effect) {
        super(effect);
    }

    @Override
    public AnimalMagnetismEffect copy() {
        return new AnimalMagnetismEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source);
        if (sourceObject != null && controller != null) {
            Cards cards = new CardsImpl(controller.getLibrary().getTopCards(game, 5));
            if (!cards.isEmpty()) {
                controller.revealCards(staticText, cards, game);
                Card cardToBattlefield;
                if (cards.size() == 1) {
                    cardToBattlefield = cards.getRandom(game);
                } else {
                    Player opponent;
                    Set<UUID> opponents = game.getOpponents(controller.getId());
                    if (opponents.size() == 1) {
                        opponent = game.getPlayer(opponents.iterator().next());
                    } else {
                        Target target = new TargetOpponent(true);
                        controller.chooseTarget(Outcome.Detriment, target, source, game);
                        opponent = game.getPlayer(target.getFirstTarget());
                    }
                    TargetCard target = new TargetCard(1, Zone.LIBRARY, StaticFilters.FILTER_CARD_CREATURE);
                    opponent.chooseTarget(outcome, cards, target, source, game);
                    cardToBattlefield = game.getCard(target.getFirstTarget());
                }
                if (cardToBattlefield != null) {
                    controller.moveCards(cardToBattlefield, Zone.BATTLEFIELD, source, game);
                    cards.remove(cardToBattlefield);
                }
                controller.moveCards(cards, Zone.GRAVEYARD, source, game);
            }
            return true;
        }
        return false;
    }
}
