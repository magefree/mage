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
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.filter.common.FilterLandPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.SupertypePredicate;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetLandPermanent;

/**
 *
 * @author TheElk801
 */
public class FieldOfRuin extends CardImpl {

    private static final FilterLandPermanent filter = new FilterLandPermanent("nonbasic land an opponent controls");

    static {
        filter.add(new ControllerPredicate(TargetController.OPPONENT));
        filter.add(Predicates.not(new SupertypePredicate(SuperType.BASIC)));
    }

    public FieldOfRuin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // {T}: Add {C} to your mana pool.
        this.addAbility(new ColorlessManaAbility());

        // {2}, {T}, Sacrifice Field of Ruin: Destroy target nonbasic land an opponent controls. Each player searches his or her library for a basic land card, puts it onto the battlefield, then shuffles his or her library.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DestroyTargetEffect(), new TapSourceCost());
        ability.addCost(new ManaCostsImpl("{2}"));
        ability.addCost(new SacrificeSourceCost());
        ability.addEffect(new FieldOfRuinEffect());
        ability.addTarget(new TargetLandPermanent(filter));
    }

    public FieldOfRuin(final FieldOfRuin card) {
        super(card);
    }

    @Override
    public FieldOfRuin copy() {
        return new FieldOfRuin(this);
    }
}

class FieldOfRuinEffect extends OneShotEffect {

    FieldOfRuinEffect() {
        super(Outcome.Benefit);
        this.staticText = "Each player searches his or her library for a basic land card, puts it onto the battlefield, then shuffles his or her library.";
    }

    FieldOfRuinEffect(final FieldOfRuinEffect effect) {
        super(effect);
    }

    @Override
    public FieldOfRuinEffect copy() {
        return new FieldOfRuinEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                Player player = game.getPlayer(playerId);
                if (player != null) {
                    TargetCardInLibrary target = new TargetCardInLibrary(0, 1, StaticFilters.FILTER_BASIC_LAND_CARD);
                    if (player.searchLibrary(target, game)) {
                        for (UUID cardId : target.getTargets()) {
                            Card card = player.getLibrary().getCard(cardId, game);
                            if (card != null) {
                                card.putOntoBattlefield(game, Zone.LIBRARY, source.getSourceId(), player.getId());
                            }

                        }
                        player.shuffleLibrary(source, game);
                    }
                }
            }
            return true;
        }
        return false;
    }
}
