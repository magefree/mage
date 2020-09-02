package mage.cards.n;

import java.util.UUID;

import mage.abilities.keyword.TransformAbility;
import mage.abilities.mana.RedManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 * @author TheElk801
 */
public final class NeedlevergePathway extends CardImpl {

    public NeedlevergePathway(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        this.transformable = true;
        this.secondSideCardClazz = mage.cards.p.PillarvergePathway.class;

        // {T}: Add {R}.
        this.addAbility(new RedManaAbility());
        this.addAbility(new TransformAbility());
    }

    private NeedlevergePathway(final NeedlevergePathway card) {
        super(card);
    }

    @Override
    public NeedlevergePathway copy() {
        return new NeedlevergePathway(this);
    }
}
