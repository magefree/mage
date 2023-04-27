package mage.cards.i;

import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.mana.BlueManaAbility;
import mage.abilities.mana.WhiteManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class IdyllicBeachfront extends CardImpl {

    public IdyllicBeachfront(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        this.subtype.add(SubType.PLAINS);
        this.subtype.add(SubType.ISLAND);

        // ({T}: Add {W} or {U}.)
        this.addAbility(new WhiteManaAbility());
        this.addAbility(new BlueManaAbility());

        // Idyllic Beachfront enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());
    }

    private IdyllicBeachfront(final IdyllicBeachfront card) {
        super(card);
    }

    @Override
    public IdyllicBeachfront copy() {
        return new IdyllicBeachfront(this);
    }
}
