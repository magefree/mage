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
package mage.sets.shadowmoor;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.PreventAllDamageByAllEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.PermanentIdPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.Target;
import mage.target.common.TargetAttackingOrBlockingCreature;

/**
 *
 * @author jeffwadsworth
 *
 */
public class InquisitorsSnare extends CardImpl<InquisitorsSnare> {

    public InquisitorsSnare(UUID ownerId) {
        super(ownerId, 8, "Inquisitor's Snare", Rarity.COMMON, new CardType[]{CardType.INSTANT}, "{1}{W}");
        this.expansionSetCode = "SHM";

        this.color.setWhite(true);

        // Prevent all damage target attacking or blocking creature would deal this turn. If that creature is black or red, destroy it.
        this.getSpellAbility().addEffect(new InquisitorsSnareEffect());
        Target target = new TargetAttackingOrBlockingCreature();
        target.setRequired(true);
        this.getSpellAbility().addTarget(target);

    }

    public InquisitorsSnare(final InquisitorsSnare card) {
        super(card);
    }

    @Override
    public InquisitorsSnare copy() {
        return new InquisitorsSnare(this);
    }
}

class InquisitorsSnareEffect extends OneShotEffect<InquisitorsSnareEffect> {

    public InquisitorsSnareEffect() {
        super(Outcome.Detriment);
        this.staticText = "Prevent all damage target attacking or blocking creature would deal this turn. If that creature is black or red, destroy it";
    }

    public InquisitorsSnareEffect(final InquisitorsSnareEffect effect) {
        super(effect);
    }

    @Override
    public InquisitorsSnareEffect copy() {
        return new InquisitorsSnareEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent targetCreature = game.getPermanent(source.getFirstTarget());
        if (targetCreature != null) {
            FilterCreaturePermanent filter = new FilterCreaturePermanent();
            filter.add(new PermanentIdPredicate(targetCreature.getId()));
            game.addEffect(new PreventAllDamageByAllEffect(filter, Duration.EndOfTurn, false), source);
            if (targetCreature.getColor().contains(ObjectColor.BLACK)
                    || targetCreature.getColor().contains(ObjectColor.RED)) {
                return targetCreature.destroy(source.getId(), game, false);
            }
        }
        return false;
    }
}