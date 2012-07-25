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
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;

/**
 *
 * @author jeffwadsworth
 */
public class RealitySpasm extends CardImpl<RealitySpasm> {
    
    public RealitySpasm(UUID ownerId) {
        super(ownerId, 81, "Reality Spasm", Rarity.UNCOMMON, new CardType[]{CardType.INSTANT}, "{X}{U}{U}");
        this.expansionSetCode = "ROE";

        this.color.setBlue(true);

        // Choose one - Tap X target permanents; or untap X target permanents.
        this.getSpellAbility().addEffect(new RealitySpasmTapEffect());
        Mode mode = new Mode();
        mode.getEffects().add(new RealitySpasmUntapEffect());
        this.getSpellAbility().addMode(mode);
    }

    public RealitySpasm(final RealitySpasm card) {
        super(card);
    }

    @Override
    public RealitySpasm copy() {
        return new RealitySpasm(this);
    }
}

class RealitySpasmTapEffect extends OneShotEffect<RealitySpasmTapEffect> {
    
    FilterPermanent filter = new FilterPermanent("permanent");

    public RealitySpasmTapEffect() {
        super(Outcome.Tap);
        staticText = "Tap X target permanents";
    }

    public RealitySpasmTapEffect(final RealitySpasmTapEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int numberToTap = source.getManaCostsToPay().getX();
        numberToTap = Math.min(game.getBattlefield().getAllActivePermanents().size(), numberToTap);
        TargetPermanent target = new TargetPermanent(numberToTap, filter);
        if (target.canChoose(source.getControllerId(), game) && target.choose(Outcome.Tap, source.getControllerId(), source.getId(), game)) {
            if (!target.getTargets().isEmpty()) {
                List<UUID> targets = target.getTargets();
                for (UUID targetId : targets) {
                    Permanent permanent = game.getPermanent(targetId);
                    if (permanent != null) {
                        permanent.tap(game);
                    }
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public RealitySpasmTapEffect copy() {
        return new RealitySpasmTapEffect(this);
    }

}

class RealitySpasmUntapEffect extends OneShotEffect<RealitySpasmUntapEffect> {
    
    FilterPermanent filter = new FilterPermanent("permanent");

    public RealitySpasmUntapEffect() {
        super(Outcome.Untap);
        staticText = "Untap X target permanents";
    }

    public RealitySpasmUntapEffect(final RealitySpasmUntapEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int numberToTap = source.getManaCostsToPay().getX();
        numberToTap = Math.min(game.getBattlefield().getAllActivePermanents().size(), numberToTap);
        TargetPermanent target = new TargetPermanent(numberToTap, filter);
        if (target.canChoose(source.getControllerId(), game) && target.choose(Outcome.Untap, source.getControllerId(), source.getId(), game)) {
            if (!target.getTargets().isEmpty()) {
                List<UUID> targets = target.getTargets();
                for (UUID targetId : targets) {
                    Permanent permanent = game.getPermanent(targetId);
                    if (permanent != null) {
                        permanent.untap(game);
                    }
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public RealitySpasmUntapEffect copy() {
        return new RealitySpasmUntapEffect(this);
    }

}
