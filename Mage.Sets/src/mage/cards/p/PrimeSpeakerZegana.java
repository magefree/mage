package mage.cards.p;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.common.GreatestAmongPermanentsValue;
import mage.abilities.dynamicvalue.common.SourcePermanentPowerValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;

import java.util.UUID;

/**
 * @author Plopman
 */
public final class PrimeSpeakerZegana extends CardImpl {

    public PrimeSpeakerZegana(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}{G}{U}{U}");
        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.WIZARD);
        this.supertype.add(SuperType.LEGENDARY);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        //Prime Speaker Zegana enters the battlefield with X +1/+1 counters on it, where X is the greatest power among other creatures you control.
        Effect effect = new AddCountersSourceEffect(
                CounterType.P1P1.createInstance(0),
                GreatestAmongPermanentsValue.POWER_OTHER_CONTROLLED_CREATURES,
                true
        );
        effect.setText("with X +1/+1 counters on it, where X is the greatest power among other creatures you control.");
        this.addAbility(new EntersBattlefieldAbility(effect).addHint(GreatestAmongPermanentsValue.POWER_OTHER_CONTROLLED_CREATURES.getHint()));
        //When Prime Speaker Zegana enters the battlefield, draw cards equal to its power.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new DrawCardSourceControllerEffect(SourcePermanentPowerValue.NOT_NEGATIVE)
                .setText("draw cards equal to its power")));
    }

    private PrimeSpeakerZegana(final PrimeSpeakerZegana card) {
        super(card);
    }

    @Override
    public PrimeSpeakerZegana copy() {
        return new PrimeSpeakerZegana(this);
    }
}