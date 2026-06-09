package mage.cards.b;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.cost.SpellsCostReductionControllerEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.AdventureCard;
import mage.cards.Card;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.common.FilterPermanentCard;
import mage.filter.predicate.mageobject.AdventurePredicate;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class BelunaGrandsquall extends AdventureCard {

    private static final FilterCard filter = new FilterPermanentCard("permanent spells you cast that have an Adventure");

    static {
        filter.add(AdventurePredicate.instance);
    }

    public BelunaGrandsquall(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.CREATURE}, new SubType[]{SubType.GIANT, SubType.NOBLE}, "{G}{U}{R}",
                "Seek Thrills",
                new CardType[]{CardType.INSTANT}, "{2}{G}{U}{R}");

        // Beluna Grandsquall
        this.getLeftHalfCard().setPT(4, 4);

        // Trample
        this.getLeftHalfCard().addAbility(TrampleAbility.getInstance());

        // Permanent spells you cast that have an Adventure cost {1} less to cast.
        this.getLeftHalfCard().addAbility(new SimpleStaticAbility(new SpellsCostReductionControllerEffect(filter, 1)));

        // Seek Thrills
        // Mill seven cards. Then put all cards that have an Adventure from among the milled cards into your hand.
        this.getRightHalfCard().getSpellAbility().addEffect(new SeekThrillsEffect());

        finalizeCard();
    }

    private BelunaGrandsquall(final BelunaGrandsquall card) {
        super(card);
    }

    @Override
    public BelunaGrandsquall copy() {
        return new BelunaGrandsquall(this);
    }
}


class SeekThrillsEffect extends OneShotEffect {

    SeekThrillsEffect() {
        super(Outcome.Benefit);
        staticText = "mill seven cards. Then put all cards that have an Adventure from among the milled cards into your hand.";
    }

    private SeekThrillsEffect(final SeekThrillsEffect effect) {
        super(effect);
    }

    @Override
    public SeekThrillsEffect copy() {
        return new SeekThrillsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }

        Cards cards = player.millCards(7, source, game);
        cards.retainZone(Zone.GRAVEYARD, game);
        for (Card card : cards.getCards(game)) {
            if (!AdventurePredicate.instance.apply(card, game)) {
                cards.remove(card);
            }
        }
        
        player.moveCardsToHandWithInfo(cards, source, game, true);
        return true;
    }

}
