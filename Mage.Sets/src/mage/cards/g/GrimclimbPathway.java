package mage.cards.g;

import java.util.UUID;

import mage.abilities.mana.BlackManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 * @author TheElk801
 */
public final class GrimclimbPathway extends CardImpl {

    public GrimclimbPathway(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

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
