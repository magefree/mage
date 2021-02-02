
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.LookLibraryTopCardTargetPlayerEffect;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.TargetPlayer;

/**
 *
 * @author fireshoes
 */
public final class DewdropSpy extends CardImpl {

    public DewdropSpy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{U}{U}");
        this.subtype.add(SubType.FAERIE);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flash
        this.addAbility(FlashAbility.getInstance());
        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // When Dewdrop Spy enters the battlefield, look at the top card of target player's library.
        Ability ability = new EntersBattlefieldTriggeredAbility(new LookLibraryTopCardTargetPlayerEffect(), false);
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
    }

    private DewdropSpy(final DewdropSpy card) {
        super(card);
    }

    @Override
    public DewdropSpy copy() {
        return new DewdropSpy(this);
    }
}
