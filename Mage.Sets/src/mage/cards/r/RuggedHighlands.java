
package mage.cards.r;

import java.util.UUID;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.mana.GreenManaAbility;
import mage.abilities.mana.RedManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author LevelX2
 */
public final class RuggedHighlands extends CardImpl {

    public RuggedHighlands(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");

        // Rugged Highlands enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());
        // When Rugged Highlands enters the battlefield, you gain 1 life.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new GainLifeEffect(1)));
        // {T}: Add {R} or {G}.
        this.addAbility(new RedManaAbility());
        this.addAbility(new GreenManaAbility());
                
    }

    private RuggedHighlands(final RuggedHighlands card) {
        super(card);
    }

    @Override
    public RuggedHighlands copy() {
        return new RuggedHighlands(this);
    }
}
