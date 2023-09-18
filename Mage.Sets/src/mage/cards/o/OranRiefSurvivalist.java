
package mage.cards.o;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.AllyEntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;

/**
 *
 * @author North
 */
public final class OranRiefSurvivalist extends CardImpl {

    public OranRiefSurvivalist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{G}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.subtype.add(SubType.ALLY);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        this.addAbility(new AllyEntersBattlefieldTriggeredAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance()), true).setAbilityWord(null));
    }

    private OranRiefSurvivalist(final OranRiefSurvivalist card) {
        super(card);
    }

    @Override
    public OranRiefSurvivalist copy() {
        return new OranRiefSurvivalist(this);
    }
}
