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
package mage.sets.tempestremastered;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.RequirementEffect;
import mage.abilities.effects.common.combat.MustBeBlockedByTargetSourceEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.SetTargetPointer;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author emerald000
 */
public class CrashingBoars extends CardImpl {

    public CrashingBoars(UUID ownerId) {
        super(ownerId, 168, "Crashing Boars", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{3}{G}{G}");
        this.expansionSetCode = "TPR";
        this.subtype.add("Boar");
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Whenever Crashing Boars attacks, defending player chooses an untapped creature he or she controls. That creature block Crashing Boars this turn if able.
        this.addAbility(new AttacksTriggeredAbility(new CrashingBoarsEffect(), false, "", SetTargetPointer.PLAYER));
    }

    public CrashingBoars(final CrashingBoars card) {
        super(card);
    }

    @Override
    public CrashingBoars copy() {
        return new CrashingBoars(this);
    }
}

class CrashingBoarsEffect extends OneShotEffect {
    
    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("untapped creature you control");
    static {
        filter.add(Predicates.not(new TappedPredicate()));
    }
    
    CrashingBoarsEffect() {
        super(Outcome.Benefit);
        this.staticText = "defending player chooses an untapped creature he or she controls. That creature blocks {this} this turn if able";
    }
    
    CrashingBoarsEffect(final CrashingBoarsEffect effect) {
        super(effect);
    }
    
    @Override
    public CrashingBoarsEffect copy() {
        return new CrashingBoarsEffect(this);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        Player defendingPlayer = game.getPlayer(this.getTargetPointer().getFirst(game, source));
        if (defendingPlayer != null) {
            Target target = new TargetControlledCreaturePermanent(1, 1, filter, true);
            if (target.choose(Outcome.Neutral, defendingPlayer.getId(), source.getSourceId(), game)) {
                RequirementEffect effect = new MustBeBlockedByTargetSourceEffect();
                effect.setTargetPointer(new FixedTarget(target.getFirstTarget()));
                game.addEffect(effect, source);
            }
            return true;
        }
        return false;
    }
}
