package mage.cards.e;

import mage.MageInt;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.keyword.ProwessAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ElementalistAdept extends CardImpl {

    public ElementalistAdept(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // Prowess
        this.addAbility(new ProwessAbility());
    }

    private ElementalistAdept(final ElementalistAdept card) {
        super(card);
    }

    @Override
    public ElementalistAdept copy() {
        return new ElementalistAdept(this);
    }
}
