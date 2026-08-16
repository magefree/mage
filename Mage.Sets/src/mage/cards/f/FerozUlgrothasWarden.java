package mage.cards.f;

import java.util.UUID;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterCard;
import mage.filter.common.FilterPermanentCard;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.permanent.token.BlueBirdToken;
import mage.game.permanent.token.FerozsBanToken;
import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.CanBeYourCommanderAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.PutCardFromHandOntoBattlefieldEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;

/**
 *
 * @author muz
 */
public final class FerozUlgrothasWarden extends CardImpl {

    private static final FilterCard filter = new FilterPermanentCard("a permanent card with mana value 4 or less");

    static {
        filter.add(new ManaValuePredicate(ComparisonType.OR_LESS, 4));
    }

    public FerozUlgrothasWarden(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{3}{G}{W}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.FEROZ);
        this.setStartingLoyalty(5);

        // +2: Create two 1/1 blue Bird creature tokens with flying.
        this.addAbility(new LoyaltyAbility(new CreateTokenEffect(new BlueBirdToken(), 2), 2));

        // 0: Draw a card. You may put a permanent card with mana value 4 or less from your hand onto the battlefield.
        Ability ability = new LoyaltyAbility(new DrawCardSourceControllerEffect(1), 0);
        ability.addEffect(new PutCardFromHandOntoBattlefieldEffect(filter));
        this.addAbility(ability);

        // −7: Create a Feroz's Ban token.
        this.addAbility(new LoyaltyAbility(new CreateTokenEffect(new FerozsBanToken()), -7));

        // Feroz, Ulgrotha's Warden can be your commander.
        this.addAbility(CanBeYourCommanderAbility.getInstance());
    }

    private FerozUlgrothasWarden(final FerozUlgrothasWarden card) {
        super(card);
    }

    @Override
    public FerozUlgrothasWarden copy() {
        return new FerozUlgrothasWarden(this);
    }
}
