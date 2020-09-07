package mage.cards.v;

import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.mana.RedManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ValakutStoneforge extends CardImpl {

    public ValakutStoneforge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        this.modalDFC = true;
        this.nightCard = true;

        // Valakut Stoneforge enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());

        // {T}: Add {R}.
        this.addAbility(new RedManaAbility());
    }

    private ValakutStoneforge(final ValakutStoneforge card) {
        super(card);
    }

    @Override
    public ValakutStoneforge copy() {
        return new ValakutStoneforge(this);
    }
}
