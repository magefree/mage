
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.BecomesMonstrousSourceTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.MonstrosityAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;

/**
 *
 * @author Styxo
 */
public final class Gundark extends CardImpl {

    public Gundark(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{R}");
        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // {3}{R}{R}: Monstrosity 3.
        this.addAbility(new MonstrosityAbility("{3}{R}{R}", 3));

        //  When Gundark becomes monstrous, it gets +2/+2 until end of turn.
        this.addAbility(new BecomesMonstrousSourceTriggeredAbility(new BoostSourceEffect(2, 2, Duration.EndOfTurn)));
    }

    private Gundark(final Gundark card) {
        super(card);
    }

    @Override
    public Gundark copy() {
        return new Gundark(this);
    }
}
