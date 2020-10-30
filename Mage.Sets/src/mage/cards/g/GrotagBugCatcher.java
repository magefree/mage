package mage.cards.g;

import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.dynamicvalue.common.PartyCount;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.hint.common.PartyCountHint;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GrotagBugCatcher extends CardImpl {

    public GrotagBugCatcher(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Whenever Grotag Bug-Catcher attacks, it gets +1/+0 until end of turn for each creature in your party.
        this.addAbility(new AttacksTriggeredAbility(new BoostSourceEffect(
                PartyCount.instance, StaticValue.get(0), Duration.EndOfTurn, true
        ).setText("it gets +1/+0 until end of turn for each creature in your party. " + PartyCount.getReminder()), false).addHint(PartyCountHint.instance));
    }

    private GrotagBugCatcher(final GrotagBugCatcher card) {
        super(card);
    }

    @Override
    public GrotagBugCatcher copy() {
        return new GrotagBugCatcher(this);
    }
}
