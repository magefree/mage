package mage.cards.r;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardAndOrCardInLibrary;

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
