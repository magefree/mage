
package mage.cards.u;

import java.util.UUID;
import mage.abilities.condition.common.ManaWasSpentCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.InfoEffect;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ColoredManaSymbol;
import mage.constants.Duration;
import mage.constants.TargetController;
import mage.filter.common.FilterCreaturePermanent;

/**
 *
 * @author jeffwadsworth
 *
 */
public final class UnnervingAssault extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Creatures your opponents control");
    private static final FilterCreaturePermanent filter2 = new FilterCreaturePermanent("creatures you control");

    static {
        filter.add(TargetController.OPPONENT.getControllerPredicate());
        filter2.add(TargetController.YOU.getControllerPredicate());
    }

    public UnnervingAssault(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{U/R}");

        // Creatures your opponents control get -1/-0 until end of turn if {U} was spent to cast Unnerving Assault, and creatures you control get +1/+0 until end of turn if {R} was spent to cast it.
        this.getSpellAbility().addEffect(new ConditionalContinuousEffect(
                new BoostAllEffect(-1, 0, Duration.EndOfTurn, filter, false),
                new ManaWasSpentCondition(ColoredManaSymbol.U), "Creatures your opponents control get -1/-0 until end of turn if {U} was spent to cast this spell,"));
        this.getSpellAbility().addEffect(new ConditionalContinuousEffect(
                new BoostAllEffect(1, 0, Duration.EndOfTurn, filter2, false),
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
