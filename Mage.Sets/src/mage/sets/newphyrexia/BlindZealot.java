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
package mage.sets.newphyrexia;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.IntimidateAbility;
import mage.cards.CardImpl;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.game.events.DamagedPlayerEvent;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author North
 */
public class BlindZealot extends CardImpl<BlindZealot> {

    public BlindZealot(UUID ownerId) {
        super(ownerId, 52, "Blind Zealot", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{1}{B}{B}");
        this.expansionSetCode = "NPH";
        this.subtype.add("Human");
        this.subtype.add("Cleric");

        this.color.setBlack(true);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        this.addAbility(IntimidateAbility.getInstance());
        // Whenever Blind Zealot deals combat damage to a player, you may sacrifice it. If you do, destroy target creature that player controls.
        this.addAbility(new BlindZealotTriggeredAbility());
    }

    public BlindZealot(final BlindZealot card) {
        super(card);
    }

    @Override
    public BlindZealot copy() {
        return new BlindZealot(this);
    }
}

class BlindZealotTriggeredAbility extends TriggeredAbilityImpl<BlindZealotTriggeredAbility> {

    public BlindZealotTriggeredAbility() {
        super(Zone.BATTLEFIELD, new BlindZealotEffect(), false);
    }

    public BlindZealotTriggeredAbility(final BlindZealotTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public BlindZealotTriggeredAbility copy() {
        return new BlindZealotTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == EventType.DAMAGED_PLAYER && event.getSourceId().equals(this.sourceId)
                && ((DamagedPlayerEvent) event).isCombatDamage()) {

            Player player = game.getPlayer(this.getControllerId());
            Permanent sourcePermanent = game.getPermanent(this.getSourceId());

            if (player != null && sourcePermanent != null) {
                StringBuilder sb = new StringBuilder();
                sb.append("Do you wish to sacrifice ").append(sourcePermanent.getName());
                sb.append(" to destroy target creature controlled by ");
                sb.append(game.getPlayer(event.getTargetId()).getName()).append("?");
                if (player.chooseUse(Outcome.DestroyPermanent, sb.toString(), game)) {
                    FilterCreaturePermanent filter = new FilterCreaturePermanent();
                    filter.add(new ControllerIdPredicate(event.getTargetId()));
                    filter.setMessage("creature controlled by " + game.getPlayer(event.getTargetId()).getName());

                    this.getTargets().clear();
                    this.addTarget(new TargetCreaturePermanent(filter));
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever Blind Zealot deals combat damage to a player, you may sacrifice it. If you do, destroy target creature that player controls.";
    }
}

class BlindZealotEffect extends OneShotEffect<BlindZealotEffect> {

    public BlindZealotEffect() {
        super(Outcome.DestroyPermanent);
    }

    public BlindZealotEffect(final BlindZealotEffect effect) {
        super(effect);
    }

    @Override
    public BlindZealotEffect copy() {
        return new BlindZealotEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent sourcePermanent = game.getPermanent(source.getSourceId());
        Permanent targetPermanent = game.getPermanent(source.getFirstTarget());

        if (sourcePermanent != null && targetPermanent != null) {
            if (sourcePermanent.sacrifice(source.getSourceId(), game)) {
                targetPermanent.destroy(source.getId(), game, false);
            }
        }

        return false;
    }
}
