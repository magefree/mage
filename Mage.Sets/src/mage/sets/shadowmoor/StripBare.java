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
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author jeffwadsworth
 *
 */
public class StripBare extends CardImpl<StripBare> {

    public StripBare(UUID ownerId) {
        super(ownerId, 24, "Strip Bare", Rarity.COMMON, new CardType[]{CardType.INSTANT}, "{W}");
        this.expansionSetCode = "SHM";

        this.color.setWhite(true);

        // Destroy all Auras and Equipment attached to target creature.
        this.getSpellAbility().addEffect(new StripBareEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(true));

    }

    public StripBare(final StripBare card) {
        super(card);
    }

    @Override
    public StripBare copy() {
        return new StripBare(this);
    }
}

class StripBareEffect extends OneShotEffect<StripBareEffect> {

    public StripBareEffect() {
        super(Outcome.GainLife);
        this.staticText = "Destroy all Auras and Equipment attached to target creature";
    }

    public StripBareEffect(final StripBareEffect effect) {
        super(effect);
    }

    @Override
    public StripBareEffect copy() {
        return new StripBareEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        boolean applied = false;
        FilterPermanent filter = new FilterPermanent();
        filter.add(Predicates.or(new SubtypePredicate("Equipment"),
                new SubtypePredicate("Aura")));
        Permanent targetCreature = game.getPermanent(source.getFirstTarget());
        if (targetCreature != null
                && targetCreature.getAttachments().size() > 0) {
            for (Permanent attachment : game.getBattlefield().getAllActivePermanents(filter, game)) {
                if (attachment != null
                        && targetCreature.getAttachments().contains(attachment.getId())) {
                    applied = attachment.destroy(source.getId(), game, false);
                }
            }
        }
        return applied;
    }
}