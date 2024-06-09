package mage.cards.r;

import java.util.UUID;

import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.mana.BlueManaAbility;
import mage.abilities.mana.GreenManaAbility;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author weirddan455
 */
public final class RimewoodFalls extends CardImpl {

    public RimewoodFalls(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        this.supertype.add(SuperType.SNOW);
        this.subtype.add(SubType.FOREST);
        this.subtype.add(SubType.ISLAND);

        // {tap}: Add {G} or {U}.
        this.addAbility(new GreenManaAbility());
        this.addAbility(new BlueManaAbility());

        // Rimewood Falls enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());
    }

    private RimewoodFalls(final RimewoodFalls card) {
        super(card);
    }

    @Override
    public RimewoodFalls copy() {
        return new RimewoodFalls(this);
    }
}
