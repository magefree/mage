package mage.cards.f;

import mage.MageInt;
import mage.abilities.common.BecomeDayAsEntersAbility;
import mage.abilities.common.BecomesDayOrNightTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FirmamentSage extends CardImpl {

    public FirmamentSage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // If it's neither day or night, it becomes day as Firmament Sage enters the battlefield.
        this.addAbility(new BecomeDayAsEntersAbility());

        // Whenever day becomes night or night becomes day, draw a card.
        this.addAbility(new BecomesDayOrNightTriggeredAbility(new DrawCardSourceControllerEffect(1)));
    }

    private FirmamentSage(final FirmamentSage card) {
        super(card);
    }

    @Override
    public FirmamentSage copy() {
        return new FirmamentSage(this);
    }
}
