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

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.condition.common.RevoltCondition;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;
import mage.watchers.common.RevoltWatcher;

/**
 *
 * @author emerald000
 */
public class FatalPush extends CardImpl {

    public FatalPush(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{B}");

        // Destroy target creature if it has converted mana cost 2 or less.
        // <i>Revolt</i> &mdash; Destroy that creature if it has converted mana cost 4 or less instead if a permanent you controlled left the battlefield this turn.
        this.getSpellAbility().addEffect(new FatalPushEffect());
        this.getSpellAbility().addWatcher(new RevoltWatcher());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    public FatalPush(final FatalPush card) {
        super(card);
    }

    @Override
    public FatalPush copy() {
        return new FatalPush(this);
    }
}
class FatalPushEffect extends OneShotEffect {

    FatalPushEffect() {
        super(Outcome.DestroyPermanent);
        this.staticText = "Destroy target creature if it has converted mana cost 2 or less.<br><i>Revolt</i> &mdash; Destroy that creature if it has converted mana cost 4 or less instead if a permanent you controlled left the battlefield this turn.";
    }

    FatalPushEffect(final FatalPushEffect effect) {
        super(effect);
    }

    @Override
    public FatalPushEffect copy() {
        return new FatalPushEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent targetCreature = game.getPermanent(this.getTargetPointer().getFirst(game, source));
        if (targetCreature != null) {
            int cmc = targetCreature.getConvertedManaCost();
            if (cmc <= 2 || (RevoltCondition.getInstance().apply(game, source) && cmc <= 4)) {
                targetCreature.destroy(source.getSourceId(), game, false);
            }
            return true;
        }
        return false;
    }
}
