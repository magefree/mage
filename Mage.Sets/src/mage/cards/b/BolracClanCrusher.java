package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.RemoveCounterCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.target.common.TargetAnyTarget;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BolracClanCrusher extends CardImpl {

    public BolracClanCrusher(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}{G}");

        this.subtype.add(SubType.OGRE);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // {T}, Remove a +1/+1 counter from a creature you control: Bolrac-Clan Crusher deals 2 damage to any target.
        Ability ability = new SimpleActivatedAbility(new DamageTargetEffect(2), new TapSourceCost());
        ability.addCost(new RemoveCounterCost(new TargetControlledCreaturePermanent(), CounterType.P1P1));
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);
    }

    private BolracClanCrusher(final BolracClanCrusher card) {
        super(card);
    }

    @Override
    public BolracClanCrusher copy() {
        return new BolracClanCrusher(this);
    }
}
