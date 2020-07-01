package mage.cards.l;

import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LibraryLarcenist extends CardImpl {

    public LibraryLarcenist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");

        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // Whenever Library Larcenist attacks, draw a card.
        this.addAbility(new AttacksTriggeredAbility(new DrawCardSourceControllerEffect(1), false));
    }

    private LibraryLarcenist(final LibraryLarcenist card) {
        super(card);
    }

    @Override
    public LibraryLarcenist copy() {
        return new LibraryLarcenist(this);
    }
}
