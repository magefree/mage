package mage.cards.m;

import mage.abilities.mana.BlackManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MurkwaterPathway extends CardImpl {

    public MurkwaterPathway(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        this.modalDFC = true;
        this.nightCard = true;

        // {T}: Add {B}.
        this.addAbility(new BlackManaAbility());
    }

    private MurkwaterPathway(final MurkwaterPathway card) {
        super(card);
    }

    @Override
    public MurkwaterPathway copy() {
        return new MurkwaterPathway(this);
    }
}
