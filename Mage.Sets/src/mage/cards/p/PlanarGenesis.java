package mage.cards.p;

import mage.abilities.Ability;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.PutCards;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class PlanarGenesis extends CardImpl {

    public PlanarGenesis(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{G}{U}");

        // Look at the top four cards of your library. You may put a land card from among them onto the battlefield tapped. If you don't, put a card from among them into your hand. Put the rest on the bottom of your library in a random order.
        this.getSpellAbility().addEffect(new PlanarGenesisEffect());
    }

    private PlanarGenesis(final PlanarGenesis card) {
        super(card);
    }

    @Override
    public PlanarGenesis copy() {
        return new PlanarGenesis(this);
    }
}

class PlanarGenesisEffect extends LookLibraryAndPickControllerEffect {

    PlanarGenesisEffect() {
        super(4, 1, StaticFilters.FILTER_CARD_LAND, PutCards.BATTLEFIELD_TAPPED, PutCards.BOTTOM_RANDOM, true);
        staticText = "Look at the top four cards of your library. You may put a land card from among them onto the battlefield tapped. "
                + "If you don't, put a card from among them into your hand. Put the rest on the bottom of your library in a random order.";
    }

    private PlanarGenesisEffect(final PlanarGenesisEffect effect) {
        super(effect);
    }

    @Override
    public PlanarGenesisEffect copy() {
        return new PlanarGenesisEffect(this);
    }

    @Override
    protected boolean actionWithPickedCards(Game game, Ability source, Player player, Cards pickedCards, Cards otherCards) {
        boolean result = putPickedCards.moveCards(player, pickedCards, source, game);
        if (pickedCards.isEmpty()) {
            if (otherCards.size() >= 1) {
                TargetCard target = new TargetCard(0, 1, Zone.LIBRARY, filter);
                target.withChooseHint("to put " + PutCards.HAND.getMessage(false, false));
                if (player.chooseTarget(PutCards.HAND.getOutcome(), otherCards, target, source, game)) {
                    Cards toPutInHand = new CardsImpl(target.getTargets());
                    result |= PutCards.HAND.moveCards(player, toPutInHand, source, game);
                }
            }
        }
        otherCards.retainZone(Zone.LIBRARY, game);
        result |= putLookedCards.moveCards(player, otherCards, source, game);
        return result;
    }
}