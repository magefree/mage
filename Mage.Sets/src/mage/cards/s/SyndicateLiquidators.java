package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.abilities.Ability;
import mage.abilities.common.LandfallAbility;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 * @author muz
 */
public final class SyndicateLiquidators extends CardImpl {

    public SyndicateLiquidators(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{G}");

        this.subtype.add(SubType.ORION);
        this.subtype.add(SubType.PIRATE);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Landfall -- Whenever a land you control enters, put two +1/+1 counters on target creature you control.
        Ability ability = new LandfallAbility(new AddCountersTargetEffect(CounterType.P1P1.createInstance(2)));
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(ability);
    }

    private SyndicateLiquidators(final SyndicateLiquidators card) {
        super(card);
    }

    @Override
    public SyndicateLiquidators copy() {
        return new SyndicateLiquidators(this);
    }
}
