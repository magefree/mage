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
package mage.cards.p;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.IsStepCondition;
import mage.abilities.decorator.ConditionalTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.PhaseStep;
import mage.filter.StaticFilters;
import mage.filter.common.FilterAttackingCreature;
import mage.game.Game;
import mage.game.combat.CombatGroup;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;
import mage.target.common.TargetDefender;

/**
 *
 * @author LevelX2
 */
public class PortalMage extends CardImpl {

    public PortalMage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");

        this.subtype.add("Human");
        this.subtype.add("Wizard");
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // If Portal Mage enters the battlefield during the declare attackers step, you may reselect the player or planeswalker that the target attacking creature attacks.
        Ability ability = new ConditionalTriggeredAbility(
                new EntersBattlefieldTriggeredAbility(new PortalMageEffect(), true),
                new IsStepCondition(PhaseStep.DECLARE_ATTACKERS, false),
                "If {this} enters the battlefield during the declare attackers step, you may reselect the player or planeswalker that the target attacking creature attacks. "
                + "<i>(It can't attack its controller or its controller's planeswalkers.)</i>");
        ability.addTarget(new TargetCreaturePermanent(new FilterAttackingCreature()));
        this.addAbility(ability);
    }

    public PortalMage(final PortalMage card) {
        super(card);
    }

    @Override
    public PortalMage copy() {
        return new PortalMage(this);
    }
}

class PortalMageEffect extends OneShotEffect {

    public PortalMageEffect() {
        super(Outcome.Benefit);
        this.staticText = "you may reselect the player or planeswalker that the target attacking creature attacks";
    }

    public PortalMageEffect(final PortalMageEffect effect) {
        super(effect);
    }

    @Override
    public PortalMageEffect copy() {
        return new PortalMageEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Permanent attackingCreature = game.getPermanent(getTargetPointer().getFirst(game, source));
            if (attackingCreature != null) {
                CombatGroup combatGroupTarget = null;
                for (CombatGroup combatGroup : game.getCombat().getGroups()) {
                    if (combatGroup.getAttackers().contains(attackingCreature.getId())) {
                        combatGroupTarget = combatGroup;
                        break;
                    }
                }
                if (combatGroupTarget == null) {
                    return false;
                }
                // Reselecting which player or planeswalker a creature is attacking ignores all requirements,
                // restrictions, and costs associated with attacking.

                // Update possible defender
                Set<UUID> defenders = new LinkedHashSet<>();
                for (UUID playerId : game.getCombat().getAttackablePlayers(game)) {
                    defenders.add(playerId);
                    for (Permanent permanent : game.getBattlefield().getAllActivePermanents(StaticFilters.FILTER_PERMANENT_PLANESWALKER, playerId, game)) {
                        defenders.add(permanent.getId());
                    }
                }
                // Select the new defender
                TargetDefender target = new TargetDefender(defenders, null);
                target.setNotTarget(true); // player or planswalker hexproof does not prevent attacking a player
                if (controller.chooseTarget(Outcome.Damage, target, source, game)) {
                    if (!combatGroupTarget.getDefenderId().equals(target.getFirstTarget())) {
                        if (combatGroupTarget.changeDefenderPostDeclaration(target.getFirstTarget(), game)) {
                            String attacked = "";
                            Player player = game.getPlayer(target.getFirstTarget());
                            if (player != null) {
                                attacked = player.getLogName();
                            } else {
                                Permanent permanent = game.getPermanent(target.getFirstTarget());
                                if (permanent != null) {
                                    attacked = permanent.getLogName();
                                }
                            }
                            game.informPlayers(attackingCreature.getLogName() + " attacks now " + attacked);
                            return true;
                        }
                    }
                }
            }
            return true;
        }
        return false;
    }
}
