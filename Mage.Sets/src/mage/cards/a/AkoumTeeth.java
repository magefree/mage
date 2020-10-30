package mage.cards.a;

import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.mana.RedManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AkoumTeeth extends CardImpl {

    public AkoumTeeth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        this.modalDFC = true;
        this.nightCard = true;

        // Akoum Teeth enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());

        // {T}: Add {R}.
        this.addAbility(new RedManaAbility());
    }

    private AkoumTeeth(final AkoumTeeth card) {
        super(card);
    }

    @Override
    public AkoumTeeth copy() {
        return new AkoumTeeth(this);
    }
}
