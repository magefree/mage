package mage.cards.i;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardAndOrCardInLibrary;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class InThePresenceOfAges extends CardImpl {

    public InThePresenceOfAges(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{G}");

        // Reveal the top four cards of your library. You may put a creature card and/or a land card from among them into your hand. Put the rest into your graveyard.
        this.getSpellAbility().addEffect(new InThePresenceOfAgesEffect());
    }

    private InThePresenceOfAges(final InThePresenceOfAges card) {
        super(card);
    }

    @Override
    public InThePresenceOfAges copy() {
        return new InThePresenceOfAges(this);
    }
}

class InThePresenceOfAgesEffect extends OneShotEffect {

    InThePresenceOfAgesEffect() {
        super(Outcome.DrawCard);
        this.staticText = "Reveal the top four cards of your library. "
                + "You may put a creature card and/or a land card from among them into your hand. "
                + "Put the rest into your graveyard";
    }

    private InThePresenceOfAgesEffect(final InThePresenceOfAgesEffect effect) {
        super(effect);
    }

    @Override
    public InThePresenceOfAgesEffect copy() {
        return new InThePresenceOfAgesEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Cards cards = new CardsImpl(player.getLibrary().getTopCards(game, 4));
        player.revealCards(source, cards, game);
        TargetCard target = new TargetCardAndOrCardInLibrary(CardType.CREATURE, CardType.LAND);
        player.choose(outcome, cards, target, source, game);
        Cards toHand = new CardsImpl();
        toHand.addAll(target.getTargets());
        player.moveCards(toHand, Zone.HAND, source, game);
        cards.removeAll(toHand);
        player.moveCards(cards, Zone.GRAVEYARD, source, game);
        return true;
    }
}
