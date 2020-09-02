package mage.cards.b;

import java.util.UUID;

import mage.abilities.keyword.TransformAbility;
import mage.abilities.mana.WhiteManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.g.GrimclimbPathway;
import mage.constants.CardType;

/**
 * @author TheElk801
 */
public final class BrightclimbPathway extends CardImpl {

    public BrightclimbPathway(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        this.secondSideCardClazz = mage.cards.g.GrimclimbPathway.class;

        // {T}: Add {W}.
        this.addAbility(new WhiteManaAbility());
        this.addAbility(new TransformAbility());
    }

    private BrightclimbPathway(final BrightclimbPathway card) {
        super(card);
    }

    @Override
    public BrightclimbPathway copy() {
        return new BrightclimbPathway(this);
    }
}
