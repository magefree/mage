package mage.cards.f;

import mage.MageInt;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FeralMaaka extends CardImpl {

    public FeralMaaka(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.subtype.add(SubType.CAT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);
    }

    private FeralMaaka(final FeralMaaka card) {
        super(card);
    }

    @Override
    public FeralMaaka copy() {
        return new FeralMaaka(this);
    }
}
