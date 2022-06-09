
package mage.cards.s;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.IslandwalkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.PermanentIdPredicate;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author MarcoMarin
 */
public final class SandalsOfAbdallah extends CardImpl {

    public SandalsOfAbdallah(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{4}");

        // {2}, {tap}: Target creature gains islandwalk until end of turn. When that creature dies this turn, destroy Sandals of Abdallah.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new GainAbilityTargetEffect(new IslandwalkAbility(), Duration.EndOfTurn), new ManaCostsImpl<>("{2}"));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
        
        //If the following doesn work, Alternatively do //Target target = ability.getTargets()
        //target.setTargetTag(1);
        //then later do: filter.add(new AnotherTargetPredicate(1));        
        FilterCreaturePermanent filter = new FilterCreaturePermanent();
        //hopefully a null pointer wont break anything before the ability is activated :-S
        filter.add(new PermanentIdPredicate(ability.getFirstTarget()));
        this.addAbility(new DiesCreatureTriggeredAbility(new SacrificeSourceEffect(), false, filter, true));
    }

    private SandalsOfAbdallah(final SandalsOfAbdallah card) {
        super(card);
    }

    @Override
    public SandalsOfAbdallah copy() {
        return new SandalsOfAbdallah(this);
    }
}
