package mage.cards.b;

import mage.abilities.mana.WhiteManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BrightclimbPathway extends CardImpl {

    public BrightclimbPathway(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        this.modalDFC = true;
        this.secondSideCardClazz = mage.cards.g.GrimclimbPathway.class;

        // {T}: Add {W}.
        this.addAbility(new WhiteManaAbility());
    }

    private BrightclimbPathway(final BrightclimbPathway card) {
        super(card);
    }

    @Override
    public BrightclimbPathway copy() {
        return new BrightclimbPathway(this);
    }
}
