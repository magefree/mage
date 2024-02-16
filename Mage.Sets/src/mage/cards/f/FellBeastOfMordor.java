package mage.cards.f;

import mage.MageInt;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.EntersBattlefieldOrAttacksSourceTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CountersSourceCount;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.abilities.keyword.DevourAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class FellBeastOfMordor extends CardImpl {

    private static final DynamicValue xValue = new CountersSourceCount(CounterType.P1P1);

    public FellBeastOfMordor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}{B}");

        this.subtype.add(SubType.DRAKE);
        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Devour 1
        this.addAbility(new DevourAbility(1));

        // Whenever Fell Beast of Mordor enters the battlefield or attacks, target opponent loses X life and you gain X life, where X is the number of +1/+1 counters on it.
        TriggeredAbility trigger = new EntersBattlefieldOrAttacksSourceTriggeredAbility(
                new LoseLifeTargetEffect(xValue)
                        .setText("target opponent loses X life")
        );
        trigger.addEffect(new GainLifeEffect(xValue)
                .setText(" and you gain X life, where X is the number of +1/+1 counters on it"));
        trigger.addTarget(new TargetOpponent());
        this.addAbility(trigger);
    }

    private FellBeastOfMordor(final FellBeastOfMordor card) {
        super(card);
    }

    @Override
    public FellBeastOfMordor copy() {
        return new FellBeastOfMordor(this);
    }
}
