package mage.cards.w;

import mage.MageInt;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class WarshipScout extends CardImpl {

    public WarshipScout(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SCOUT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);
    }

    private WarshipScout(final WarshipScout card) {
        super(card);
    }

    @Override
    public WarshipScout copy() {
        return new WarshipScout(this);
    }
}
