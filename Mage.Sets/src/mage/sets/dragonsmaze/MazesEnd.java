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

package mage.sets.dragonsmaze;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.ReturnToHandSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author LevelX2
 */

public class MazesEnd extends CardImpl<MazesEnd> {

    private static final FilterCard filterCard = new FilterCard("Gate card");
    static {
        filterCard.add(new SubtypePredicate("Gate"));
    }

    public MazesEnd(UUID ownerId) {
        super(ownerId, 152, "Maze's End", Rarity.MYTHIC, new CardType[]{CardType.LAND}, "");
        this.expansionSetCode = "DGM";


        // Maze's End enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());

        // {T}: Add 1 to your mana pool.
        this.addAbility(new ColorlessManaAbility());

        // 3, {T}, Return Maze's End to your hand: Search your library for a Gate card, put it onto the battlefield, then shuffle your library. If you control ten or more Gates with different names, you win the game.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new SearchLibraryPutInPlayEffect(new TargetCardInLibrary(filterCard)), new GenericManaCost(3));
        ability.addEffect(new MazesEndEffect());
        ability.addCost(new TapSourceCost());
        ability.addCost(new ReturnToHandSourceCost());
        this.addAbility(ability);

    }

    public MazesEnd(final MazesEnd card) {
        super(card);
    }

    @Override
    public MazesEnd copy() {
        return new MazesEnd(this);
    }
}

class MazesEndEffect extends OneShotEffect<MazesEndEffect> {

    public MazesEndEffect() {
        super(Outcome.PutLandInPlay);
        this.staticText = "If you control ten or more Gates with different names, you win the game";
    }

    public MazesEndEffect(final MazesEndEffect effect) {
        super(effect);
    }

    @Override
    public MazesEndEffect copy() {
        return new MazesEndEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        List<String> names = new ArrayList<String>();
        for (Permanent permanent : game.getBattlefield().getActivePermanents(source.getControllerId(), game)) {
            if (permanent.hasSubtype("Gate")) {
                if (!names.contains(permanent.getName())) {
                    names.add(permanent.getName());
                }
            }
        }
        if (names.size() >= 10) {
            Player controller = game.getPlayer(source.getControllerId());
            if (controller != null) {
                controller.won(game);
            }
        }
        return false;
    }

}
