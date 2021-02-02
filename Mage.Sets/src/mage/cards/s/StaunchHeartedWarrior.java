
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.HeroicAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;

/**
 *
 * @author LevelX2
 */
public final class StaunchHeartedWarrior extends CardImpl {

    public StaunchHeartedWarrior(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{G}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Heroic - Whenever you cast a spell that targets Staunch-Hearted Warrior, put two +1/+1 counters on Staunch-Hearted Warrior.
        this.addAbility(new HeroicAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance(2))));
    }

    private StaunchHeartedWarrior(final StaunchHeartedWarrior card) {
        super(card);
    }

    @Override
    public StaunchHeartedWarrior copy() {
        return new StaunchHeartedWarrior(this);
    }
}
