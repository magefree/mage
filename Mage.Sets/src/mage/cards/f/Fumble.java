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
package mage.cards.f;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author TheElk801
 */
public final class Fumble extends CardImpl {

    public Fumble(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U}");

        // Return target creature to its owner's hand. Gain control of all Auras and Equipment that were attached to it, then attach them to another creature.
        this.getSpellAbility().addEffect(new FumbleEffect());
    }

    public Fumble(final Fumble card) {
        super(card);
    }

    @Override
    public Fumble copy() {
        return new Fumble(this);
    }
}

class FumbleEffect extends OneShotEffect {

    FumbleEffect() {
        super(Outcome.Benefit);
        this.staticText = "return target creature to its owner's hand. "
                + "Gain control of all Auras and Equipment that were attached to it, "
                + "then attach them to another creature";
    }

    FumbleEffect(final FumbleEffect effect) {
        super(effect);
    }

    @Override
    public FumbleEffect copy() {
        return new FumbleEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        Player player = game.getPlayer(source.getControllerId());
        if (player == null || permanent == null) {
            return false;
        }
        List<Permanent> attachments = new ArrayList<>();
        for (UUID permId : permanent.getAttachments()) {
            Permanent attachment = game.getPermanent(permId);
            if (attachment != null) {
                attachments.add(permanent);
            }
        }
        new ReturnToHandTargetEffect().apply(game, source);
        Target target = new TargetCreaturePermanent(1, 1, StaticFilters.FILTER_PERMANENT_CREATURE, true);
        Permanent newCreature = null;
        if (player.choose(Outcome.BoostCreature, target, source.getSourceId(), game)) {
            newCreature = game.getPermanent(target.getFirstTarget());
        }
        for (Permanent attachment : attachments) {
            if (!attachment.hasSubtype(SubType.AURA, game) && !attachment.hasSubtype(SubType.EQUIPMENT, game)) {
                continue;
            }
            ContinuousEffect effect = new GainControlTargetEffect(Duration.Custom, true, player.getId());
            effect.setTargetPointer(new FixedTarget(attachment, game));
            game.addEffect(effect, source);
            if (newCreature != null) {
                attachment.attachTo(newCreature.getId(), game);
            }
        }
        return true;
    }
}
