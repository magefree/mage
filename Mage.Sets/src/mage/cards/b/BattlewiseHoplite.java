
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.keyword.ScryEffect;
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
public final class BattlewiseHoplite extends CardImpl {

    public BattlewiseHoplite(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{W}{U}");
        this.subtype.add(SubType.HUMAN, SubType.SOLDIER);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Heroic - Whenever you cast a spell that targets Battlewise Hoplite, put a +1/+1 counter on Battlewise Hoplite, then scry 1.
        Ability ability = new HeroicAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance()));
        ability.addEffect(new ScryEffect(1, false));
        this.addAbility(ability);
    }

    private BattlewiseHoplite(final BattlewiseHoplite card) {
        super(card);
    }

    @Override
    public BattlewiseHoplite copy() {
        return new BattlewiseHoplite(this);
    }
}
