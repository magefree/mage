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
package mage.sets.mediainserts;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreatureCard;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.AnotherPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author fireshoes
 */
public class MagisterOfWorth extends CardImpl {

    public MagisterOfWorth(UUID ownerId) {
        super(ownerId, 86, "Magister of Worth", Rarity.SPECIAL, new CardType[]{CardType.CREATURE}, "{4}{W}{B}");
        this.expansionSetCode = "MBP";
        this.subtype.add("Angel");
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Will of the council - When Magister of Worth enters the battlefield, starting with you, each player votes for grace or condemnation. If grace gets more votes, each player returns each creature card from his or her graveyard to the battlefield. If condemnation gets more votes or the vote is tied, destroy all creatures other than Magister of Worth.
        Effect effect = new MagisterOfWorthVoteEffect();
        effect.setText("Will of the council - When Magister of Worth enters the battlefield, starting with you, each player votes for grace or condemnation. If grace gets more votes, each player returns each creature card from his or her graveyard to the battlefield. If condemnation gets more votes or the vote is tied, destroy all creatures other than Magister of Worth");
        this.addAbility(new EntersBattlefieldTriggeredAbility(effect, false, true));
    }

    public MagisterOfWorth(final MagisterOfWorth card) {
        super(card);
    }

    @Override
    public MagisterOfWorth copy() {
        return new MagisterOfWorth(this);
    }
}

class MagisterOfWorthVoteEffect extends OneShotEffect {

    MagisterOfWorthVoteEffect() {
        super(Outcome.Benefit);
        this.staticText = "<i>Will of the council</i> - When {this} enters the battlefield, starting with you, each player votes for grace or condemnation. If grace gets more votes, each player returns each creature card from his or her graveyard to the battlefield. If condemnation gets more votes or the vote is tied, destroy all creatures other than {this}.";
    }

    MagisterOfWorthVoteEffect(final MagisterOfWorthVoteEffect effect) {
        super(effect);
    }

    @Override
    public MagisterOfWorthVoteEffect copy() {
        return new MagisterOfWorthVoteEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            int graceCount = 0;
            int condemnationCount = 0;
            for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                Player player = game.getPlayer(playerId);
                if (player != null) {
                    if (player.chooseUse(Outcome.DestroyPermanent, "Choose grace?", source, game)) {
                        graceCount++;
                        game.informPlayers(player.getLogName() + " has chosen: grace");
                    }
                    else {
                        condemnationCount++;
                        game.informPlayers(player.getLogName() + " has chosen: condemnation");
                    }
                }
            }
            if (graceCount > condemnationCount) {
                new MagisterOfWorthReturnFromGraveyardEffect().apply(game, source);
            } else {
                new MagisterOfWorthDestroyEffect().apply(game, source);
            }
            return true;
        }
        return false;
    }
}

class MagisterOfWorthDestroyEffect extends OneShotEffect {

    private static final FilterPermanent filter = new FilterCreaturePermanent("creatures other than {this}");
    
    static {
        filter.add(new AnotherPredicate());
    }

    public MagisterOfWorthDestroyEffect() {
        super(Outcome.DestroyPermanent);
        staticText = "destroy all creatures other than {this}";
    }

    public MagisterOfWorthDestroyEffect(final MagisterOfWorthDestroyEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (Permanent permanent : game.getBattlefield().getActivePermanents(filter, source.getControllerId(), source.getSourceId(), game)) {
            permanent.destroy(source.getSourceId(), game, false);
        }
        return true;
    }

    @Override
    public MagisterOfWorthDestroyEffect copy() {
        return new MagisterOfWorthDestroyEffect(this);
    }

}

class MagisterOfWorthReturnFromGraveyardEffect extends OneShotEffect {

    public MagisterOfWorthReturnFromGraveyardEffect() {
        super(Outcome.PutCreatureInPlay);
        staticText = "each player returns each creature card from his or her graveyard to the battlefield";
    }

    public MagisterOfWorthReturnFromGraveyardEffect(final MagisterOfWorthReturnFromGraveyardEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source.getSourceId());
        if (controller != null && sourceObject != null) {
            for (UUID playerId: controller.getInRange()) {
                Player player = game.getPlayer(playerId);
                if (player != null) {
                    for (Card card :player.getGraveyard().getCards(new FilterCreatureCard(), game)) {
                            player.putOntoBattlefieldWithInfo(card, game, Zone.GRAVEYARD, source.getSourceId());
                    }
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public MagisterOfWorthReturnFromGraveyardEffect copy() {
        return new MagisterOfWorthReturnFromGraveyardEffect(this);
    }

}