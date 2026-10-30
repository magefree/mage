package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.LandfallAbility;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.AffinityAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AffinityType;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 * @author muz
 */
public final class AmandaGraysonLogicsLink extends CardImpl {

    public AmandaGraysonLogicsLink(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ADVISOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Affinity for Vulcans
        this.addAbility(new AffinityAbility(AffinityType.VULCANS));

        // Landfall -- Whenever a land you control enters, put two +1/+1 counters on target creature you control.
        Ability ability = new LandfallAbility(new AddCountersTargetEffect(CounterType.P1P1.createInstance(2)));
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(ability);
    }

    private AmandaGraysonLogicsLink(final AmandaGraysonLogicsLink card) {
        super(card);
    }

    @Override
    public AmandaGraysonLogicsLink copy() {
        return new AmandaGraysonLogicsLink(this);
    }
}
