
package mage.cards.k;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.common.SourceTappedBeforeUntapStepCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalContinuousRuleModifyingEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DontUntapInControllersUntapStepAllEffect;
import mage.abilities.effects.common.TapAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterArtifactPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.PermanentIdPredicate;

/**
 *
 * @author spjspj
 */
public final class KillSwitch extends CardImpl {

    
    //static {
    //    filter.add(AnotherPredicate.instance);
   // }

    public KillSwitch(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{3}");

        // {2}, {tap}: Tap all other artifacts. They don't untap during their controllers' untap steps for as long as Kill Switch remains tapped.
        FilterArtifactPermanent filter = new FilterArtifactPermanent();
        filter.add(Predicates.not(new PermanentIdPredicate(getId())));
        
        SourceTappedBeforeUntapStepCondition condition = new SourceTappedBeforeUntapStepCondition();
        condition.setPermanentId(this.getId());
        Effect effect = new ConditionalContinuousRuleModifyingEffect(
                new DontUntapInControllersUntapStepAllEffect(Duration.WhileOnBattlefield, TargetController.ANY, filter),
                condition);
        effect.setText("Artifacts tapped this way don't untap during their controllers' untap steps for as long as {this} remains tapped");

        Effect effect2 = new TapAllEffect(filter);
        effect2.setText("Tap all other artifacts");
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, effect2, new ManaCostsImpl("{2}"));
        ability.addCost(new TapSourceCost());
        ability.addEffect(effect);
        this.addAbility(ability);        
    }

    private KillSwitch(final KillSwitch card) {
        super(card);
    }

    @Override
    public KillSwitch copy() {
        return new KillSwitch(this);
    }
}
