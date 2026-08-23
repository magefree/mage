package mage.cards.w;

import mage.abilities.LoyaltyAbility;
import mage.abilities.common.CanBeYourCommanderAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.PutCards;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.game.permanent.token.LoyaltyCatToken;
import mage.game.permanent.token.ScrybSpritesToken;

import java.util.UUID;

/**
 * @author PurpleCrowbar
 */
public final class WorzelTheProtector extends CardImpl {

    private static final FilterCard filter = new FilterCard("a planeswalker or basic Plains card");

    static {
        filter.add(Predicates.or(
                CardType.PLANESWALKER.getPredicate(),
                Predicates.and(
                        SuperType.BASIC.getPredicate(),
                        SubType.PLAINS.getPredicate()
                )
        ));
    }

    public WorzelTheProtector(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{1}{W}{W}{W}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.WORZEL);

        this.setStartingLoyalty(4);

        // 0: Create a 1/1 white Cat creature token with "{T}: Put a loyalty counter on each planeswalker you control."
        this.addAbility(new LoyaltyAbility(new CreateTokenEffect(new LoyaltyCatToken()), 0));

        // −1: Look at the top six cards of your library. You may reveal a planeswalker or basic Plains card from among them and put it into your hand. Put the rest on the bottom of your library in a random order.
        this.addAbility(new LoyaltyAbility(new LookLibraryAndPickControllerEffect(
                6, 1, filter, PutCards.HAND, PutCards.BOTTOM_RANDOM
        ), -1));

        // −8: Create ten Scryb Sprites tokens.
        this.addAbility(new LoyaltyAbility(new CreateTokenEffect(new ScrybSpritesToken(), 10)
                .setText("Create ten Scryb Sprites tokens. <i>(They're {G} 1/1 Faerie creatures with flying.)</i>"), -8)
        );

        // Worzel, the Protector can be your commander.
        this.addAbility(CanBeYourCommanderAbility.getInstance());
    }

    private WorzelTheProtector(final WorzelTheProtector card) {
        super(card);
    }

    @Override
    public WorzelTheProtector copy() {
        return new WorzelTheProtector(this);
    }
}
