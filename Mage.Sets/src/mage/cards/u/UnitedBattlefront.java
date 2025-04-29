package mage.cards.u;

import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.PutCards;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.filter.predicate.mageobject.PermanentPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class UnitedBattlefront extends CardImpl {

    private static final FilterCard filter = new FilterCard("noncreature, nonland permanent cards with mana value 3 or less");

    static {
        filter.add(Predicates.not(CardType.CREATURE.getPredicate()));
        filter.add(Predicates.not(CardType.LAND.getPredicate()));
        filter.add(PermanentPredicate.instance);
        filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, 4));
    }

    public UnitedBattlefront(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{W}");

        // Look at the top seven cards of your library. Put up to two noncreature, nonland permanent cards with mana value 3 or less from among them onto the battlefield. Put the rest on the bottom of your library in a random order.
        this.getSpellAbility().addEffect(new LookLibraryAndPickControllerEffect(
                7, 2, filter, PutCards.BATTLEFIELD, PutCards.BOTTOM_RANDOM
        ));
    }

    private UnitedBattlefront(final UnitedBattlefront card) {
        super(card);
    }

    @Override
    public UnitedBattlefront copy() {
        return new UnitedBattlefront(this);
    }
}
