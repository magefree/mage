
package mage.cards.k;

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
 * @author North
 */
public final class KazanduRefuge extends CardImpl {

    public KazanduRefuge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");

        // Kazandu Refuge enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());
        // When Kazandu Refuge enters the battlefield, you gain 1 life.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new GainLifeEffect(1)));
        
        // {T}: Add {R} or {G}.
        this.addAbility(new RedManaAbility());
        this.addAbility(new GreenManaAbility());
        
    }

    private KazanduRefuge(final KazanduRefuge card) {
        super(card);
    }

    @Override
    public KazanduRefuge copy() {
        return new KazanduRefuge(this);
    }
}
