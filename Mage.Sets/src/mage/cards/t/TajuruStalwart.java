
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.dynamicvalue.common.ColorsOfManaSpentToCastCount;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;

/**
 *
 * @author LevelX2
 */
public final class TajuruStalwart extends CardImpl {

    public TajuruStalwart(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{G}");
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.SCOUT);
        this.subtype.add(SubType.ALLY);
        this.power = new MageInt(0);
        this.toughness = new MageInt(1);

        // <i>Converge</i> &mdash; Tajuru Stalwart enters the battlefield with a +1/+1 counter on it for each color of mana spent to cast it.
        this.addAbility(new EntersBattlefieldAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance(), ColorsOfManaSpentToCastCount.getInstance(), true),
                null, "<i>Converge</i> &mdash; {this} enters the battlefield with a +1/+1 counter on it for each color of mana spent to cast it.", null));

    }

    private TajuruStalwart(final TajuruStalwart card) {
        super(card);
    }

    @Override
    public TajuruStalwart copy() {
        return new TajuruStalwart(this);
    }
}
