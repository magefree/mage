
package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.AttacksEachCombatStaticAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.SuperType;
import mage.constants.Zone;

/**
 *
 * @author LoneFox
 */
public final class PhyrexianSnowcrusher extends CardImpl {

    public PhyrexianSnowcrusher(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{6}");
       this.supertype.add(SuperType.SNOW);
        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.JUGGERNAUT);
        this.power = new MageInt(6);
        this.toughness = new MageInt(5);

        // Phyrexian Snowcrusher attacks each turn if able.
        this.addAbility(new AttacksEachCombatStaticAbility());
        // {1}{S}: Phyrexian Snowcrusher gets +1/+0 until end of turn.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new BoostSourceEffect(1, 0, Duration.EndOfTurn), new ManaCostsImpl<>("{1}{S}")));
    }

    private PhyrexianSnowcrusher(final PhyrexianSnowcrusher card) {
        super(card);
    }

    @Override
    public PhyrexianSnowcrusher copy() {
        return new PhyrexianSnowcrusher(this);
    }
}
