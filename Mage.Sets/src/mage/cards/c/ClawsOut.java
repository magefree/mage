package mage.cards.c;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.cost.SpellCostReductionForEachSourceEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ClawsOut extends CardImpl {

    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(new FilterControlledPermanent(SubType.CAT));
    private static final Hint hint = new ValueHint("Cats you control", xValue);

    public ClawsOut(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{W}{W}");

        // This spell costs {1} less to cast for each Cat you control.
        this.addAbility(new SimpleStaticAbility(
                Zone.ALL, new SpellCostReductionForEachSourceEffect(1, xValue)
        ).addHint(hint));

        // Creatures you control get +2/+2 until end of turn.
        this.getSpellAbility().addEffect(new BoostControlledEffect(2, 2, Duration.EndOfTurn));
    }

    private ClawsOut(final ClawsOut card) {
        super(card);
    }

    @Override
    public ClawsOut copy() {
        return new ClawsOut(this);
    }
}
