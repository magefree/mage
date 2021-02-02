package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.condition.common.SourceAttackingCondition;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.decorator.ConditionalActivatedAbility;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.SpaceflightAbility;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author NinthWorld
 */
public final class ResistanceBomber extends CardImpl {

    public ResistanceBomber(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{3}{R}");
        
        this.subtype.add(SubType.REBEL);
        this.subtype.add(SubType.STARSHIP);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Spaceflight
        this.addAbility(SpaceflightAbility.getInstance());

        // Resistance Bomber enters the battlefield with a charge counter on it.
        this.addAbility(new EntersBattlefieldAbility(
                new AddCountersSourceEffect(CounterType.CHARGE.createInstance())
                    .setText("with a charge counter on it")));

        // Remove a charge counter from Resistance Bomber: Resistance Bomber deals 5 damage to target creature. Activate this ability only if Resistance Bomber is attacking.
        Ability ability = new ConditionalActivatedAbility(Zone.BATTLEFIELD,
                new DamageTargetEffect(5),
                new RemoveCountersSourceCost(CounterType.CHARGE.createInstance()),
                SourceAttackingCondition.instance);
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private ResistanceBomber(final ResistanceBomber card) {
        super(card);
    }

    @Override
    public ResistanceBomber copy() {
        return new ResistanceBomber(this);
    }
}
