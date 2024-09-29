package mage.cards.b;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.DrawDiscardControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BellowingCrier extends CardImpl {

    public BellowingCrier(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");

        this.subtype.add(SubType.FROG);
        this.subtype.add(SubType.ADVISOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // When Bellowing Crier enters, draw a card, then discard a card.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new DrawDiscardControllerEffect(1, 1)));
    }

    private BellowingCrier(final BellowingCrier card) {
        super(card);
    }

    @Override
    public BellowingCrier copy() {
        return new BellowingCrier(this);
    }
}
