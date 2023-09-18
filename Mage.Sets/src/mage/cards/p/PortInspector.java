package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.BecomesBlockedSourceTriggeredAbility;
import mage.abilities.effects.common.LookAtTargetPlayerHandEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author jeffwadsworth
 */
public final class PortInspector extends CardImpl {

    public PortInspector(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");

        this.subtype.add(SubType.HUMAN);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // Whenever Port Inspector becomes blocked, you may look at defending player's hand.
        this.addAbility(new BecomesBlockedSourceTriggeredAbility(
                new LookAtTargetPlayerHandEffect().setText("look at defending player's hand"),
                true, true));
    }

    private PortInspector(final PortInspector card) {
        super(card);
    }

    @Override
    public PortInspector copy() {
        return new PortInspector(this);
    }
}
