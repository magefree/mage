
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author ciaccona007
 */
public final class DevoteeOfStrength extends CardImpl {

    public DevoteeOfStrength(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");
        
        this.subtype.add(SubType.NAGA);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // {4}{G}: Target creature gets +2/+2 until end of turn.
        SimpleActivatedAbility ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new BoostTargetEffect(2,2,Duration.EndOfTurn), new ManaCostsImpl<>("{4}{G}"));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private DevoteeOfStrength(final DevoteeOfStrength card) {
        super(card);
    }

    @Override
    public DevoteeOfStrength copy() {
        return new DevoteeOfStrength(this);
    }
}
