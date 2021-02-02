
package mage.cards.g;

import java.util.UUID;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.mana.GreenManaAbility;
import mage.abilities.mana.WhiteManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author North
 */
public final class GraypeltRefuge extends CardImpl {

    public GraypeltRefuge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");

        this.addAbility(new EntersBattlefieldTappedAbility());
        this.addAbility(new GreenManaAbility());
        this.addAbility(new WhiteManaAbility());
        this.addAbility(new EntersBattlefieldTriggeredAbility(new GainLifeEffect(1)));
    }

    private GraypeltRefuge(final GraypeltRefuge card) {
        super(card);
    }

    @Override
    public GraypeltRefuge copy() {
        return new GraypeltRefuge(this);
    }
}
