package mage.cards.n;

import mage.abilities.mana.RedManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class NeedlevergePathway extends CardImpl {

    public NeedlevergePathway(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        this.modalDFC = true;
        this.secondSideCardClazz = mage.cards.p.PillarvergePathway.class;

        // {T}: Add {R}.
        this.addAbility(new RedManaAbility());
    }

    private NeedlevergePathway(final NeedlevergePathway card) {
        super(card);
    }

    @Override
    public NeedlevergePathway copy() {
        return new NeedlevergePathway(this);
    }
}
