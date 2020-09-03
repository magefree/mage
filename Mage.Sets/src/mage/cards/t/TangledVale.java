package mage.cards.t;

import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.mana.GreenManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TangledVale extends CardImpl {

    public TangledVale(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        this.modalDFC = true;
        this.nightCard = true;

        // Tangled Vale enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());

        // {T}: Add {G}.
        this.addAbility(new GreenManaAbility());
    }

    private TangledVale(final TangledVale card) {
        super(card);
    }

    @Override
    public TangledVale copy() {
        return new TangledVale(this);
    }
}
