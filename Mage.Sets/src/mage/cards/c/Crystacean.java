package mage.cards.c;

import mage.MageInt;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Crystacean extends CardImpl {

    public Crystacean(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}");

        this.subtype.add(SubType.CRAB);
        this.power = new MageInt(1);
        this.toughness = new MageInt(6);

        // Flash
        this.addAbility(FlashAbility.getInstance());
    }

    private Crystacean(final Crystacean card) {
        super(card);
    }

    @Override
    public Crystacean copy() {
        return new Crystacean(this);
    }
}
