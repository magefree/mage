
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
public final class SkinbrandGoblin extends CardImpl {

    public SkinbrandGoblin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{R}");
        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Bloodrush - {R}, Discard Skinbrand Goblin: Target attacking creature gets +2/+1 until end of turn.
        this.addAbility(new BloodrushAbility("{R}", new BoostTargetEffect(2, 1, Duration.EndOfTurn)));
    }

    private SkinbrandGoblin(final SkinbrandGoblin card) {
        super(card);
    }

    @Override
    public SkinbrandGoblin copy() {
        return new SkinbrandGoblin(this);
    }
}
