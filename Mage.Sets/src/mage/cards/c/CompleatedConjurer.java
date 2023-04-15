package mage.cards.c;

import mage.MageInt;
import mage.abilities.common.TransformIntoSourceTriggeredAbility;
import mage.abilities.effects.common.ExileTopXMayPlayUntilEndOfTurnEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CompleatedConjurer extends CardImpl {

    public CompleatedConjurer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "");

        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.WEIRD);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);
        this.color.setBlue(true);
        this.color.setRed(true);
        this.nightCard = true;

        // When this creature transforms into Compleated Conjurer, exile the top card of your library. Until the end of your next turn, you may play that card.
        this.addAbility(new TransformIntoSourceTriggeredAbility(
                new ExileTopXMayPlayUntilEndOfTurnEffect(1, false, Duration.UntilEndOfYourNextTurn)
        ));
    }

    private CompleatedConjurer(final CompleatedConjurer card) {
        super(card);
    }

    @Override
    public CompleatedConjurer copy() {
        return new CompleatedConjurer(this);
    }
}
