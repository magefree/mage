
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.FadingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author LoneFox
 */
public final class AncientHydra extends CardImpl {

    public AncientHydra(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{R}");
        this.subtype.add(SubType.HYDRA);
        this.power = new MageInt(5);
        this.toughness = new MageInt(1);

        // Fading 5
        this.addAbility(new FadingAbility(5, this));
        // {1}, Remove a fade counter from Ancient Hydra: Ancient Hydra deals 1 damage to any target.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DamageTargetEffect(1), new ManaCostsImpl<>("{1}"));
        ability.addCost(new RemoveCountersSourceCost(CounterType.FADE.createInstance(1)));
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);
    }

    private AncientHydra(final AncientHydra card) {
        super(card);
    }

    @Override
    public AncientHydra copy() {
        return new AncientHydra(this);
    }
}
