

package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;

/**
 *
 * @author LevelX2
 */


public final class KraulWarrior extends CardImpl {

    public KraulWarrior (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{G}");
        this.subtype.add(SubType.INSECT);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {5}{G}: Kraul Warrior gets +3/+3 until end of turn.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new BoostSourceEffect(3,3,Duration.EndOfTurn), new ManaCostsImpl<>("{5}{G}")));

    }

    public KraulWarrior (final KraulWarrior card) {
        super(card);
    }

    @Override
    public KraulWarrior copy() {
        return new KraulWarrior(this);
    }

}
