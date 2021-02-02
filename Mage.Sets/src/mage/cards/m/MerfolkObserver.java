
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.LookLibraryTopCardTargetPlayerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.TargetPlayer;

/**
 *
 * @author North
 */
public final class MerfolkObserver extends CardImpl {

    public MerfolkObserver(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{U}");
        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.ROGUE);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // When Merfolk Observer enters the battlefield, look at the top card of target player's library.
        EntersBattlefieldTriggeredAbility ability = new EntersBattlefieldTriggeredAbility(new LookLibraryTopCardTargetPlayerEffect());
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
    }

    private MerfolkObserver(final MerfolkObserver card) {
        super(card);
    }

    @Override
    public MerfolkObserver copy() {
        return new MerfolkObserver(this);
    }
}
