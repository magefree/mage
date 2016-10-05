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
package mage.cards.d;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.permanent.AttachedToPredicate;
import mage.filter.predicate.permanent.PermanentIdPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Derpthemeus
 */
public class Disarm extends CardImpl {

    public Disarm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{U}");

        // Unattach all Equipment from target creature.
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addEffect(new DisarmEffect());
    }

    public Disarm(final Disarm card) {
        super(card);
    }

    @Override
    public Disarm copy() {
        return new Disarm(this);
    }

    class DisarmEffect extends OneShotEffect {

        public DisarmEffect() {
            super(Outcome.UnboostCreature);
            this.staticText = "Unattach all Equipment from target creature";
        }

        public DisarmEffect(final DisarmEffect effect) {
            super(effect);
        }

        @Override
        public DisarmEffect copy() {
            return new DisarmEffect(this);
        }

        @Override
        public boolean apply(Game game, Ability source) {
            Permanent creature = game.getPermanent(targetPointer.getFirst(game, source));
            if (creature != null) {
                FilterPermanent creatureFilter = new FilterPermanent();
                creatureFilter.add(new PermanentIdPredicate(creature.getId()));

                FilterPermanent equipmentFilter = new FilterPermanent();
                equipmentFilter.add(new AttachedToPredicate(creatureFilter));
                equipmentFilter.add(new SubtypePredicate("Equipment"));

                for (Permanent equipment : game.getBattlefield().getAllActivePermanents(equipmentFilter, game)) {
                    creature.removeAttachment(equipment.getId(), game);
                }
                return true;
            }
            return false;
        }
    }
}
