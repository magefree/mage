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
package mage.abilities.keyword;

import java.util.ArrayList;
import java.util.UUID;
import mage.abilities.SpellAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.cards.Card;
import mage.constants.SpellAbilityType;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;
import mage.watchers.common.CastSpellLastTurnWatcher;

/**
 *
 * @author LevelX2
 */
public class SurgeAbility extends SpellAbility {

    public static final String SURGE_ACTIVATION_VALUE_KEY = "surgeActivation";

    private String rule;

    public SurgeAbility(Card card, String surgeCosts) {
        super(new ManaCostsImpl<>(surgeCosts), card.getName() + " with surge", Zone.HAND, SpellAbilityType.BASE_ALTERNATE);
        this.getCosts().addAll(card.getSpellAbility().getCosts().copy());
        this.getEffects().addAll(card.getSpellAbility().getEffects().copy());
        this.getTargets().addAll(card.getSpellAbility().getTargets().copy());
        this.spellAbilityType = SpellAbilityType.BASE_ALTERNATE;
        this.timing = card.getSpellAbility().getTiming();
        this.setRuleAtTheTop(true);
        this.rule = "Surge " + surgeCosts
                + " <i>(You may cast this spell for its surge cost if you or a teammate has cast another spell this turn.)</i>";
    }

    public SurgeAbility(final SurgeAbility ability) {
        super(ability);
        this.rule = ability.rule;
    }

    @Override
    public boolean canActivate(UUID playerId, Game game) {
        // check if controller or teammate has already cast a spell this turn
        CastSpellLastTurnWatcher watcher = (CastSpellLastTurnWatcher) game.getState().getWatchers().get(CastSpellLastTurnWatcher.class.getSimpleName());
        if (watcher != null) {
            Player player = game.getPlayer(playerId);
            if (player != null) {
                for (UUID playerToCheckId : game.getState().getPlayersInRange(playerId, game)) {
                    if (!player.hasOpponent(playerToCheckId, game)) {
                        if (watcher.getAmountOfSpellsPlayerCastOnCurrentTurn(playerToCheckId) > 0) {
                            return super.canActivate(playerId, game);
                        }
                    }
                }
            }
        }
        return false;
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean activate(Game game, boolean noMana) {
        if (super.activate(game, noMana)) {
            ArrayList<Integer> surgeActivations = (ArrayList) game.getState().getValue(SURGE_ACTIVATION_VALUE_KEY + getSourceId());
            if (surgeActivations == null) {
                surgeActivations = new ArrayList<>(); // zoneChangeCounter
                game.getState().setValue(SURGE_ACTIVATION_VALUE_KEY + getSourceId(), surgeActivations);
            }
            surgeActivations.add(game.getState().getZoneChangeCounter(getSourceId()));
            return true;
        }
        return false;
    }

    @Override
    public SurgeAbility copy() {
        return new SurgeAbility(this);
    }

    @Override
    public String getRule(boolean all) {
        return getRule();
    }

    @Override
    public String getRule() {
        return rule;
    }

}
