package mage.cards.f;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author TheElk801
 */
public final class FearlessHalberdier extends CardImpl {

    public FearlessHalberdier(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);
    }

    private FearlessHalberdier(final FearlessHalberdier card) {
        super(card);
    }

    @Override
    public FearlessHalberdier copy() {
        return new FearlessHalberdier(this);
    }
}
