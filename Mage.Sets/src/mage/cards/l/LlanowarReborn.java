
package mage.cards.l;

import java.util.UUID;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.keyword.GraftAbility;
import mage.abilities.mana.GreenManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author LevelX2
 */
public final class LlanowarReborn extends CardImpl {

    public LlanowarReborn(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");

        // Llanowar Reborn enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());
        // {tap}: Add {G}.
        this.addAbility(new GreenManaAbility());
        // Graft 1 (This land enters the battlefield with a +1/+1 counter on it. Whenever a creature enters the battlefield, you may move a +1/+1 counter from this land onto it.)
        this.addAbility(new GraftAbility(this, 1));
        
    }

    private LlanowarReborn(final LlanowarReborn card) {
        super(card);
    }

    @Override
    public LlanowarReborn copy() {
        return new LlanowarReborn(this);
    }
}
