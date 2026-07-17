package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.BecomesTappedSourceTriggeredAbility;
import mage.abilities.effects.common.DrawDiscardControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SilvergillPeddler extends CardImpl {

    public SilvergillPeddler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");

        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.CITIZEN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Whenever this creature becomes tapped, draw a card, then discard a card.
        this.addAbility(new BecomesTappedSourceTriggeredAbility(new DrawDiscardControllerEffect()));
    }

    private SilvergillPeddler(final SilvergillPeddler card) {
        super(card);
    }

    @Override
    public SilvergillPeddler copy() {
        return new SilvergillPeddler(this);
    }
}
