package mage.cards.p;

import java.util.UUID;

import mage.abilities.mana.WhiteManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 * @author TheElk801
 */
public final class PillarvergePathway extends CardImpl {

    public PillarvergePathway(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        this.nightCard = true;
        this.transformable = true;

        // {T}: Add {W}.
        this.addAbility(new WhiteManaAbility());
    }

    private PillarvergePathway(final PillarvergePathway card) {
        super(card);
    }

    @Override
    public PillarvergePathway copy() {
        return new PillarvergePathway(this);
    }
}
