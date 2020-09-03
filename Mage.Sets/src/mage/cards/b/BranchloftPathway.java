package mage.cards.b;

import mage.abilities.mana.GreenManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BranchloftPathway extends CardImpl {

    public BranchloftPathway(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        this.modalDFC = true;
        this.secondSideCardClazz = mage.cards.b.BoulderloftPathway.class;

        // {T}: Add {G}.
        this.addAbility(new GreenManaAbility());
    }

    private BranchloftPathway(final BranchloftPathway card) {
        super(card);
    }

    @Override
    public BranchloftPathway copy() {
        return new BranchloftPathway(this);
    }
}
