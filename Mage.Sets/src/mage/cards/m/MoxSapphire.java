
package mage.cards.m;

import java.util.UUID;
import mage.abilities.mana.BlueManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author LevelX2
 */
public final class MoxSapphire extends CardImpl {

    public MoxSapphire(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{0}");

        // {tap}: Add {U}.
        this.addAbility(new BlueManaAbility());
    }

    private MoxSapphire(final MoxSapphire card) {
        super(card);
    }

    @Override
    public MoxSapphire copy() {
        return new MoxSapphire(this);
    }
}
