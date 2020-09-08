package mage.cards.h;

import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.mana.BlackManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HagraBroodpit extends CardImpl {

    public HagraBroodpit(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        this.modalDFC = true;
        this.nightCard = true;

        // Hagra Broodpit enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());

        // {T}: Add {B}.
        this.addAbility(new BlackManaAbility());
    }

    private HagraBroodpit(final HagraBroodpit card) {
        super(card);
    }

    @Override
    public HagraBroodpit copy() {
        return new HagraBroodpit(this);
    }
}
