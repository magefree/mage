
package mage.cards.v;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;

/**
 *
 * @author jeffwadsworth
 */
public final class ViashinoFirstblade extends CardImpl {

    public ViashinoFirstblade(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{R}{W}");
        this.subtype.add(SubType.VIASHINO);
        this.subtype.add(SubType.SOLDIER);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Haste
        this.addAbility(HasteAbility.getInstance());
        
        // When Viashino Firstblade enters the battlefield, it gets +2/+2 until end of turn.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new BoostSourceEffect(2, 2, Duration.EndOfTurn)));
    }

    private ViashinoFirstblade(final ViashinoFirstblade card) {
        super(card);
    }

    @Override
    public ViashinoFirstblade copy() {
        return new ViashinoFirstblade(this);
    }
}
