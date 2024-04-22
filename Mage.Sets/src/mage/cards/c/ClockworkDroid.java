package mage.cards.c;

import mage.MageInt;
import mage.abilities.common.BecomesExertSourceTriggeredAbility;
import mage.abilities.effects.common.combat.CantBeBlockedSourceEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.abilities.keyword.ExertAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class ClockworkDroid extends CardImpl {

    public ClockworkDroid(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{2}");

        this.subtype.add(SubType.ROBOT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // You may exert Clockwork Droid as it attacks. When you do, it can't be blocked this turn and you scry 1.
        BecomesExertSourceTriggeredAbility trigger = new BecomesExertSourceTriggeredAbility(
                new CantBeBlockedSourceEffect(Duration.EndOfTurn)
        );
        trigger.addEffect(new ScryEffect(1, false).concatBy("and you"));
        this.addAbility(new ExertAbility(trigger));
    }

    private ClockworkDroid(final ClockworkDroid card) {
        super(card);
    }

    @Override
    public ClockworkDroid copy() {
        return new ClockworkDroid(this);
    }
}
