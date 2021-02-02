
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.target.Target;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author LevelX2
 */
public final class GoblinTestPilot extends CardImpl {

    public GoblinTestPilot(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{U}{R}");
        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.PILOT);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(0);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // {tap}: Goblin Test Pilot deals 2 damage to any target chosen at random.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DamageTargetEffect(2), new TapSourceCost());
        Target target = new TargetAnyTarget();
        target.setRandom(true);
        ability.addTarget(target);
        this.addAbility(ability);

    }

    private GoblinTestPilot(final GoblinTestPilot card) {
        super(card);
    }

    @Override
    public GoblinTestPilot copy() {
        return new GoblinTestPilot(this);
    }
}
