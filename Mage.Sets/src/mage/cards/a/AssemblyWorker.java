
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.target.TargetPermanent;

/**
 *
 * @author LevelX2
 */
public final class AssemblyWorker extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Assembly-Worker creature");

    static {
        filter.add(SubType.ASSEMBLY_WORKER.getPredicate());
    }

    public AssemblyWorker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{3}");
        this.subtype.add(SubType.ASSEMBLY_WORKER);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {tap}: Target Assembly-Worker creature gets +1/+1 until end of turn.
       Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new BoostTargetEffect(1, 1, Duration.EndOfTurn), new TapSourceCost());
       ability.addTarget(new TargetPermanent(filter));
       this.addAbility(ability);
    }

    private AssemblyWorker(final AssemblyWorker card) {
        super(card);
    }

    @Override
    public AssemblyWorker copy() {
        return new AssemblyWorker(this);
    }
}
