
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
public final class ScabClanCharger extends CardImpl {

    public ScabClanCharger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{G}");
        this.subtype.add(SubType.CENTAUR);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Bloodrush - {1}{G}, Discard Scab-Clan Charger: Target attacking creature gets +2/+4 until end of turn.
        this.addAbility(new BloodrushAbility("{1}{G}", new BoostTargetEffect(2, 4, Duration.EndOfTurn)));
    }

    private ScabClanCharger(final ScabClanCharger card) {
        super(card);
    }

    @Override
    public ScabClanCharger copy() {
        return new ScabClanCharger(this);
    }
}
