package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetOpponent;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ChaosWand extends CardImpl {

    public ChaosWand(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        // {4}, {T}: Target opponent exiles cards from the top of their library 
        // until they exile an instant or sorcery card. You may cast that card 
        // without paying its mana cost. Then put the exiled cards that weren't 
        // cast this way on the bottom of that library in a random order.
        Ability ability = new SimpleActivatedAbility(new ChaosWandEffect(), new GenericManaCost(4));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
    }

    private ChaosWand(final ChaosWand card) {
        super(card);
    }

    @Override
    public ChaosWand copy() {
        return new ChaosWand(this);
    }
}

class ChaosWandEffect extends OneShotEffect {

    public ChaosWandEffect() {
        super(Outcome.PlayForFree);
        this.staticText = "Target opponent exiles cards from the top of their library "
                + "until they exile an instant or sorcery card. "
                + "You may cast that card without paying its mana cost. "
                + "Then put the exiled cards that weren't cast this way on the "
                + "bottom of that library in a random order.";
    }

    public ChaosWandEffect(final ChaosWandEffect effect) {
        super(effect);
    }

    @Override
    public ChaosWandEffect copy() {
        return new ChaosWandEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player opponent = game.getPlayer(source.getFirstTarget());
        if (controller == null || opponent == null) {
            return false;
        }
        Cards cardsToShuffle = new CardsImpl();
        while (opponent.canRespond() && opponent.getLibrary().hasCards()) {
            Card card = opponent.getLibrary().getFromTop(game);
            if (card == null) {
                break;
            }
            cardsToShuffle.add(card);
            opponent.moveCards(card, Zone.EXILED, source, game);
            if (card.isInstantOrSorcery(game)) {
                CardUtil.castSpellWithAttributesForFree(controller, source, game, card);
                break;
            }
        }
        cardsToShuffle.retainZone(Zone.EXILED, game);
        controller.revealCards(source, cardsToShuffle, game);
        return opponent.putCardsOnBottomOfLibrary(cardsToShuffle, game, source, false);
    }
}
