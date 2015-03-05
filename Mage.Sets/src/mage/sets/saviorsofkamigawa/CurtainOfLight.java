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
package mage.sets.saviorsofkamigawa;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.PhaseStep;
import mage.constants.Rarity;
import mage.constants.TurnPhase;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.AttackingPredicate;
import mage.filter.predicate.permanent.BlockingPredicate;
import mage.game.Game;
import mage.game.combat.CombatGroup;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public class CurtainOfLight extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("unblocked attacking creature");
    
    static {
        filter.add(new AttackingPredicate());
        filter.add(Predicates.not(new BlockingPredicate()));
    }
    
    public CurtainOfLight(UUID ownerId) {
        super(ownerId, 6, "Curtain of Light", Rarity.COMMON, new CardType[]{CardType.INSTANT}, "{1}{W}");
        this.expansionSetCode = "SOK";

        // Cast Curtain of Light only during combat after blockers are declared.
        Ability ability = new SimpleStaticAbility(Zone.ALL, new CurtainOfLightRuleModifyingEffect());
        ability.setRuleAtTheTop(true);
        this.addAbility(ability);
        
        // Target unblocked attacking creature becomes blocked.
        this.getSpellAbility().addEffect(new CurtainOfLightEffect()); 
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(filter));
        
        // Draw a card.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1));
    }

    public CurtainOfLight(final CurtainOfLight card) {
        super(card);
    }

    @Override
    public CurtainOfLight copy() {
        return new CurtainOfLight(this);
    }
}

class CurtainOfLightRuleModifyingEffect extends ContinuousRuleModifyingEffectImpl {

    CurtainOfLightRuleModifyingEffect() {
        super(Duration.EndOfGame, Outcome.Detriment);
        staticText = "Cast {this} only during combat after blockers are declared";
    }

    CurtainOfLightRuleModifyingEffect(final CurtainOfLightRuleModifyingEffect effect) {
        super(effect);
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getType().equals(GameEvent.EventType.CAST_SPELL) && event.getSourceId().equals(source.getSourceId())) {
            return !game.getPhase().getType().equals(TurnPhase.COMBAT) ||
                    game.getStep().getType().equals(PhaseStep.BEGIN_COMBAT) ||
                    game.getStep().getType().equals(PhaseStep.DECLARE_ATTACKERS);
        }
        return false;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public CurtainOfLightRuleModifyingEffect copy() {
        return new CurtainOfLightRuleModifyingEffect(this);
    }
}

class CurtainOfLightEffect extends OneShotEffect {
    
    public CurtainOfLightEffect() {
        super(Outcome.Benefit);
        this.staticText = "Target unblocked attacking creature becomes blocked";
    }
    
    public CurtainOfLightEffect(final CurtainOfLightEffect effect) {
        super(effect);
    }
    
    @Override
    public CurtainOfLightEffect copy() {
        return new CurtainOfLightEffect(this);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (controller != null && permanent != null) {
            CombatGroup combatGroup = game.getCombat().findGroup(permanent.getId());
            if (combatGroup != null) {
                combatGroup.setBlocked(true);
                return true;
            }
        }
        return false;
    }
}
