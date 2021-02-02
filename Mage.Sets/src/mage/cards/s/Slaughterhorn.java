
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
 * @author LevelX2
 */
public final class Slaughterhorn extends CardImpl {

    public Slaughterhorn(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{G}");
        this.subtype.add(SubType.BEAST);

        this.power = new MageInt(3);
        this.toughness = new MageInt(2);


        // Bloodrush - {G}, Discard Slaughterhorn: Target attacking creature gets +3/+2 until end of turn.
        this.addAbility(new BloodrushAbility("{G}", new BoostTargetEffect(3,2, Duration.EndOfTurn)));
    }

    private Slaughterhorn(final Slaughterhorn card) {
        super(card);
    }

    @Override
    public Slaughterhorn copy() {
        return new Slaughterhorn(this);
    }
}
