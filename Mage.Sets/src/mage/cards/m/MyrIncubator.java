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
package mage.cards.m;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.SearchEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.Game;
import mage.game.permanent.token.MyrToken;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author jeffwadsworth
 */
public class MyrIncubator extends CardImpl {

    public MyrIncubator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{6}");

        // {6}, {tap}, Sacrifice Myr Incubator: Search your library for any number of artifact cards, exile them, then put that many 1/1 colorless Myr artifact creature tokens onto the battlefield. Then shuffle your library.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new MyrIncubatorEffect(), new ManaCostsImpl("{6}"));
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);

    }

    public MyrIncubator(final MyrIncubator card) {
        super(card);
    }

    @Override
    public MyrIncubator copy() {
        return new MyrIncubator(this);
    }
}

class MyrIncubatorEffect extends SearchEffect {

    private static final FilterCard filter = new FilterCard();

    static {
        filter.add(new CardTypePredicate(CardType.ARTIFACT));
    }
    
    int tokensToCreate = 0;

    MyrIncubatorEffect() {
        super(new TargetCardInLibrary(0, Integer.MAX_VALUE, filter), Outcome.Neutral);
        staticText = "Search your library for any number of artifact cards, exile them, then put that many 1/1 colorless Myr artifact creature tokens onto the battlefield. Then shuffle your library";
    }

    MyrIncubatorEffect(final MyrIncubatorEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null 
                && controller.searchLibrary(target, game)) {
            if (!target.getTargets().isEmpty()) {
                tokensToCreate = target.getTargets().size();
                controller.moveCards(new CardsImpl(target.getTargets()), Zone.EXILED, source, game);
            }
            CreateTokenEffect effect = new CreateTokenEffect(new MyrToken(), tokensToCreate);
            effect.apply(game, source);
            controller.shuffleLibrary(source, game);
            return true;
        }
        return false;
    }

    @Override
    public MyrIncubatorEffect copy() {
        return new MyrIncubatorEffect(this);
    }
}
