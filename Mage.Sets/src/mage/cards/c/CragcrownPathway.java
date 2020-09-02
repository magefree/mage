package mage.cards.c;

import java.util.UUID;

import mage.abilities.keyword.TransformAbility;
import mage.abilities.mana.RedManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 * @author TheElk801
 */
public final class CragcrownPathway extends CardImpl {

    public CragcrownPathway(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        this.transformable = true;
        this.secondSideCardClazz = mage.cards.t.TimbercrownPathway.class;

        // {T}: Add {R}.
        this.addAbility(new RedManaAbility());
        this.addAbility(new TransformAbility());
    }

    private CragcrownPathway(final CragcrownPathway card) {
        super(card);
    }

    @Override
    public CragcrownPathway copy() {
        return new CragcrownPathway(this);
    }
}
