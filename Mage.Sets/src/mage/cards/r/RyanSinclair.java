package mage.cards.r;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.DoctorsCompanionAbility;
import mage.cards.*;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.Optional;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RyanSinclair extends CardImpl {

    public RyanSinclair(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever Ryan attacks, exile cards from the top of your library until you exile a nonland card. You may cast the exiled card without paying its mana cost if it's a spell with mana value less than or equal to Ryan's power. Put the exiled cards not cast this way on the bottom of your library in a random order.
        this.addAbility(new AttacksTriggeredAbility(new RyanSinclairEffect()));

        // Doctor's companion
        this.addAbility(DoctorsCompanionAbility.getInstance());
    }

    private RyanSinclair(final RyanSinclair card) {
        super(card);
    }

    @Override
    public RyanSinclair copy() {
        return new RyanSinclair(this);
    }
}

class RyanSinclairEffect extends OneShotEffect {

    RyanSinclairEffect() {
        super(Outcome.Benefit);
        staticText = "exile cards from the top of your library until you exile a nonland card. " +
                "You may cast the exiled card without paying its mana cost if it's a spell " +
                "with mana value less than or equal to {this}'s power. Put the exiled cards " +
                "not cast this way on the bottom of your library in a random order";
    }

    private RyanSinclairEffect(final RyanSinclairEffect effect) {
        super(effect);
    }

    @Override
    public RyanSinclairEffect copy() {
        return new RyanSinclairEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Cards cards = new CardsImpl();
        Card card = getCard(player, cards, source, game);
        if (card != null) {
            FilterCard filter = new FilterCard();
            filter.add(new ManaValuePredicate(
                    ComparisonType.OR_LESS,
                    Optional.ofNullable(source.getSourcePermanentOrLKI(game))
                            .map(MageObject::getPower)
                            .map(MageInt::getValue)
                            .orElse(-1)
            ));
            CardUtil.castSpellWithAttributesForFree(player, source, game, card, filter);
        }
        cards.retainZone(Zone.EXILED, game);
        player.putCardsOnBottomOfLibrary(cards, game, source, false);
        return true;
    }

    private static Card getCard(Player player, Cards cards, Ability source, Game game) {
        for (Card card : player.getLibrary().getCards(game)) {
            cards.add(card);
            player.moveCards(card, Zone.EXILED, source, game);
            if (!card.isLand(game)) {
                return card;
            }
        }
        return null;
    }
}
