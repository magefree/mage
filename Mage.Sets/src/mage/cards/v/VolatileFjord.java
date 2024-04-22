package mage.cards.v;

import java.util.UUID;

import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.mana.BlueManaAbility;
import mage.abilities.mana.RedManaAbility;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author weirddan455
 */
public final class VolatileFjord extends CardImpl {

    public VolatileFjord(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        this.supertype.add(SuperType.SNOW);
        this.subtype.add(SubType.ISLAND);
        this.subtype.add(SubType.MOUNTAIN);

        // {tap}: Add {U} or {R}.
        this.addAbility(new BlueManaAbility());
        this.addAbility(new RedManaAbility());

        // Volatile Fjord enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());
    }

    private VolatileFjord(final VolatileFjord card) {
        super(card);
    }

    @Override
    public VolatileFjord copy() {
        return new VolatileFjord(this);
    }
}
