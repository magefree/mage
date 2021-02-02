
package mage.cards.h;

import java.util.UUID;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.keyword.BuybackAbility;
import mage.abilities.keyword.StormAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

/**
 *
 * @author LevelX2
 */
public final class HazeOfRage extends CardImpl {

    public HazeOfRage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{1}{R}");


        // Buyback {2}
        this.addAbility(new BuybackAbility("{2}"));

        // Creatures you control get +1/+0 until end of turn.
        this.getSpellAbility().addEffect(new BoostControlledEffect(1,0, Duration.EndOfTurn));
        
        // Storm
        this.addAbility(new StormAbility());
        
    }

    private HazeOfRage(final HazeOfRage card) {
        super(card);
    }

    @Override
    public HazeOfRage copy() {
        return new HazeOfRage(this);
    }
}
