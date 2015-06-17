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
package mage.sets.magicorigins;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfCombatTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FirstTargetPointer;

/**
 *
 * @author LevelX2
 */
public class SentinelOfTheEternalWatch extends CardImpl {

    public SentinelOfTheEternalWatch(UUID ownerId) {
        super(ownerId, 30, "Sentinel of the Eternal Watch", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{5}{W}");
        this.expansionSetCode = "ORI";
        this.subtype.add("Giant");
        this.subtype.add("Soldier");
        this.power = new MageInt(4);
        this.toughness = new MageInt(6);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());
        // At the beginning of combat on each opponent's turn, tap target creature that player controls.
        this.addAbility(new BeginningOfCombatTriggeredAbility(Zone.BATTLEFIELD, new TapTargetEffect("target creature that player controls"), TargetController.OPPONENT, false, true));
        
    }

    public SentinelOfTheEternalWatch(final SentinelOfTheEternalWatch card) {
        super(card);
    }

    @Override
    public SentinelOfTheEternalWatch copy() {
        return new SentinelOfTheEternalWatch(this);
    }

    @Override
    public void adjustTargets(Ability ability, Game game) {
        if (ability instanceof BeginningOfCombatTriggeredAbility) {
            for (Effect effect: ability.getEffects()) {
                UUID opponentId = effect.getTargetPointer().getFirst(game, ability);
                Player opponent = game.getPlayer(opponentId);
                if (opponent != null) {
                    effect.setTargetPointer(new FirstTargetPointer());
                    FilterCreaturePermanent filter = new FilterCreaturePermanent("a creature from the active opponent");
                    filter.add(new ControllerIdPredicate(opponentId));
                    Target target = new TargetCreaturePermanent(filter);
                    ability.addTarget(target);
                }
            }
        }
    }
    
    
}
