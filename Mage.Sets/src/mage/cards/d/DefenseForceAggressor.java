package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 * @author muz
 */
public final class DefenseForceAggressor extends CardImpl {

    public DefenseForceAggressor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}");
        
        this.subtype.add(SubType.KLINGON);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);
    }

    private DefenseForceAggressor(final DefenseForceAggressor card) {
        super(card);
    }

    @Override
    public DefenseForceAggressor copy() {
        return new DefenseForceAggressor(this);
    }
}
