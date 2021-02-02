
package mage.cards.c;

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
public final class CentaurBattlemaster extends CardImpl {

    public CentaurBattlemaster(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{G}{G}");
        this.subtype.add(SubType.CENTAUR);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Heroic - Whenever you cast a spell that targets Centaur Battlemaster, put three +1/+1 counters on Centaur Battlemaster.
        this.addAbility(new HeroicAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance(3))));
    }

    private CentaurBattlemaster(final CentaurBattlemaster card) {
        super(card);
    }

    @Override
    public CentaurBattlemaster copy() {
        return new CentaurBattlemaster(this);
    }
}
