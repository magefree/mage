
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.target.common.TargetEnchantmentPermanent;

/**
 *
 * @author Loki
 */
public final class Shinewend extends CardImpl {

    public Shinewend(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{W}");
        this.subtype.add(SubType.ELEMENTAL);

        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Shinewend enters the battlefield with a +1/+1 counter on it.
        this.addAbility(new EntersBattlefieldAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance(1)), "with a +1/+1 counter on it"));

        // {1}{W}, Remove a +1/+1 counter from Shinewend: Destroy target enchantment.
        Ability ability = new SimpleActivatedAbility( new DestroyTargetEffect(), new ManaCostsImpl<>("{1}{W}"));
        ability.addCost(new RemoveCountersSourceCost(CounterType.P1P1.createInstance(1)));
        ability.addTarget(new TargetEnchantmentPermanent());
        this.addAbility(ability);
    }

    private Shinewend(final Shinewend card) {
        super(card);
    }

    @Override
    public Shinewend copy() {
        return new Shinewend(this);
    }
}
