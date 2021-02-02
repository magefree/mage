
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;

/**
 *
 * @author LoneFox
 */
public final class GristleGrinner extends CardImpl {

    public GristleGrinner(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{B}");
        this.subtype.add(SubType.ZOMBIE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever a creature dies, Gristle Grinner gets +2/+2 until end of turn.
        this.addAbility(new DiesCreatureTriggeredAbility(new BoostSourceEffect(2, 2, Duration.EndOfTurn), false));
    }

    private GristleGrinner(final GristleGrinner card) {
        super(card);
    }

    @Override
    public GristleGrinner copy() {
        return new GristleGrinner(this);
    }
}
