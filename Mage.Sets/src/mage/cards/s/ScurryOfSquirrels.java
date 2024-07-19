package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.MyriadAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ScurryOfSquirrels extends CardImpl {

    public ScurryOfSquirrels(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.subtype.add(SubType.SQUIRREL);
        this.subtype.add(SubType.SCOUT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Myriad
        this.addAbility(new MyriadAbility());

        // Myriad
        this.addAbility(new MyriadAbility());

        // Whenever Scurry of Squirrels deals combat damage to a player, put a +1/+1 counter on target creature you control.
        Ability ability = new DealsCombatDamageToAPlayerTriggeredAbility(new AddCountersTargetEffect(CounterType.P1P1.createInstance()));
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(ability);
    }

    private ScurryOfSquirrels(final ScurryOfSquirrels card) {
        super(card);
    }

    @Override
    public ScurryOfSquirrels copy() {
        return new ScurryOfSquirrels(this);
    }
}
