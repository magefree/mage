
package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.LoseLifeAllPlayersEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author fireshoes
 */
public final class RabidBloodsucker extends CardImpl {

    public RabidBloodsucker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{B}");
        this.subtype.add(SubType.VAMPIRE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        
        // When Rabid Bloodsucker enters the battlefield, each player loses 2 life.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new LoseLifeAllPlayersEffect(2), false));
    }

    private RabidBloodsucker(final RabidBloodsucker card) {
        super(card);
    }

    @Override
    public RabidBloodsucker copy() {
        return new RabidBloodsucker(this);
    }
}
