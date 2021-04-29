
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastOpponentTriggeredAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;

/**
 *
 * @author fireshoes
 */
public final class SunscorchRegent extends CardImpl {

    public SunscorchRegent(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{W}{W}");
        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        
        // Whenever an opponent casts a spell, put a +1/+1 counter on Sunscorch Regent and you gain 1 life.
        Ability ability = new SpellCastOpponentTriggeredAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance()), false);
        ability.addEffect(new GainLifeEffect(1).concatBy("and"));
        this.addAbility(ability);
    }

    private SunscorchRegent(final SunscorchRegent card) {
        super(card);
    }

    @Override
    public SunscorchRegent copy() {
        return new SunscorchRegent(this);
    }
}
