package mage.cards.v;

import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.mana.GreenManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class VastwoodThicket extends CardImpl {

    public VastwoodThicket(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        this.modalDFC = true;
        this.nightCard = true;

        // Vastwood Thicket enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());

        // {T}: Add {G}.
        this.addAbility(new GreenManaAbility());
    }

    private VastwoodThicket(final VastwoodThicket card) {
        super(card);
    }

    @Override
    public VastwoodThicket copy() {
        return new VastwoodThicket(this);
    }
}
