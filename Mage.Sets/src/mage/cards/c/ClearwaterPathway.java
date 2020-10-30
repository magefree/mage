package mage.cards.c;

import mage.abilities.mana.BlueManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ClearwaterPathway extends CardImpl {

    public ClearwaterPathway(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        this.modalDFC = true;
        this.secondSideCardClazz = mage.cards.m.MurkwaterPathway.class;

        // {T}: Add {U}.
        this.addAbility(new BlueManaAbility());
    }

    private ClearwaterPathway(final ClearwaterPathway card) {
        super(card);
    }

    @Override
    public ClearwaterPathway copy() {
        return new ClearwaterPathway(this);
    }
}
