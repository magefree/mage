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
package mage.sets.riseoftheeldrazi;

import java.util.List;
import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

/**
 *
 * @author jeffwadsworth
 */
public class LightmineField extends CardImpl<LightmineField> {

    public LightmineField(UUID ownerId) {
        super(ownerId, 32, "Lightmine Field", Rarity.RARE, new CardType[]{CardType.ENCHANTMENT}, "{2}{W}{W}");
        this.expansionSetCode = "ROE";

        this.color.setWhite(true);

        // Whenever one or more creatures attack, Lightmine Field deals damage to each of those creatures equal to the number of attacking creatures.
        this.addAbility(new LightmineFieldTriggeredAbility());
    }

    public LightmineField(final LightmineField card) {
        super(card);
    }

    @Override
    public LightmineField copy() {
        return new LightmineField(this);
    }
}

class LightmineFieldTriggeredAbility extends TriggeredAbilityImpl<LightmineFieldTriggeredAbility> {

    public LightmineFieldTriggeredAbility() {
        super(Constants.Zone.BATTLEFIELD, new LightmineFieldEffect());
    }

    public LightmineFieldTriggeredAbility(final LightmineFieldTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public LightmineFieldTriggeredAbility copy() {
        return new LightmineFieldTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.DECLARED_ATTACKERS && !game.getCombat().getAttackers().isEmpty()) {
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever one or more creatures attack, " + super.getRule();
    }
}

class LightmineFieldEffect extends OneShotEffect<LightmineFieldEffect> {

    public LightmineFieldEffect() {
        super(Constants.Outcome.Damage);
        this.staticText = "{this} deals damage to each of those creatures equal to the number of attacking creatures";
    }

    public LightmineFieldEffect(final LightmineFieldEffect effect) {
        super(effect);
    }

    @Override
    public LightmineFieldEffect copy() {
        return new LightmineFieldEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        List<UUID> attackers = game.getCombat().getAttackers();
        int damage = attackers.size();
        if (!attackers.isEmpty()) {
            for (UUID attacker : attackers) {
                Permanent creature = game.getPermanent(attacker);
                if (creature != null) {
                    creature.damage(damage, source.getSourceId(), game, false, false);
                }
            }
            return true;
        }
        return false;
    }
}
