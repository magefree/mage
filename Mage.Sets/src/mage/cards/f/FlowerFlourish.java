package mage.cards.f;

import java.util.UUID;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.cards.CardSetInfo;
import mage.cards.SplitCard;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SpellAbilityType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.mageobject.SupertypePredicate;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author TheElk801
 */
public final class FlowerFlourish extends SplitCard {

    private static final FilterCard filter
            = new FilterCard("basic Forest or Plains card");

    static {
        filter.add(new SupertypePredicate(SuperType.BASIC));
        filter.add(Predicates.or(
                new SubtypePredicate(SubType.FOREST),
                new SubtypePredicate(SubType.PLAINS)
        ));
    }

    public FlowerFlourish(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{G/W}", "{4}{G}{W}", SpellAbilityType.SPLIT);

        // Flower
        // Search your library for a basic Forest or Plains card, reveal it, put it into your hand, then shuffle your library.
        this.getLeftHalfCard().getSpellAbility().addEffect(
                new SearchLibraryPutInHandEffect(
                        new TargetCardInLibrary(filter), true, true
                )
        );

        // Flourish
        // Creatures you control get +2/+2 until end of turn.
        this.getRightHalfCard().getSpellAbility().addEffect(
                new BoostControlledEffect(2, 2, Duration.EndOfTurn)
        );
    }

    public FlowerFlourish(final FlowerFlourish card) {
        super(card);
    }

    @Override
    public FlowerFlourish copy() {
        return new FlowerFlourish(this);
    }
}
