package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 * @author muz
 */
public final class SickbayOrderly extends CardImpl {

    public SickbayOrderly(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");
        
        this.subtype.add(SubType.CAITIAN);
        this.subtype.add(SubType.DOCTOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());
    }

    private SickbayOrderly(final SickbayOrderly card) {
        super(card);
    }

    @Override
    public SickbayOrderly copy() {
        return new SickbayOrderly(this);
    }
}
