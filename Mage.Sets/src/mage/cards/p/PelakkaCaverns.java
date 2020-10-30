package mage.cards.p;

import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.mana.BlackManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PelakkaCaverns extends CardImpl {

    public PelakkaCaverns(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        this.modalDFC = true;
        this.nightCard = true;

        // Pelakka Caverns enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());

        // {T}: Add {B}.
        this.addAbility(new BlackManaAbility());
    }

    private PelakkaCaverns(final PelakkaCaverns card) {
        super(card);
    }

    @Override
    public PelakkaCaverns copy() {
        return new PelakkaCaverns(this);
    }
}
