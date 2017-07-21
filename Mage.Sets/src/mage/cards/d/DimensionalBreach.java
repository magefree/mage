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
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.other.OwnerIdPredicate;
import mage.game.ExileZone;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInExile;
import mage.util.CardUtil;

/**
 *
 * @author jeffwadsworth
 */
public class DimensionalBreach extends CardImpl {

    public DimensionalBreach(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{5}{W}{W}");

        // Exile all permanents. For as long as any of those cards remain exiled, at the beginning of each player's upkeep, that player returns one of the exiled cards he or she owns to the battlefield.
        this.getSpellAbility().addEffect(new DimensionalBreachExileEffect());
        this.addAbility(new ConditionalTriggeredAbility(new BeginningOfUpkeepTriggeredAbility(Zone.ALL, new DimensionalBreachReturnFromExileEffect(), TargetController.ANY, false, true, null), new CardsStillInExileCondition(), null));

    }

    public DimensionalBreach(final DimensionalBreach card) {
        super(card);
    }

    @Override
    public DimensionalBreach copy() {
        return new DimensionalBreach(this);
    }
}

class DimensionalBreachExileEffect extends OneShotEffect {

    public DimensionalBreachExileEffect() {
        super(Outcome.Exile);
        staticText = "Exile all permanents.";
    }

    public DimensionalBreachExileEffect(final DimensionalBreachExileEffect effect) {
        super(effect);
    }

    @Override
    public DimensionalBreachExileEffect copy() {
        return new DimensionalBreachExileEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        MageObject sourceObject = source.getSourceObject(game);
        Player controller = game.getPlayer(source.getControllerId());
        if (sourceObject != null
                && controller != null) {
            UUID exileId = CardUtil.getExileZoneId(game, source.getSourceId(), 0);
            if (exileId != null) {
                game.getBattlefield().getAllActivePermanents().forEach((permanent) -> {
                    permanent.moveToExile(exileId, sourceObject.getName(), source.getSourceId(), game);
                });
                return true;
            }
        }
        return false;
    }
}

class DimensionalBreachReturnFromExileEffect extends OneShotEffect {

    public DimensionalBreachReturnFromExileEffect() {
        super(Outcome.PutCardInPlay);
        staticText = "For as long as any of those cards remain exiled, at the beginning of each player's upkeep, that player returns one of the exiled cards he or she owns to the battlefield.";
    }

    public DimensionalBreachReturnFromExileEffect(final DimensionalBreachReturnFromExileEffect effect) {
        super(effect);
    }

    @Override
    public DimensionalBreachReturnFromExileEffect copy() {
        return new DimensionalBreachReturnFromExileEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(targetPointer.getFirst(game, source));
        if (player != null) {
            FilterCard filter = new FilterCard("card you own from the Dimensional Breach exile");
            filter.add(new OwnerIdPredicate(player.getId()));

            TargetCardInExile target = new TargetCardInExile(filter, CardUtil.getExileZoneId(game, source.getSourceId(), 0));
            target.setNotTarget(true);
            
            if (target.canChoose(source.getSourceId(), player.getId(), game)) {
                if (player.chooseTarget(Outcome.PutCardInPlay, target, source, game)) {
                    Card card = game.getCard(target.getFirstTarget());
                    if (card != null
                            && player.moveCards(card, Zone.BATTLEFIELD, source, game)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}

class CardsStillInExileCondition implements Condition {

    @Override
    public final boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(game.getActivePlayerId());
        if (player != null) {
            FilterCard filter = new FilterCard();
            filter.add(new OwnerIdPredicate(player.getId()));
            UUID exileId = CardUtil.getExileZoneId(game, source.getSourceId(), 0);
            ExileZone exileZone = game.getExile().getExileZone(exileId);
            return (exileZone != null
                    && !exileZone.getCards(filter, game).isEmpty());
        }
        return false;
    }
}
