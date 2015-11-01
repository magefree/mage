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
package mage.sets.judgment;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.LeavesBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.predicate.permanent.AnotherPredicate;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.game.ExileZone;
import mage.game.Game;
import mage.game.permanent.PermanentToken;
import mage.players.Player;
import mage.util.CardUtil;

/**
 *
 * @author LevelX2
 */
public class WorldgorgerDragon extends CardImpl {

    public WorldgorgerDragon(UUID ownerId) {
        super(ownerId, 103, "Worldgorger Dragon", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{3}{R}{R}{R}");
        this.expansionSetCode = "JUD";
        this.subtype.add("Nightmare");
        this.subtype.add("Dragon");

        this.power = new MageInt(7);
        this.toughness = new MageInt(7);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Trample
        this.addAbility(TrampleAbility.getInstance());
        // When Worldgorger Dragon enters the battlefield, exile all other permanents you control.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new WorldgorgerDragonEntersEffect(), false));

        // When Worldgorger Dragon leaves the battlefield, return the exiled cards to the battlefield under their owners' control.
        this.addAbility(new LeavesBattlefieldTriggeredAbility(new WorldgorgerDragonLeavesEffect(), false));
    }

    public WorldgorgerDragon(final WorldgorgerDragon card) {
        super(card);
    }

    @Override
    public WorldgorgerDragon copy() {
        return new WorldgorgerDragon(this);
    }
}

class WorldgorgerDragonEntersEffect extends OneShotEffect {

    private static final FilterPermanent filter = new FilterPermanent("all other permanents you control");

    static {
        filter.add(new ControllerPredicate(TargetController.YOU));
        filter.add(new AnotherPredicate());
    }

    public WorldgorgerDragonEntersEffect() {
        super(Outcome.Detriment);
        staticText = "exile all other permanents you control";
    }

    public WorldgorgerDragonEntersEffect(final WorldgorgerDragonEntersEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = source.getSourceObject(game);
        if (controller != null) {
            UUID exileId = CardUtil.getExileZoneId(game, source.getSourceId(), source.getSourceObjectZoneChangeCounter());
            if (exileId != null) {
                Set<Card> cardsToExile = new LinkedHashSet<>();
                cardsToExile.addAll(game.getBattlefield().getActivePermanents(filter, source.getControllerId(), source.getSourceId(), game));
                controller.moveCardsToExile(cardsToExile, source, game, true, exileId, sourceObject.getIdName());
                return true;
            }
        }
        return false;
    }

    @Override
    public WorldgorgerDragonEntersEffect copy() {
        return new WorldgorgerDragonEntersEffect(this);
    }
}

class WorldgorgerDragonLeavesEffect extends OneShotEffect {

    public WorldgorgerDragonLeavesEffect() {
        super(Outcome.Neutral);
        staticText = "return the exiled cards to the battlefield under their owners' control";
    }

    public WorldgorgerDragonLeavesEffect(final WorldgorgerDragonLeavesEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = source.getSourceObject(game);
        if (controller != null && sourceObject != null) {
            int zoneChangeCounter = (sourceObject instanceof PermanentToken) ? source.getSourceObjectZoneChangeCounter() : source.getSourceObjectZoneChangeCounter() - 1;
            ExileZone exile = game.getExile().getExileZone(CardUtil.getExileZoneId(game, source.getSourceId(), zoneChangeCounter));
            if (exile != null) {
                return controller.moveCards(exile.getCards(game), Zone.BATTLEFIELD, source, game, false, false, true, null);
            }
        }
        return false;

    }

    @Override
    public WorldgorgerDragonLeavesEffect copy() {
        return new WorldgorgerDragonLeavesEffect(this);
    }
}
