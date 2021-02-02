
package mage.cards.j;

import java.util.UUID;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.mana.BlackManaAbility;
import mage.abilities.mana.BlueManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author North
 */
public final class JwarIsleRefuge extends CardImpl {

    public JwarIsleRefuge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");

        this.addAbility(new EntersBattlefieldTappedAbility());
        this.addAbility(new BlueManaAbility());
        this.addAbility(new BlackManaAbility());
        this.addAbility(new EntersBattlefieldTriggeredAbility(new GainLifeEffect(1)));
    }

    private JwarIsleRefuge(final JwarIsleRefuge card) {
        super(card);
    }

    @Override
    public JwarIsleRefuge copy() {
        return new JwarIsleRefuge(this);
    }
}
