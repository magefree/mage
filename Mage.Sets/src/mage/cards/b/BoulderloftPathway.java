package mage.cards.b;

import java.util.UUID;

import mage.abilities.mana.WhiteManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 * @author TheElk801
 */
public final class BoulderloftPathway extends CardImpl {

    public BoulderloftPathway(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        this.nightCard = true;
        this.transformable = true;

        // {T}: Add {W}.
        this.addAbility(new WhiteManaAbility());
    }

    private BoulderloftPathway(final BoulderloftPathway card) {
        super(card);
    }

    @Override
    public BoulderloftPathway copy() {
        return new BoulderloftPathway(this);
    }
}
