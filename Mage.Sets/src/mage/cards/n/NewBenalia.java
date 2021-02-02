
package mage.cards.n;

import java.util.UUID;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.keyword.ScryEffect;
import mage.abilities.mana.WhiteManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author jonubuu
 */
public final class NewBenalia extends CardImpl {

    public NewBenalia(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");

        // New Benalia enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());
        // When New Benalia enters the battlefield, scry 1.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new ScryEffect(1)));
        // {tap}: Add {W}.
        this.addAbility(new WhiteManaAbility());
    }

    private NewBenalia(final NewBenalia card) {
        super(card);
    }

    @Override
    public NewBenalia copy() {
        return new NewBenalia(this);
    }
}
