package mage.cards.u;

import java.util.UUID;
import mage.abilities.condition.common.ManaWasSpentCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.InfoEffect;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ColoredManaSymbol;
import mage.constants.Duration;
import mage.filter.StaticFilters;

/**
 *
 * @author jeffwadsworth
 *
 */
public final class UnnervingAssault extends CardImpl {

    public UnnervingAssault(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{U/R}");

        // Creatures your opponents control get -1/-0 until end of turn if {U} was spent to cast Unnerving Assault, and creatures you control get +1/+0 until end of turn if {R} was spent to cast it.
        this.getSpellAbility().addEffect(new ConditionalContinuousEffect(
                new BoostAllEffect(-1, 0, Duration.EndOfTurn, StaticFilters.FILTER_OPPONENTS_PERMANENT_CREATURES, false),
                new ManaWasSpentCondition(ColoredManaSymbol.U), "Creatures your opponents control get -1/-0 until end of turn if {U} was spent to cast this spell,"));
        this.getSpellAbility().addEffect(new ConditionalContinuousEffect(
                new BoostControlledEffect(1, 0, Duration.EndOfTurn),
                new ManaWasSpentCondition(ColoredManaSymbol.R), " and creatures you control get +1/+0 until end of turn if {R} was spent to cast this spell"));
        this.getSpellAbility().addEffect(new InfoEffect("<i>(Do both if {U}{R} was spent.)</i>"));

    }

    private UnnervingAssault(final UnnervingAssault card) {
        super(card);
    }

    @Override
    public UnnervingAssault copy() {
        return new UnnervingAssault(this);
    }
}
