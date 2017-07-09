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
package mage.cards.g;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.SpiritWhiteToken;
import mage.players.Player;
import mage.target.common.TargetControlledPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author noxx
 */
public class GallowsAtWillowHill extends CardImpl {

    private static final FilterControlledPermanent humanFilter = new FilterControlledPermanent("untapped Human you control");

    static {
        humanFilter.add(Predicates.not(new TappedPredicate()));
        humanFilter.add(new SubtypePredicate(SubType.HUMAN));
    }

    public GallowsAtWillowHill(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{3}");

        // {3}, {tap}, Tap three untapped Humans you control: Destroy target creature. Its controller creates a 1/1 white Spirit creature token with flying.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new GallowsAtWillowHillEffect(), new GenericManaCost(3));
        ability.addCost(new TapSourceCost());
        ability.addCost(new TapTargetCost(new TargetControlledPermanent(3, 3, humanFilter, false)));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    public GallowsAtWillowHill(final GallowsAtWillowHill card) {
        super(card);
    }

    @Override
    public GallowsAtWillowHill copy() {
        return new GallowsAtWillowHill(this);
    }
}

class GallowsAtWillowHillEffect extends OneShotEffect {

    public GallowsAtWillowHillEffect() {
        super(Outcome.DestroyPermanent);
        staticText = "Destroy target creature. Its controller creates a 1/1 white Spirit creature token with flying";
    }

    public GallowsAtWillowHillEffect(final GallowsAtWillowHillEffect effect) {
        super(effect);
    }

    @Override
    public GallowsAtWillowHillEffect copy() {
        return new GallowsAtWillowHillEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int affectedTargets = 0;
        if (!targetPointer.getTargets(game, source).isEmpty()) {
            for (UUID permanentId : targetPointer.getTargets(game, source)) {
                Permanent permanent = game.getPermanent(permanentId);
                if (permanent != null) {
                    Player controller = game.getPlayer(permanent.getControllerId());
                    permanent.destroy(source.getSourceId(), game, false);
                    if (controller != null) {
                        new CreateTokenEffect(new SpiritWhiteToken()).apply(game, source);
                    }
                    affectedTargets++;
                }
            }
        }
        return affectedTargets > 0;
    }
}
