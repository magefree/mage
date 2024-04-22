

package mage.cards.n;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.InfectAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Loki
 */
public final class Necropede extends CardImpl {

    public Necropede (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{2}");
        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.INSECT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);
        this.addAbility(InfectAbility.getInstance());
        Ability ability = new DiesSourceTriggeredAbility(new AddCountersTargetEffect(CounterType.M1M1.createInstance()), true);
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private Necropede(final Necropede card) {
        super(card);
    }

    @Override
    public Necropede copy() {
        return new Necropede(this);
    }

}
