
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.DamageEverythingEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Plopman
 */
public final class MagmaGiant extends CardImpl {

    public MagmaGiant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{5}{R}{R}");
        this.subtype.add(SubType.GIANT);

        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // When Magma Giant enters the battlefield, it deals 2 damage to each creature and each player.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new DamageEverythingEffect(2)));
    }

    private MagmaGiant(final MagmaGiant card) {
        super(card);
    }

    @Override
    public MagmaGiant copy() {
        return new MagmaGiant(this);
    }
}
