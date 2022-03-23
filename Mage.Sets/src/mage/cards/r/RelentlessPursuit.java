package mage.cards.r;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;
import mage.filter.common.FilterLandCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RelentlessPursuit extends CardImpl {

    public RelentlessPursuit(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{G}");

        // Reveal the top four cards of your library. You may put a creature card and/or land card from among them into your hand. Put the rest into your graveyard.
        this.getSpellAbility().addEffect(new RelentlessPursuitEffect());
    }

    private RelentlessPursuit(final RelentlessPursuit card) {
        super(card);
    }

    @Override
    public RelentlessPursuit copy() {
        return new RelentlessPursuit(this);
    }
}

class RelentlessPursuitEffect extends OneShotEffect {

    private static final FilterCard creatureFilter
            = new FilterCreatureCard("creature card to put into your hand");
    private static final FilterCard landFilter
            = new FilterLandCard("land card to put into your hand");

    RelentlessPursuitEffect() {
        super(Outcome.DrawCard);
        this.staticText = "Reveal the top four cards of your library. " +
                "You may put a creature card and/or a land card from among them into your hand." +
                " Put the rest into your graveyard";
    }

    private RelentlessPursuitEffect(final RelentlessPursuitEffect effect) {
        super(effect);
    }

    @Override
    public RelentlessPursuitEffect copy() {
        return new RelentlessPursuitEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source);
        if (controller == null || sourceObject == null) {
            return false;
        }
        Cards cards = new CardsImpl(controller.getLibrary().getTopCards(game, 4));
        if (cards.isEmpty()) {
            return false;
        }

        controller.revealCards(sourceObject.getName(), cards, game);

        boolean creatureCardFound = cards.getCards(game).stream().anyMatch(card -> card.isCreature(game));
        boolean landCardFound = cards.getCards(game).stream().anyMatch(card -> card.isLand(game));

        if (!creatureCardFound && !landCardFound) {
            controller.moveCards(cards, Zone.GRAVEYARD, source, game);
            return true;
        }

        Cards cardsToHand = new CardsImpl();

        if (creatureCardFound) {
            TargetCard target = new TargetCardInLibrary(0, 1, creatureFilter);
            controller.chooseTarget(Outcome.DrawCard, cards, target, source, game);
            if (target.getFirstTarget() != null) {
                cards.remove(target.getFirstTarget());
                cardsToHand.add(target.getFirstTarget());
            }
        }
        if (landCardFound) {
            TargetCard target = new TargetCardInLibrary(0, 1, landFilter);
            controller.chooseTarget(Outcome.DrawCard, cards, target, source, game);
            if (target.getFirstTarget() != null) {
                cards.remove(target.getFirstTarget());
                cardsToHand.add(target.getFirstTarget());
            }
        }
        controller.moveCards(cardsToHand, Zone.HAND, source, game);
        controller.moveCards(cards, Zone.GRAVEYARD, source, game);
        return true;
    }
}
