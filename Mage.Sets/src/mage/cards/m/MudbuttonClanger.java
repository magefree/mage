
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.abilityword.KinshipAbility;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

/**
 *
 * @author LevelX2
 */
public final class MudbuttonClanger extends CardImpl {

    public MudbuttonClanger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{R}");
        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Kinship - At the beginning of your upkeep, you may look at the top card of your library. If it shares a creature type with Mudbutton Clanger, you may reveal it. 
        // If you do, Mudbutton Clanger gets +1/+1 until end of turn.
        this.addAbility(new KinshipAbility(new BoostSourceEffect(1, 1, Duration.EndOfTurn)));
    }

    private MudbuttonClanger(final MudbuttonClanger card) {
        super(card);
    }

    @Override
    public MudbuttonClanger copy() {
        return new MudbuttonClanger(this);
    }
}
