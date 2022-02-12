
package mage.cards.t;

import java.util.UUID;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.keyword.ScryEffect;
import mage.abilities.mana.BlackManaAbility;
import mage.abilities.mana.GreenManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author LevelX2
 */
public final class TempleOfMalady extends CardImpl {

    public TempleOfMalady(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");

        // Temple of Malady enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());
        // When Temple of Malady enters the battlefield, scry 1.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new ScryEffect(1, false)));
        // {T}: Add {B} or {G}.
        this.addAbility(new BlackManaAbility());
        this.addAbility(new GreenManaAbility());        
        
    }

    private TempleOfMalady(final TempleOfMalady card) {
        super(card);
    }

    @Override
    public TempleOfMalady copy() {
        return new TempleOfMalady(this);
    }
}
