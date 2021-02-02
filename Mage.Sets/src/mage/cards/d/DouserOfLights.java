package mage.cards.d;

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
public final class DouserOfLights extends CardImpl {

    public DouserOfLights(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{B}");

        this.subtype.add(SubType.HORROR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(5);
    }

    private DouserOfLights(final DouserOfLights card) {
        super(card);
    }

    @Override
    public DouserOfLights copy() {
        return new DouserOfLights(this);
    }
}
