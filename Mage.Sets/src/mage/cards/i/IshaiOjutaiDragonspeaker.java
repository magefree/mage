
package mage.cards.i;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SpellCastOpponentTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.PartnerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;

/**
 *
 * @author Styxo
 */
public final class IshaiOjutaiDragonspeaker extends CardImpl {

    public IshaiOjutaiDragonspeaker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.BIRD);
        this.subtype.add(SubType.MONK);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever an opponent cards a spell, put +1/+1 counter on Ishai, Ojutai Dragonspeaker.
        this.addAbility(new SpellCastOpponentTriggeredAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance()), false));

        // Partner
        this.addAbility(PartnerAbility.getInstance());
    }

    private IshaiOjutaiDragonspeaker(final IshaiOjutaiDragonspeaker card) {
        super(card);
    }

    @Override
    public IshaiOjutaiDragonspeaker copy() {
        return new IshaiOjutaiDragonspeaker(this);
    }
}
