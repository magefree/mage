package mage.cards.h;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksWithCreaturesTriggeredAbility;
import mage.abilities.condition.common.FormidableCondition;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author muz
 */
public final class HologramJaneway extends CardImpl {

    public HologramJaneway(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{2}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ILLUSION);
        this.subtype.add(SubType.OFFICER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever you attack while creatures you control have total power 8 or greater, put a +1/+1 counter on target creature.
        Ability ability = new AttacksWithCreaturesTriggeredAbility(
            new AddCountersTargetEffect(CounterType.P1P1.createInstance()), 1
        ).withTriggerCondition(FormidableCondition.instance);
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private HologramJaneway(final HologramJaneway card) {
        super(card);
    }

    @Override
    public HologramJaneway copy() {
        return new HologramJaneway(this);
    }
}
