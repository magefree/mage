package mage.cards.a;

import java.util.UUID;

import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.mana.GreenManaAbility;
import mage.abilities.mana.WhiteManaAbility;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author weirddan455
 */
public final class ArcticTreeline extends CardImpl {

    public ArcticTreeline(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        this.supertype.add(SuperType.SNOW);
        this.subtype.add(SubType.FOREST);
        this.subtype.add(SubType.PLAINS);

        // {tap}: Add {G} or {W}.
        this.addAbility(new GreenManaAbility());
        this.addAbility(new WhiteManaAbility());

        // Arctic Treeline enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());
    }

    private ArcticTreeline(final ArcticTreeline card) {
        super(card);
    }

    @Override
    public ArcticTreeline copy() {
        return new ArcticTreeline(this);
    }
}
