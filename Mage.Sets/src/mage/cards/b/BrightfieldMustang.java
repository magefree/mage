package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksWhileSaddledTriggeredAbility;
import mage.abilities.effects.common.UntapSourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.SaddleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BrightfieldMustang extends CardImpl {

    public BrightfieldMustang(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");

        this.subtype.add(SubType.HORSE);
        this.subtype.add(SubType.MOUNT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever this creature attacks while saddled, untap it and put a +1/+1 counter on it.
        Ability ability = new AttacksWhileSaddledTriggeredAbility(new UntapSourceEffect().setText("untap it"));
        ability.addEffect(new AddCountersSourceEffect(CounterType.P1P1.createInstance()).setText("and put a +1/+1 counter on it"));
        this.addAbility(ability);

        // Saddle 1
        this.addAbility(new SaddleAbility(1));
    }

    private BrightfieldMustang(final BrightfieldMustang card) {
        super(card);
    }

    @Override
    public BrightfieldMustang copy() {
        return new BrightfieldMustang(this);
    }
}
