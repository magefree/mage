package mage.cards.h;

import java.util.UUID;

import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.mana.GreenManaAbility;
import mage.abilities.mana.RedManaAbility;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author weirddan455
 */
public final class HighlandForest extends CardImpl {

    public HighlandForest(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        this.supertype.add(SuperType.SNOW);
        this.subtype.add(SubType.MOUNTAIN);
        this.subtype.add(SubType.FOREST);

        // {tap}: Add {R} or {G}.
        this.addAbility(new RedManaAbility());
        this.addAbility(new GreenManaAbility());

        // Highland Forest enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());
    }

    private HighlandForest(final HighlandForest card) {
        super(card);
    }

    @Override
    public HighlandForest copy() {
        return new HighlandForest(this);
    }
}
