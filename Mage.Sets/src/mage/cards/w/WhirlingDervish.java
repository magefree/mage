package mage.cards.w;

import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.condition.common.DealtDamageToAnOpponent;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.ProtectionAbility;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.counters.CounterType;

import java.util.UUID;

/**
 * @author LevelX
 */
public final class WhirlingDervish extends CardImpl {

    public WhirlingDervish(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}{G}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.MONK);

        this.color.setGreen(true);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Protection from black
        this.addAbility(ProtectionAbility.from(ObjectColor.BLACK));

        // At the beginning of each end step, if Whirling Dervish dealt damage to an opponent this turn, put a +1/+1 counter on it.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(
                TargetController.ANY,
                new AddCountersSourceEffect(CounterType.P1P1.createInstance())
                        .setText("put a +1/+1 counter on it"),
                false, DealtDamageToAnOpponent.instance
        ));
    }

    private WhirlingDervish(final WhirlingDervish card) {
        super(card);
    }

    @Override
    public WhirlingDervish copy() {
        return new WhirlingDervish(this);
    }
}
