
package mage.cards.b;

import java.util.UUID;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.ExileGraveyardAllTargetPlayerEffect;
import mage.abilities.mana.BlackManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.TargetPlayer;

/**
 *
 * @author North
 */
public final class BojukaBog extends CardImpl {

    public BojukaBog(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");

        // Bojuka Bog enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());
        // When Bojuka Bog enters the battlefield, exile all cards from target player's graveyard.
        EntersBattlefieldTriggeredAbility ability = new EntersBattlefieldTriggeredAbility(new ExileGraveyardAllTargetPlayerEffect());
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
        // {T}: Add {B}.
        this.addAbility(new BlackManaAbility());
    }

    private BojukaBog(final BojukaBog card) {
        super(card);
    }

    @Override
    public BojukaBog copy() {
        return new BojukaBog(this);
    }
}
