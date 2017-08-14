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
package mage.cards.k;

import java.util.UUID;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ChooseCreatureTypeEffect;
import mage.abilities.effects.common.DestroyAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author caldover
 */
public class KindredDominance extends CardImpl {

    public KindredDominance(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{5}{B}{B}");

        // Choose a creature type. Destroy all creatures that are not the chosen type.
        this.getSpellAbility().addEffect(new ChooseCreatureTypeEffect(Outcome.DestroyPermanent));
        this.getSpellAbility().addEffect(new KindredDominanceEffect());
    }

    public KindredDominance(final KindredDominance card) {
        super(card);
    }

    @Override
    public KindredDominance copy() {
        return new KindredDominance(this);
    }
}

class KindredDominanceEffect extends OneShotEffect {

    public KindredDominanceEffect() {
        super(Outcome.DestroyPermanent);
        this.staticText = " Destroy all creatures that are not the chosen type.";
    }

    public KindredDominanceEffect(final KindredDominanceEffect effect) {
        super(effect);
    }

    @Override
    public KindredDominanceEffect copy() {
        return new KindredDominanceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject mageObject = game.getObject(source.getSourceId());
        if (controller != null & mageObject != null) {
            String creatureType = game.getState().getValue(mageObject.getId() + "_type").toString();
            FilterPermanent filter = new FilterCreaturePermanent("creatures");
            filter.add(Predicates.not(new SubtypePredicate(SubType.byDescription(creatureType))));
            return new DestroyAllEffect(filter).apply(game, source);
        }
        return false;
    }
}