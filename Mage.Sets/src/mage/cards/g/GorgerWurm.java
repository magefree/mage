
package mage.cards.g;

import mage.MageInt;
import mage.abilities.keyword.DevourAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public final class GorgerWurm extends CardImpl {

    public GorgerWurm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{R}{G}");
        this.subtype.add(SubType.WURM);


        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Devour 1 (As this enters the battlefield, you may sacrifice any number of creatures. This creature enters the battlefield with twice that many +1/+1 counters on it.)
        this.addAbility(new DevourAbility(1));
    }

    private GorgerWurm(final GorgerWurm card) {
        super(card);
    }

    @Override
    public GorgerWurm copy() {
        return new GorgerWurm(this);
    }
}
