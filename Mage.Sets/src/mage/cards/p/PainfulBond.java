package mage.cards.p;

import mage.abilities.Ability;
import mage.abilities.effects.common.*;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.filter.FilterCard;
import mage.filter.common.FilterNonlandCard;
import mage.filter.predicate.mageobject.ManaValuePredicate;

import java.util.UUID;

public final class PainfulBond extends CardImpl {

    private static final FilterCard filter = new FilterNonlandCard(" then nonland cards in your hand with mana value 3 or less");

    static {
        filter.add(new ManaValuePredicate(ComparisonType.OR_LESS, 3));
    }

    public PainfulBond(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{B}");

        // Draw two cards, then nonland cards in your hand with mana value 3 or less perpetually gain “When you cast this spell, you lose 1 life.”
        getSpellAbility().addEffect(new DrawCardSourceControllerEffect(2)
                .setText("Draw two cards,"));
        Ability loseLifeAbility = new CastSourceTriggeredAbility(new LoseLifeSourceControllerEffect(1), false);
        getSpellAbility().addEffect(new CardsInYourHandPerpetuallyGainEffect(loseLifeAbility, filter));
    }

    private PainfulBond(final PainfulBond card) {
        super(card);
    }

    @Override
    public PainfulBond copy() {
        return new PainfulBond(this);
    }
}