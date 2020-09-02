package mage.cards.c;

import java.util.UUID;

import mage.abilities.keyword.TransformAbility;
import mage.abilities.mana.BlueManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 * @author TheElk801
 */
public final class ClearwaterPathway extends CardImpl {

    public ClearwaterPathway(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        this.transformable = true;
        this.secondSideCardClazz = mage.cards.m.MurkwaterPathway.class;

        // {T}: Add {U}.
        this.addAbility(new BlueManaAbility());
        this.addAbility(new TransformAbility());
    }

    private ClearwaterPathway(final ClearwaterPathway card) {
        super(card);
    }

    @Override
    public ClearwaterPathway copy() {
        return new ClearwaterPathway(this);
    }
}
