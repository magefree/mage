package mage.cards.d;

import mage.MageInt;
import mage.abilities.common.ForetellSourceControllerTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.ForetellAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

import java.util.UUID;

/**
 *
 * @author jeffwadsworth
 */
public final class DreamDevourer extends CardImpl {

    public DreamDevourer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");

        this.subtype.add(SubType.DEMON);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(0);
        this.toughness = new MageInt(3);

        // Each nonland card in your hand without foretell has foretell. Its foretell cost is equal to its mana cost reduced by 2.
        this.addAbility(new SimpleStaticAbility(ForetellAbility.makeAddForetellEffect()));

        // Whenever you foretell a card, Dream Devourer gets +2/+0 until end of turn.
        this.addAbility(new ForetellSourceControllerTriggeredAbility(new BoostSourceEffect(2, 0, Duration.EndOfTurn)));

    }

    private DreamDevourer(final DreamDevourer card) {
        super(card);
    }

    @Override
    public DreamDevourer copy() {
        return new DreamDevourer(this);
    }
}
