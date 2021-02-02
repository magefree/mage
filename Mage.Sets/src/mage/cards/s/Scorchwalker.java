
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.keyword.BloodrushAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;

/**
 *
 * @author Plopman
 */
public final class Scorchwalker extends CardImpl {

    public Scorchwalker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{R}");
        this.subtype.add(SubType.ELEMENTAL);

        this.power = new MageInt(5);
        this.toughness = new MageInt(1);

        // Bloodrush - {1}{R}{R}, Discard Scorchwalker: Target attacking creature gets +5/+1 until end of turn.
        this.addAbility(new BloodrushAbility("{1}{R}{R}", new BoostTargetEffect(5, 1, Duration.EndOfTurn)));
    }

    private Scorchwalker(final Scorchwalker card) {
        super(card);
    }

    @Override
    public Scorchwalker copy() {
        return new Scorchwalker(this);
    }
}
