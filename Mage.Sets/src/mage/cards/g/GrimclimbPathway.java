package mage.cards.g;

import mage.abilities.mana.BlackManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GrimclimbPathway extends CardImpl {

    public GrimclimbPathway(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        this.modalDFC = true;
        this.nightCard = true;

        // {T}: Add {B}.
        this.addAbility(new BlackManaAbility());
    }

    private GrimclimbPathway(final GrimclimbPathway card) {
        super(card);
    }

    @Override
    public GrimclimbPathway copy() {
        return new GrimclimbPathway(this);
    }
}
