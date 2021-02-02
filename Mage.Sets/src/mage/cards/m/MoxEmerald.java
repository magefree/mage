
package mage.cards.m;

import java.util.UUID;
import mage.abilities.mana.GreenManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author LevelX2
 */
public final class MoxEmerald extends CardImpl {

    public MoxEmerald(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{0}");

        // {tap}: Add {G}.
        this.addAbility(new GreenManaAbility());
    }

    private MoxEmerald(final MoxEmerald card) {
        super(card);
    }

    @Override
    public MoxEmerald copy() {
        return new MoxEmerald(this);
    }
}
