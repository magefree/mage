
package mage.cards.a;

import java.util.UUID;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.mana.BlackManaAbility;
import mage.abilities.mana.RedManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author North
 */
public final class AkoumRefuge extends CardImpl {

    public AkoumRefuge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");

        this.addAbility(new EntersBattlefieldTappedAbility());
        this.addAbility(new EntersBattlefieldTriggeredAbility(new GainLifeEffect(1)));
        this.addAbility(new BlackManaAbility());
        this.addAbility(new RedManaAbility());

    }

    private AkoumRefuge(final AkoumRefuge card) {
        super(card);
    }

    @Override
    public AkoumRefuge copy() {
        return new AkoumRefuge(this);
    }
}
