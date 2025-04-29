package mage.cards.s;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.GetXValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.CounterUnlessPaysEffect;
import mage.abilities.effects.common.cost.SpellCostReductionForEachSourceEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.PowerPredicate;
import mage.target.TargetSpell;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SpectralDenial extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledCreaturePermanent("creature you control with power 4 or greater");

    static {
        filter.add(new PowerPredicate(ComparisonType.MORE_THAN, 3));
    }

    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(filter, 1);
    private static final Hint hint = new ValueHint("Creatures you control with power 4 or greater", xValue);

    public SpectralDenial(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{X}{U}");

        // This spell costs {1} less to cast for each creature you control with power 4 or greater.
        this.addAbility(new SimpleStaticAbility(
                Zone.ALL, new SpellCostReductionForEachSourceEffect(1, xValue)
        ).addHint(hint));

        // Counter target spell unless its controller pays {X}.
        this.getSpellAbility().addEffect(new CounterUnlessPaysEffect(GetXValue.instance));
        this.getSpellAbility().addTarget(new TargetSpell());
    }

    private SpectralDenial(final SpectralDenial card) {
        super(card);
    }

    @Override
    public SpectralDenial copy() {
        return new SpectralDenial(this);
    }
}
