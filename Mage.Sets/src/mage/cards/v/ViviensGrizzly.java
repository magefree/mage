package mage.cards.v;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ViviensGrizzly extends CardImpl {

    public ViviensGrizzly(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.subtype.add(SubType.BEAR);
        this.subtype.add(SubType.SPIRIT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // {3}{G}: Look at the top card of your library. If it's a creature or planeswalker card, you may reveal it and put it into your hand. If you don't put the card into your hand, put it on the bottom of your library.
        this.addAbility(new SimpleActivatedAbility(
                new ViviensGrizzlyEffect(), new ManaCostsImpl<>("{3}{G}")
        ));
    }

    private ViviensGrizzly(final ViviensGrizzly card) {
        super(card);
    }

    @Override
    public ViviensGrizzly copy() {
        return new ViviensGrizzly(this);
    }
}

class ViviensGrizzlyEffect extends OneShotEffect {

    ViviensGrizzlyEffect() {
        super(Outcome.Benefit);
        staticText = "Look at the top card of your library. " +
                "If it's a creature or planeswalker card, " +
                "you may reveal it and put it into your hand. " +
                "If you don't put the card into your hand, " +
                "put it on the bottom of your library.";
    }

    private ViviensGrizzlyEffect(final ViviensGrizzlyEffect effect) {
        super(effect);
    }

    @Override
    public ViviensGrizzlyEffect copy() {
        return new ViviensGrizzlyEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Card card = player.getLibrary().getFromTop(game);
        Cards cards = new CardsImpl(card);
        player.lookAtCards(source, null, cards, game);
        if ((!card.isPlaneswalker(game) && !card.isCreature(game))
                || !player.chooseUse(outcome, "Put this card in your hand?", source, game)) {
            player.putCardsOnBottomOfLibrary(cards, game, source, false);
        } else {
            player.revealCards(source, cards, game);
            player.moveCards(card, Zone.HAND, source, game);
        }
        return true;
    }
}