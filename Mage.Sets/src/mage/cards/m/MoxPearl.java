
package mage.cards.m;

import java.util.UUID;
import mage.abilities.mana.WhiteManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author LevelX2
 */
public final class MoxPearl extends CardImpl {

    public MoxPearl(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{0}");

        // {tap}: Add {W}.
        this.addAbility(new WhiteManaAbility());
    }

    private MoxPearl(final MoxPearl card) {
        super(card);
    }

    @Override
    public MoxPearl copy() {
        return new MoxPearl(this);
    }
}
