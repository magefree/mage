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
package mage.sets.tenth;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.watchers.Watcher;
import mage.watchers.WatcherImpl;

/**
 *
 * @author LevelX2
 */
public class RuleOfLaw extends CardImpl<RuleOfLaw> {

    public RuleOfLaw(UUID ownerId) {
        super(ownerId, 37, "Rule of Law", Rarity.UNCOMMON, new CardType[]{CardType.ENCHANTMENT}, "{2}{W}");
        this.expansionSetCode = "10E";

        this.color.setWhite(true);

        // Each player can't cast more than one spell each turn.
        this.addAbility(new SimpleStaticAbility(Constants.Zone.BATTLEFIELD, new RuleOfLawEffect()));
        this.addWatcher(new RuleOfLawWatcher());

    }

    public RuleOfLaw(final RuleOfLaw card) {
        super(card);
    }

    @Override
    public RuleOfLaw copy() {
        return new RuleOfLaw(this);
    }
}

class RuleOfLawWatcher extends WatcherImpl<RuleOfLawWatcher> {

    public RuleOfLawWatcher() {
        super("SpellCast", Constants.WatcherScope.PLAYER);
    }

    public RuleOfLawWatcher(final RuleOfLawWatcher watcher) {
        super(watcher);
    }

    @Override
    public RuleOfLawWatcher copy() {
        return new RuleOfLawWatcher(this);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (condition == true) {//no need to check - condition has already occured
            return;
        }
        if (event.getType() == GameEvent.EventType.SPELL_CAST ) {
            Permanent enchantment = game.getPermanent(this.sourceId);
            if (enchantment != null && enchantment.getAttachedTo() != null) {
                Player player = game.getPlayer(enchantment.getAttachedTo());
                if (player != null && event.getPlayerId().equals(player.getId())) {
                    condition = true;
                }
            }
        }
    }

}

class RuleOfLawEffect extends ReplacementEffectImpl<RuleOfLawEffect> {

    public RuleOfLawEffect() {
        super(Constants.Duration.WhileOnBattlefield, Constants.Outcome.Benefit);
        staticText = "Each player can't cast more than one spell each turn";
    }

    public RuleOfLawEffect(final RuleOfLawEffect effect) {
        super(effect);
    }

    @Override
    public RuleOfLawEffect copy() {
        return new RuleOfLawEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        return true;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getType() == GameEvent.EventType.CAST_SPELL) {
            Watcher watcher = game.getState().getWatchers().get("SpellCast", event.getPlayerId());
            if (watcher != null && watcher.conditionMet()) {
                return true;
            }
        }
        return false;
    }

}
