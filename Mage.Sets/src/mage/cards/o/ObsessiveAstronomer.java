package mage.cards.o;

import mage.MageInt;
import mage.abilities.common.BecomeDayAsEntersAbility;
import mage.abilities.common.BecomesDayOrNightTriggeredAbility;
import mage.abilities.effects.common.discard.DiscardAndDrawThatManyEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ObsessiveAstronomer extends CardImpl {

    public ObsessiveAstronomer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // If it's neither day nor night, it becomes day as Obsessive Astronomer enters the battlefield.
        this.addAbility(new BecomeDayAsEntersAbility());

        // Whenever day becomes night or night becomes day, discard up to two cards, then draw that many cards.
        this.addAbility(new BecomesDayOrNightTriggeredAbility(new DiscardAndDrawThatManyEffect(2)));
    }

    private ObsessiveAstronomer(final ObsessiveAstronomer card) {
        super(card);
    }

    @Override
    public ObsessiveAstronomer copy() {
        return new ObsessiveAstronomer(this);
    }
}
