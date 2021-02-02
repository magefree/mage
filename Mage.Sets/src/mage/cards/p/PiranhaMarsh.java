
package mage.cards.p;

import java.util.UUID;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.abilities.mana.BlackManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.TargetPlayer;

/**
 *
 * @author North
 */
public final class PiranhaMarsh extends CardImpl {

    public PiranhaMarsh(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");

        this.addAbility(new EntersBattlefieldTappedAbility());
        this.addAbility(new BlackManaAbility());
        EntersBattlefieldTriggeredAbility ability = new EntersBattlefieldTriggeredAbility(new LoseLifeTargetEffect(1));
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
    }

    private PiranhaMarsh(final PiranhaMarsh card) {
        super(card);
    }

    @Override
    public PiranhaMarsh copy() {
        return new PiranhaMarsh(this);
    }
}
