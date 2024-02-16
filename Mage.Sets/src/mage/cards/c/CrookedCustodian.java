package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author weirddan455
 */
public final class CrookedCustodian extends CardImpl {

    public CrookedCustodian(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");

        this.subtype.add(SubType.OGRE);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Crooked Custodian enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());
    }

    private CrookedCustodian(final CrookedCustodian card) {
        super(card);
    }

    @Override
    public CrookedCustodian copy() {
        return new CrookedCustodian(this);
    }
}
