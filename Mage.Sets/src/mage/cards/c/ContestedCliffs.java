
package mage.cards.c;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.FightTargetsEffect;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.target.Target;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class ContestedCliffs extends CardImpl {

    private static final FilterCreaturePermanent filter1 = new FilterCreaturePermanent("Beast creature you control");
    private static final FilterCreaturePermanent filter2 = new FilterCreaturePermanent("creature an opponent controls");
    static {
        filter1.add(TargetController.YOU.getControllerPredicate());
        filter1.add(SubType.BEAST.getPredicate());
        filter2.add(TargetController.OPPONENT.getControllerPredicate());
    }

    public ContestedCliffs(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");

        // {tap}: Add {C}.
        this.addAbility(new ColorlessManaAbility());
        // {R}{G}, {tap}: Choose target Beast creature you control and target creature an opponent controls. Those creatures fight each other.
        Effect effect = new FightTargetsEffect();
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, effect, new ManaCostsImpl("{R}{G}"));
        ability.addCost(new TapSourceCost());
        Target target1 = new TargetCreaturePermanent(filter1);
        ability.addTarget(target1);
        Target target2 = new TargetCreaturePermanent(filter2);
        ability.addTarget(target2);
        this.addAbility(ability);
        
    }

    private ContestedCliffs(final ContestedCliffs card) {
        super(card);
    }

    @Override
    public ContestedCliffs copy() {
        return new ContestedCliffs(this);
    }
}
