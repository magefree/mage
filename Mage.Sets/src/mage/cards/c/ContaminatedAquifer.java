package mage.cards.c;

import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.mana.BlackManaAbility;
import mage.abilities.mana.BlueManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ContaminatedAquifer extends CardImpl {

    public ContaminatedAquifer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        this.subtype.add(SubType.ISLAND);
        this.subtype.add(SubType.SWAMP);

        // ({T}: Add {U} or {B}.)
        this.addAbility(new BlueManaAbility());
        this.addAbility(new BlackManaAbility());

        // Contaminated Aquifer enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());
    }

    private ContaminatedAquifer(final ContaminatedAquifer card) {
        super(card);
    }

    @Override
    public ContaminatedAquifer copy() {
        return new ContaminatedAquifer(this);
    }
}
