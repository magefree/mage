
package mage.cards.d;

import java.util.UUID;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author North
 */
public final class DaruEncampment extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Soldier creature");

    static {
        filter.add(SubType.SOLDIER.getPredicate());
    }

    public DaruEncampment(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");

        this.addAbility(new ColorlessManaAbility());
        // {W}, {tap}: Target Soldier creature gets +1/+1 until end of turn.
        SimpleActivatedAbility ability = new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new BoostTargetEffect(1, 1, Duration.EndOfTurn),
                new ManaCostsImpl<>("{W}"));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetCreaturePermanent(filter));
        this.addAbility(ability);
    }

    private DaruEncampment(final DaruEncampment card) {
        super(card);
    }

    @Override
    public DaruEncampment copy() {
        return new DaruEncampment(this);
    }
}
