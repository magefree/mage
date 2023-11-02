
package mage.cards.s;

import java.util.UUID;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.Predicates;

/**
 * @author nantuko
 */
public final class SpareFromEvil extends CardImpl {

    private static final FilterCreatureCard filterNonHuman = new FilterCreatureCard("non-Human creatures");

    static {
        filterNonHuman.add(Predicates.not(SubType.HUMAN.getPredicate()));
    }

    public SpareFromEvil(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{W}");

        // Creatures you control gain protection from non-Human creatures until end of turn.
        this.getSpellAbility().addEffect(new GainAbilityControlledEffect(new ProtectionAbility(filterNonHuman), Duration.EndOfTurn, StaticFilters.FILTER_PERMANENT_CREATURES, false));
    }

    private SpareFromEvil(final SpareFromEvil card) {
        super(card);
    }

    @Override
    public SpareFromEvil copy() {
        return new SpareFromEvil(this);
    }
}
