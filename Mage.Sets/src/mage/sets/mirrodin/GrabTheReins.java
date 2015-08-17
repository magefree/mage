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
package mage.sets.mirrodin;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.abilities.keyword.EntwineAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetCreatureOrPlayer;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LoneFox

 */
public class GrabTheReins extends CardImpl {

    public GrabTheReins(UUID ownerId) {
        super(ownerId, 95, "Grab the Reins", Rarity.UNCOMMON, new CardType[]{CardType.INSTANT}, "{3}{R}");
        this.expansionSetCode = "MRD";

        // Choose one -
        this.getSpellAbility().getModes().setMinModes(1);
        this.getSpellAbility().getModes().setMaxModes(1);
        // Until end of turn, you gain control of target creature and it gains haste;
        Effect effect = new GainControlTargetEffect(Duration.EndOfTurn);
        effect.setText("Until end of turn, you gain control of target creature");
        effect.setApplyEffectsAfter();
        this.getSpellAbility().addEffect(effect);
        effect = new GainAbilityTargetEffect(HasteAbility.getInstance(), Duration.EndOfTurn);
        effect.setText("and it gains haste");
        this.getSpellAbility().addEffect(effect);
        TargetCreaturePermanent target = new TargetCreaturePermanent();
        target.setTargetName("a creature to take control of");
        this.getSpellAbility().addTarget(target);
        // or sacrifice a creature, then Grab the Reins deals damage equal to that creature's power to target creature or player.
        Mode mode = new Mode();
        mode.getEffects().add(new GrabTheReinsEffect());
        TargetCreatureOrPlayer target2 = new TargetCreatureOrPlayer();
        target2.setTargetName("a creature or player to damage");
        mode.getTargets().add(target2);
        this.getSpellAbility().getModes().addMode(mode);

        // Entwine {2}{R}
        this.addAbility(new EntwineAbility("{2}{R}"));
    }

    public GrabTheReins(final GrabTheReins card) {
        super(card);
    }

    @java.lang.Override
    public GrabTheReins copy() {
        return new GrabTheReins(this);
    }
}

class GrabTheReinsEffect extends OneShotEffect {

    public GrabTheReinsEffect() {
        super(Outcome.Damage);
        staticText = "sacrifice a creature. {this} deals damage equal to the sacrificed creature's power to target creature or player";
    }

    public GrabTheReinsEffect(final GrabTheReinsEffect effect) {
        super(effect);
    }

    @java.lang.Override
    public boolean apply(Game game, Ability source) {
        UUID controllerId = source.getControllerId();
        Target target = new TargetCreaturePermanent();
        target.setNotTarget(true);
        target.setTargetName("a creature to sacrifice");
        if(!target.canChoose(source.getSourceId(), controllerId, game)) {
            return false;
        }
        Player player = game.getPlayer(controllerId);
        if(player != null) {
            player.chooseTarget(Outcome.Sacrifice, target, source, game);
            Permanent creatureToSacrifice = game.getPermanent(target.getTargets().get(0));
            int amount = creatureToSacrifice.getPower().getValue();
            if(!creatureToSacrifice.sacrifice(creatureToSacrifice.getId(), game)) {
                return false;
            }
            if (amount > 0) {
                Permanent permanent = game.getPermanent(source.getFirstTarget());
                if (permanent != null) {
                    permanent.damage(amount, source.getSourceId(), game, false, true);
                    return true;
                }
                player = game.getPlayer(source.getFirstTarget());
                if (player != null) {
                    player.damage(amount, source.getSourceId(), game, false, true);
                    return true;
                }
            }
            else {
                return true;
            }
        }
        return false;
    }

    @java.lang.Override
    public GrabTheReinsEffect copy() {
        return new GrabTheReinsEffect(this);
    }
}
