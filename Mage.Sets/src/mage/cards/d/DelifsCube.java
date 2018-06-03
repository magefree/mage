
package mage.cards.d;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.AttacksAndIsNotBlockedTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.RegenerateTargetEffect;
import mage.abilities.effects.common.continuous.AssignNoCombatDamageSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author MarcoMarin
 */
public final class DelifsCube extends CardImpl {

    public DelifsCube(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{1}");

        Ability ability2 = new AttacksAndIsNotBlockedTriggeredAbility(new DelifsCubeEffect(this.getId()), false);        
        ability2.addEffect(new AssignNoCombatDamageSourceEffect(Duration.EndOfTurn));        
        // {2}, {tap}: This turn, when target creature you control attacks and isn't blocked, it assigns no combat damage this turn and you put a cube counter on Delif's Cube.
        SimpleActivatedAbility ability = new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new GainAbilityTargetEffect(ability2, Duration.EndOfTurn),
                new ManaCostsImpl("{2}"));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetControlledCreaturePermanent());
        
        this.addAbility(ability);
        // {2}, Remove a cube counter from Delif's Cube: Regenerate target creature.
        SimpleActivatedAbility ability3 = new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new RegenerateTargetEffect(),
                new ManaCostsImpl("{2}"));
        ability3.addCost(new RemoveCountersSourceCost(CounterType.CUBE.createInstance()));
        ability3.addTarget(new TargetControlledCreaturePermanent());
        
        this.addAbility(ability3);
    }

    public DelifsCube(final DelifsCube card) {
        super(card);
    }

    @Override
    public DelifsCube copy() {
        return new DelifsCube(this);
    }
}

class DelifsCubeEffect extends OneShotEffect{
                      
    private UUID cubeId;
    
    public DelifsCubeEffect(UUID cubeId) {
        super(Outcome.Benefit);
        this.cubeId = cubeId;
        this.setText("This turn, when target creature you control attacks and isn't blocked, it assigns no combat damage this turn and you put a cube counter on Delif's Cube");
    }
    
    public DelifsCubeEffect(final DelifsCubeEffect effect) {
        super(effect);
        this.cubeId = effect.cubeId;
    }
    
    @Override
    public DelifsCubeEffect copy() {
        return new DelifsCubeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent perm = game.getPermanent(cubeId);
        if (perm == null) return false;
        perm.addCounters(CounterType.CUBE.createInstance(), source, game);
        return true;         
    }
}