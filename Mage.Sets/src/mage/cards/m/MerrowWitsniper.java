
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.MillCardsTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.TargetPlayer;

/**
 *
 * @author fireshoes
 */
public final class MerrowWitsniper extends CardImpl {

    public MerrowWitsniper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{U}");
        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // When Merrow Witsniper enters the battlefield, target player puts the top card of their library into their graveyard.
        Ability ability = new EntersBattlefieldTriggeredAbility(new MillCardsTargetEffect(1));
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
    }

    private MerrowWitsniper(final MerrowWitsniper card) {
        super(card);
    }

    @Override
    public MerrowWitsniper copy() {
        return new MerrowWitsniper(this);
    }
}
