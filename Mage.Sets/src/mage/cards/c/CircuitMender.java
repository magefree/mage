package mage.cards.c;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.LeavesBattlefieldTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CircuitMender extends CardImpl {

    public CircuitMender(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{3}");

        this.subtype.add(SubType.INSECT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // When Circuit Mender enters the battlefield, you gain 2 life.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new GainLifeEffect(2)));

        // When Circuit Mender leaves the battlefield, draw a card.
        this.addAbility(new LeavesBattlefieldTriggeredAbility(new DrawCardSourceControllerEffect(1), false));
    }

    private CircuitMender(final CircuitMender card) {
        super(card);
    }

    @Override
    public CircuitMender copy() {
        return new CircuitMender(this);
    }
}
