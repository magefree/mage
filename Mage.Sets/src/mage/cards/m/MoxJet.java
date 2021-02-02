
package mage.cards.m;

import java.util.UUID;
import mage.abilities.mana.BlackManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author LevelX2
 */
public final class MoxJet extends CardImpl {

    public MoxJet(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{0}");

        // {tap}: Add {B}.
        this.addAbility(new BlackManaAbility());
    }

    private MoxJet(final MoxJet card) {
        super(card);
    }

    @Override
    public MoxJet copy() {
        return new MoxJet(this);
    }
}
