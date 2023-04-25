package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.ProliferateEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ContagiousVorrac extends CardImpl {

    public ContagiousVorrac(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.BOAR);
        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // When Contagious Vorrac enters the battlefield, look at the top four cards of your library. You may reveal a land card from among them and put it into your hand. Put the rest on the bottom of your library in a random order. If you didn't put a card into your hand this way, proliferate.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new ContagiousVorracEffect()));
    }

    private ContagiousVorrac(final ContagiousVorrac card) {
        super(card);
    }

    @Override
    public ContagiousVorrac copy() {
        return new ContagiousVorrac(this);
    }
}

class ContagiousVorracEffect extends OneShotEffect {

    ContagiousVorracEffect() {
        super(Outcome.Benefit);
        staticText = "look at the top four cards of your library. You may reveal a land card from among them " +
                "and put it into your hand. Put the rest on the bottom of your library in a random order. " +
                "If you didn't put a card into your hand this way, proliferate. " +
                "<i>(Choose any number of permanents and/or players, then give each another counter of each kind already there.)</i>";
    }

    private ContagiousVorracEffect(final ContagiousVorracEffect effect) {
        super(effect);
    }

    @Override
    public ContagiousVorracEffect copy() {
        return new ContagiousVorracEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Cards cards = new CardsImpl(player.getLibrary().getTopCards(game, 4));
        TargetCard target = new TargetCardInLibrary(0, 1, StaticFilters.FILTER_CARD_LAND);
        player.choose(Outcome.DrawCard, cards, target, source, game);
        Card card = game.getCard(target.getFirstTarget());
        if (card != null) {
            player.revealCards(source, new CardsImpl(card), game);
            player.moveCards(card, Zone.HAND, source, game);
        }
        cards.retainZone(Zone.LIBRARY, game);
        player.putCardsOnBottomOfLibrary(cards, game, source, false);
        return card != null || new ProliferateEffect().apply(game, source);
    }
}
