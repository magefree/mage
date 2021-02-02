
package mage.cards.m;

import java.util.UUID;
import mage.abilities.mana.RedManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author LevelX2
 */
public final class MoxRuby extends CardImpl {

    public MoxRuby(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{0}");

        // {tap}: Add {R}.
        this.addAbility(new RedManaAbility());
    }

    private MoxRuby(final MoxRuby card) {
        super(card);
    }

    @Override
    public MoxRuby copy() {
        return new MoxRuby(this);
    }
}
