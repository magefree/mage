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
package mage.sets.weatherlight;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.continuous.AssignNoCombatDamageSourceEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterArtifactPermanent;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.game.combat.CombatGroup;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.Target;
import mage.target.TargetPermanent;

/**
 *
 * @author LevelX2
 */
public class GoblinVandal extends CardImpl {

    public GoblinVandal(UUID ownerId) {
        super(ownerId, 105, "Goblin Vandal", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{R}");
        this.expansionSetCode = "WTH";
        this.subtype.add("Goblin");
        this.subtype.add("Rogue");

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Whenever Goblin Vandal attacks and isn't blocked, you may pay {R}. If you do, destroy target artifact defending player controls and Goblin Vandal assigns no combat damage this turn.
        Effect effect = new DestroyTargetEffect();
        effect.setText("destroy target artifact defending player controls");
        DoIfCostPaid effect2 = new DoIfCostPaid(effect, new ManaCostsImpl("{R}"), "Pay {R} to destroy artifact of defending player?");
        effect = new AssignNoCombatDamageSourceEffect(Duration.EndOfTurn);
        effect.setText("and {this} assigns no combat damage this turn");
        effect2.addEffect(effect);
        this.addAbility(new GoblinVandalTriggeredAbility(effect2));

    }

    public GoblinVandal(final GoblinVandal card) {
        super(card);
    }

    @Override
    public GoblinVandal copy() {
        return new GoblinVandal(this);
    }
}

class GoblinVandalTriggeredAbility extends TriggeredAbilityImpl {

    public GoblinVandalTriggeredAbility(Effect effect) {
        super(Zone.BATTLEFIELD, effect, false );
    }

    public GoblinVandalTriggeredAbility(final GoblinVandalTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public GoblinVandalTriggeredAbility copy() {
        return new GoblinVandalTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.DECLARED_BLOCKERS) {
            Permanent sourcePermanent = game.getPermanent(getSourceId());
            if (sourcePermanent.isAttacking()) {
                for (CombatGroup combatGroup: game.getCombat().getGroups()) {
                    if (combatGroup.getBlockers().isEmpty() && combatGroup.getAttackers().contains(getSourceId())) {
                        UUID defendingPlayerId = game.getCombat().getDefendingPlayerId(getSourceId(), game);
                        FilterPermanent filter = new FilterArtifactPermanent();
                        filter.add(new ControllerIdPredicate(defendingPlayerId));
                        Target target = new TargetPermanent(filter);
                        this.addTarget(target);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever {this} attacks and isn't blocked, you may pay {R}. If you do, destroy target artifact defending player controls and {this} assigns no combat damage this turn";
    }
}
