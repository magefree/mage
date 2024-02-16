package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.abilities.keyword.ReachAbility;
import mage.cards.Cards;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.PutCards;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BlossomPrancer extends CardImpl {

    public BlossomPrancer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}{G}");

        this.subtype.add(SubType.SPIRIT);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // When Blossom Prancer enters the battlefield, look at the top five cards of your library.
        // You may reveal a creature or enchantment card from among them and put it into your hand.
        // Put the rest on the bottom of your library in a random order.
        // If you didn't put a card into your hand this way, you gain 4 life.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new BlossomPrancerEffect()));
    }

    private BlossomPrancer(final BlossomPrancer card) {
        super(card);
    }

    @Override
    public BlossomPrancer copy() {
        return new BlossomPrancer(this);
    }
}

class BlossomPrancerEffect extends LookLibraryAndPickControllerEffect {

    private static final FilterCard filter = new FilterCard("creature or enchantment card");

    static {
        filter.add(Predicates.or(
                CardType.CREATURE.getPredicate(),
                CardType.ENCHANTMENT.getPredicate()
        ));
    }

    BlossomPrancerEffect() {
        super(5, 1, filter, PutCards.HAND, PutCards.BOTTOM_RANDOM);
    }

    private BlossomPrancerEffect(final BlossomPrancerEffect effect) {
        super(effect);
    }

    @Override
    public BlossomPrancerEffect copy() {
        return new BlossomPrancerEffect(this);
    }

    @Override
    public boolean actionWithPickedCards(Game game, Ability source, Player player, Cards pickedCards, Cards otherCards) {
        super.actionWithPickedCards(game, source, player, pickedCards, otherCards);
        if (pickedCards.isEmpty()) {
            player.gainLife(4, game, source);
        }
        return true;
    }

    @Override
    public String getText(Mode mode) {
        return super.getText(mode).concat(". If you didn't put a card into your hand this way, you gain 4 life");
    }
}
