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
package mage.cards.o;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayTargetPlayerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.filter.FilterPlayer;
import mage.filter.StaticFilters;
import mage.filter.common.FilterLandPermanent;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.common.TargetCardInLibrary;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 *
 * @author emerald000
 */
public class OathOfLieges extends CardImpl {

    private final UUID originalId;
    private static final FilterPlayer FILTER = new FilterPlayer("player who controls more lands than you do and is your opponent");

    static {
        FILTER.add(new OathOfLiegesPredicate());
    }

    public OathOfLieges(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{W}");

        // At the beginning of each player's upkeep, that player chooses target player who controls more lands than he or she does and is his or her opponent. The first player may search his or her library for a basic land card, put that card onto the battlefield, then shuffle his or her library.
        Ability ability = new BeginningOfUpkeepTriggeredAbility(new OathOfLiegesEffect(), TargetController.ANY, false);
        ability.addTarget(new TargetPlayer(1, 1, false, FILTER));
        originalId = ability.getOriginalId();
        this.addAbility(ability);
    }

    @Override
    public void adjustTargets(Ability ability, Game game) {
        if (ability.getOriginalId().equals(originalId)) {
            Player activePlayer = game.getPlayer(game.getActivePlayerId());
            if (activePlayer != null) {
                ability.getTargets().clear();
                TargetPlayer target = new TargetPlayer(1, 1, false, FILTER);
                target.setTargetController(activePlayer.getId());
                ability.getTargets().add(target);
            }
        }
    }

    public OathOfLieges(final OathOfLieges card) {
        super(card);
        this.originalId = card.originalId;
    }

    @Override
    public OathOfLieges copy() {
        return new OathOfLieges(this);
    }
}

class OathOfLiegesEffect extends OneShotEffect {

    public OathOfLiegesEffect() {
        super(Outcome.Benefit);
        this.staticText = "that player chooses target player who controls more lands than he or she does and is his or her opponent. "
                + "The first player may search his or her library for a basic land card, put that card onto the battlefield, then shuffle his or her library";
    }

    public OathOfLiegesEffect(final OathOfLiegesEffect effect) {
        super(effect);
    }

    @Override
    public OathOfLiegesEffect copy() {
        return new OathOfLiegesEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player activePlayer = game.getPlayer(game.getActivePlayerId());
        if (activePlayer != null) {
            if (activePlayer.chooseUse(outcome, "Search your library for a basic land card, put that card onto the battlefield, then shuffle your library?", source, game)) {
                Effect effect = new SearchLibraryPutInPlayTargetPlayerEffect(new TargetCardInLibrary(StaticFilters.FILTER_BASIC_LAND_CARD), false, true, Outcome.PutLandInPlay, true);
                effect.setTargetPointer(new FixedTarget(game.getActivePlayerId()));
                return effect.apply(game, source);
            }
            return true;
        }

        return false;
    }
}

class OathOfLiegesPredicate implements ObjectSourcePlayerPredicate<ObjectSourcePlayer<Player>> {

    private static final FilterLandPermanent FILTER = new FilterLandPermanent();

    @Override
    public boolean apply(ObjectSourcePlayer<Player> input, Game game) {
        Player targetPlayer = input.getObject();
        //Get active input.playerId because adjust target is used after canTarget function
        UUID activePlayerId = game.getActivePlayerId();
        if (targetPlayer == null || activePlayerId == null) {
            return false;
        }
        if (!targetPlayer.hasOpponent(activePlayerId, game)) {
            return false;
        }
        int countTargetPlayer = game.getBattlefield().countAll(FILTER, targetPlayer.getId(), game);
        int countActivePlayer = game.getBattlefield().countAll(FILTER, activePlayerId, game);

        return countTargetPlayer > countActivePlayer;
    }

    @Override
    public String toString() {
        return "player who controls more lands than he or she does and is his or her opponent";
    }
}
