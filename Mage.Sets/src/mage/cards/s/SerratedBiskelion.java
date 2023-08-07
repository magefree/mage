
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class SerratedBiskelion extends CardImpl {

    public SerratedBiskelion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{3}");
        this.subtype.add(SubType.CONSTRUCT);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {tap}: Put a -1/-1 counter on Serrated Biskelion and a -1/-1 counter on target creature.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new AddCountersSourceEffect(CounterType.M1M1.createInstance(), true), new TapSourceCost());
        ability.addEffect(new AddCountersTargetEffect(CounterType.M1M1.createInstance()).setText("and a -1/-1 counter on target creature"));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private SerratedBiskelion(final SerratedBiskelion card) {
        super(card);
    }

    @Override
    public SerratedBiskelion copy() {
        return new SerratedBiskelion(this);
    }
}
