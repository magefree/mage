package mage.cards.a;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.ExileTopXMayPlayUntilEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AlaniasPathmaker extends CardImpl {

    public AlaniasPathmaker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");

        this.subtype.add(SubType.OTTER);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(4);
        this.toughness = new MageInt(2);

        // When Alania's Pathmaker enters, exile the top card of your library. Until the end of your next turn, you may play that card.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new ExileTopXMayPlayUntilEffect(1, Duration.UntilEndOfYourNextTurn)));
    }

    private AlaniasPathmaker(final AlaniasPathmaker card) {
        super(card);
    }

    @Override
    public AlaniasPathmaker copy() {
        return new AlaniasPathmaker(this);
    }
}
