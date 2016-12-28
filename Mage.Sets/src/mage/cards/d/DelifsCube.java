/*
 *  Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without modification, are
 *  permitted provided that the following conditions are met:
 *
 *     1. Redistributions of source code must retain the above copyright notice, this list of
 *        conditions and the following disclaimer.
 *
 *     2. Redistributions in binary form must reproduce the above copyright notice, this list
 *        of conditions and the following disclaimer in the documentation and/or other materials
 *        provided with the distribution.
 *
 *  THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 *  WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 *  FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 *  CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *  CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 *  SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 *  ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *  NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 *  ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 *  The views and conclusions contained in the software and documentation are those of the
 *  authors and should not be interpreted as representing official policies, either expressed
 *  or implied, of BetaSteward_at_googlemail.com.
 */
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
public class DelifsCube extends CardImpl {

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