package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.RemoveVariableCountersSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.dynamicvalue.common.GetXValue;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.ModularAbility;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.target.common.TargetAttackingOrBlockingCreature;

/**
 *
 * @author weirddan455
 */
public final class ArcboundJavelineer extends CardImpl {

    public ArcboundJavelineer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{W}");

        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(0);
        this.toughness = new MageInt(1);

        // {T}, Remove X +1/+1 counters from Arcbound Javelineer: It deals X damage to target attacking or blocking creature.
        Ability ability = new SimpleActivatedAbility(new DamageTargetEffect(GetXValue.instance, "It"), new TapSourceCost());
        ability.addCost(new RemoveVariableCountersSourceCost(CounterType.P1P1));
        ability.addTarget(new TargetAttackingOrBlockingCreature());
        this.addAbility(ability);

        // Modular 1
        this.addAbility(new ModularAbility(this, 1));
    }

    private ArcboundJavelineer(final ArcboundJavelineer card) {
        super(card);
    }

    @Override
    public ArcboundJavelineer copy() {
        return new ArcboundJavelineer(this);
    }
}
