package mage.cards.s;

import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SuperType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SnowCoveredWastes extends CardImpl {

    public SnowCoveredWastes(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");
        this.supertype.add(SuperType.BASIC);
        this.supertype.add(SuperType.SNOW);

        // {T}: Add {C}.
        this.addAbility(new ColorlessManaAbility());
    }

    private SnowCoveredWastes(final SnowCoveredWastes card) {
        super(card);
    }

    @Override
    public SnowCoveredWastes copy() {
        return new SnowCoveredWastes(this);
    }
}
