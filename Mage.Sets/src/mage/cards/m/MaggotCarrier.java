
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.LoseLifeAllPlayersEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author dustinconrad
 */
public final class MaggotCarrier extends CardImpl {

    public MaggotCarrier(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{B}");
        this.subtype.add(SubType.ZOMBIE);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // When Maggot Carrier enters the battlefield, each player loses 1 life.
        Ability ability = new EntersBattlefieldTriggeredAbility(new LoseLifeAllPlayersEffect(1));
        this.addAbility(ability);
    }

    private MaggotCarrier(final MaggotCarrier card) {
        super(card);
    }

    @Override
    public MaggotCarrier copy() {
        return new MaggotCarrier(this);
    }
}
