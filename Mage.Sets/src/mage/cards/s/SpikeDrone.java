package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldWithCountersAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 *
 * @author Loki
 */
public final class SpikeDrone extends CardImpl {

    public SpikeDrone(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}");
        this.subtype.add(SubType.SPIKE);
        this.subtype.add(SubType.DRONE);

        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        this.addAbility(new EntersBattlefieldWithCountersAbility(CounterType.P1P1.createInstance(1)));

        Ability ability = new SimpleActivatedAbility(new AddCountersTargetEffect(CounterType.P1P1.createInstance(1)), new GenericManaCost(2));
        ability.addCost(new RemoveCountersSourceCost(CounterType.P1P1.createInstance(1)));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private SpikeDrone(final SpikeDrone card) {
        super(card);
    }

    @Override
    public SpikeDrone copy() {
        return new SpikeDrone(this);
    }
}
