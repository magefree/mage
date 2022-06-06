
package mage.cards.h;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.CountersSourceCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.SunburstAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author Plopman
 */
public final class Heliophial extends CardImpl {

    public Heliophial(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{5}");

        // Sunburst
        this.addAbility(new SunburstAbility(this));
        // {2}, Sacrifice Heliophial: Heliophial deals damage equal to the number of charge counters on it to any target.
        Effect effect = new DamageTargetEffect(new CountersSourceCount(CounterType.CHARGE));
        effect.setText("{this} deals damage equal to the number of charge counters on it to any target");
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, effect, new ManaCostsImpl<>("{2}"));
        ability.addCost(new SacrificeSourceCost());
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);
    }

    private Heliophial(final Heliophial card) {
        super(card);
    }

    @Override
    public Heliophial copy() {
        return new Heliophial(this);
    }
}
