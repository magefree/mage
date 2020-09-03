package mage.cards.r;

import mage.abilities.mana.RedManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RiverglidePathway extends CardImpl {

    public RiverglidePathway(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        this.modalDFC = true;
        this.secondSideCardClazz = mage.cards.l.LavaglidePathway.class;

        // {T}: Add {U}.
        this.addAbility(new RedManaAbility());
    }

    private RiverglidePathway(final RiverglidePathway card) {
        super(card);
    }

    @Override
    public RiverglidePathway copy() {
        return new RiverglidePathway(this);
    }
}
