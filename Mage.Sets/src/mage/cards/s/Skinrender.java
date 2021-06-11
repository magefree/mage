

package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.counters.CounterType;
import mage.target.Target;
import mage.target.common.TargetCreaturePermanent;

/**
 * @author nantuko
 */
public final class Skinrender extends CardImpl {

    public Skinrender(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{B}{B}");
        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.ZOMBIE);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        Effect putCountersEffect = new AddCountersTargetEffect(CounterType.M1M1.createInstance(3), Outcome.UnboostCreature);
        Ability ability = new EntersBattlefieldTriggeredAbility(putCountersEffect, false);
        Target target = new TargetCreaturePermanent();
        ability.addTarget(target);
        this.addAbility(ability);
    }

    private Skinrender(final Skinrender card) {
        super(card);
    }

    @Override
    public Skinrender copy() {
        return new Skinrender(this);
    }

}
