package mage.cards.h;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.PutCountersTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class HatchetBully extends CardImpl {

    public HatchetBully(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");
        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // {2}{R}, {tap}, Put a -1/-1 counter on a creature you control: Hatchet Bully deals 2 damage to any target.
        Ability ability = new SimpleActivatedAbility(new DamageTargetEffect(2), new ManaCostsImpl<>("{2}{R}"));
        ability.addCost(new TapSourceCost());
        ability.addCost(new PutCountersTargetCost(CounterType.M1M1.createInstance()));
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);
    }

    private HatchetBully(final HatchetBully card) {
        super(card);
    }

    @Override
    public HatchetBully copy() {
        return new HatchetBully(this);
    }
}
