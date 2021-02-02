
package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;

/**
 *
 * @author fireshoes
 */
public final class HavocDemon extends CardImpl {

    public HavocDemon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{5}{B}{B}");
        this.subtype.add(SubType.DEMON);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // When Havoc Demon dies, all creatures get -5/-5 until end of turn.
        this.addAbility(new DiesSourceTriggeredAbility(new BoostAllEffect(-5, -5, Duration.EndOfTurn), false));
    }

    private HavocDemon(final HavocDemon card) {
        super(card);
    }

    @Override
    public HavocDemon copy() {
        return new HavocDemon(this);
    }
}
