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

package mage.sets.returntoravnica;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

/**
 * http://www.wizards.com/magic/magazine/article.aspx?x=mtg/faq/rtr
 * Shrieking Affliction's ability will trigger only if an opponent begins his or
 * her upkeep with one or fewer cards in hand.
 * The ability will check the number of cards in that player's hand again when
 * it tries to resolve. If that player has two or more cards in hand at that time,
 * that player won't lose life.
 *
 * @author LevelX2
 */

public class ShriekingAffliction extends CardImpl<ShriekingAffliction> {

    static final String rule = "At the beginning of the upkeep of enchanted creature's controller, that player loses 2 life";

    public ShriekingAffliction (UUID ownerId) {
        super(ownerId, 76, "Shrieking Affliction", Rarity.UNCOMMON, new CardType[]{CardType.ENCHANTMENT}, "{B}");
        this.expansionSetCode = "RTR";
        this.color.setBlack(true);

        // At the beginning of each opponent's upkeep, if that player has one or fewer cards in hand, he or she loses 3 life.
        this.addAbility(new ShriekingAfflictionTriggeredAbility());
    }

    public ShriekingAffliction (final ShriekingAffliction card) {
        super(card);
    }

    @Override
    public ShriekingAffliction copy() {
        return new ShriekingAffliction(this);
    }
}

class ShriekingAfflictionTriggeredAbility extends TriggeredAbilityImpl<ShriekingAfflictionTriggeredAbility> {

    public ShriekingAfflictionTriggeredAbility() {
        super(Zone.BATTLEFIELD, null);
    }

    public ShriekingAfflictionTriggeredAbility(final ShriekingAfflictionTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public ShriekingAfflictionTriggeredAbility copy() {
        return new ShriekingAfflictionTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.UPKEEP_STEP_PRE && game.getOpponents(controllerId).contains(event.getPlayerId())) {
            Player player = game.getPlayer(event.getPlayerId());
            if (player != null && player.getHand().size() < 2) {

                this.getEffects().clear();
                ShriekingAfflictionTargetEffect effect = new ShriekingAfflictionTargetEffect();
                Permanent sourcePermanent = game.getPermanent(sourceId);
                if (sourcePermanent != null) {
                    game.informPlayers(sourcePermanent.getName() + ": " + player.getName() + " loses 3 life");
                }

                effect.setTargetPointer(new FixedTarget(player.getId()));
                this.addEffect(effect);
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "At the beginning of each opponent's upkeep, if that player has one or fewer cards in hand, he or she loses 3 life.";
    }
}
class ShriekingAfflictionTargetEffect extends OneShotEffect<ShriekingAfflictionTargetEffect> {

    public ShriekingAfflictionTargetEffect() {
        super(Constants.Outcome.Damage);
        staticText = "he or she loses 3 life";
    }

    public ShriekingAfflictionTargetEffect(final ShriekingAfflictionTargetEffect effect) {
        super(effect);
    }

    @Override
    public ShriekingAfflictionTargetEffect copy() {
        return new ShriekingAfflictionTargetEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(targetPointer.getFirst(game, source));
        if (player != null && player.getHand().size() < 2) {
            player.loseLife(3, game);
            return true;
        }
        return false;
    }

}
