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
package mage.sets.shardsofalara;

import java.util.ArrayList;
import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageAllEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author jonubuu
 */
public class JundCharm extends CardImpl<JundCharm> {

    public JundCharm(UUID ownerId) {
        super(ownerId, 175, "Jund Charm", Rarity.UNCOMMON, new CardType[]{CardType.INSTANT}, "{B}{R}{G}");
        this.expansionSetCode = "ALA";

        this.color.setRed(true);
        this.color.setGreen(true);
        this.color.setBlack(true);

        // Choose one - Exile all cards from target player's graveyard;
        this.getSpellAbility().addEffect(new JundCharmEffect());
        this.getSpellAbility().addTarget(new TargetPlayer());
        // or Jund Charm deals 2 damage to each creature;
        Mode mode = new Mode();
        mode.getEffects().add(new DamageAllEffect(2, new FilterCreaturePermanent()));
        this.getSpellAbility().addMode(mode);
        // or put two +1/+1 counters on target creature.
        mode = new Mode();
        mode.getEffects().add(new AddCountersTargetEffect(CounterType.P1P1.createInstance(2), Constants.Outcome.BoostCreature));
        mode.getTargets().add(new TargetCreaturePermanent());
        this.getSpellAbility().addMode(mode);
    }

    public JundCharm(final JundCharm card) {
        super(card);
    }

    @Override
    public JundCharm copy() {
        return new JundCharm(this);
    }
}

class JundCharmEffect extends OneShotEffect<JundCharmEffect> {

    public JundCharmEffect() {
        super(Outcome.Exile);
        staticText = "Exile all cards from target player's graveyard";
    }

    @Override
    public JundCharmEffect copy() {
        return new JundCharmEffect();
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player targetPlayer = game.getPlayer(source.getFirstTarget());
        if (targetPlayer != null) {
            ArrayList<UUID> graveyard = new ArrayList<UUID>(targetPlayer.getGraveyard());
            for (UUID cardId : graveyard) {
                game.getCard(cardId).moveToZone(Zone.EXILED, cardId, game, false);
            }
            return true;
        }
        return false;
    }
}
