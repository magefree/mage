package mage.cards.u;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.target.common.TargetAnyTarget;

/**
 * @author TheElk801
 */
public final class UrabrasksAnointer extends CardImpl {

    private static final FilterPermanent filter
            = new FilterControlledPermanent("permanents you control with oil counters on them");

    static {
        filter.add(CounterType.OIL.getPredicate());
    }

    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(filter, null);
    private static final Hint hint = new ValueHint("Permanents you control with oil counters on them", xValue);

    public UrabrasksAnointer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{3}{R}");

        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(4);
        this.toughness = new MageInt(2);

        // When Urabrask's Anointer enters the battlefield, it deals X damage to any target, where X is the number of permanents you control with oil counters on them.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DamageTargetEffect(xValue, "it"));
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability.addHint(hint));
    }

    private UrabrasksAnointer(final UrabrasksAnointer card) {
        super(card);
    }

    @Override
    public UrabrasksAnointer copy() {
        return new UrabrasksAnointer(this);
    }
}
