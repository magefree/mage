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
package mage.sets.scourge;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.LeavesBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.game.ExileZone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.PermanentToken;
import mage.game.permanent.token.DragonToken2;
import mage.players.Player;
import mage.util.CardUtil;

/**
 *
 * @author jeffwadsworth
 */
public class DayOfTheDragons extends CardImpl {

    public DayOfTheDragons(UUID ownerId) {
        super(ownerId, 31, "Day of the Dragons", Rarity.RARE, new CardType[]{CardType.ENCHANTMENT}, "{4}{U}{U}{U}");
        this.expansionSetCode = "SCG";

        // When Day of the Dragons enters the battlefield, exile all creatures you control. Then put that many 5/5 red Dragon creature tokens with flying onto the battlefield.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new DayOfTheDragonsEntersEffect(), false));

        // When Day of the Dragons leaves the battlefield, sacrifice all Dragons you control. Then return the exiled cards to the battlefield under your control.
        this.addAbility(new LeavesBattlefieldTriggeredAbility(new DayOfTheDragonsLeavesEffect(), false));
    }

    public DayOfTheDragons(final DayOfTheDragons card) {
        super(card);
    }

    @Override
    public DayOfTheDragons copy() {
        return new DayOfTheDragons(this);
    }
}

class DayOfTheDragonsEntersEffect extends OneShotEffect {

    private static final FilterPermanent filter = new FilterPermanent("all creatures you control");

    static {
        filter.add(new ControllerPredicate(TargetController.YOU));
        filter.add(new CardTypePredicate(CardType.CREATURE));
    }

    public DayOfTheDragonsEntersEffect() {
        super(Outcome.Benefit);
        staticText = "exile all creatures you control. Then put that many 5/5 red Dragon creature tokens with flying onto the battlefield";
    }

    public DayOfTheDragonsEntersEffect(final DayOfTheDragonsEntersEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source.getSourceId());
        if (controller != null && sourceObject != null) {
            Set<Card> toExile = new HashSet<>();
            toExile.addAll(game.getBattlefield().getAllActivePermanents(filter, source.getControllerId(), game));
            if (!toExile.isEmpty()) {
                UUID exileId = CardUtil.getExileZoneId(game, source.getSourceId(), source.getSourceObjectZoneChangeCounter());
                controller.moveCardsToExile(toExile, source, game, true, exileId, sourceObject.getIdName());
                DragonToken2 token = new DragonToken2();
                token.putOntoBattlefield(toExile.size(), game, source.getSourceId(), source.getControllerId());
            }
            return true;
        }
        return false;
    }

    @Override
    public DayOfTheDragonsEntersEffect copy() {
        return new DayOfTheDragonsEntersEffect(this);
    }
}

class DayOfTheDragonsLeavesEffect extends OneShotEffect {

    private static final FilterPermanent filter = new FilterPermanent("all Dragons you control");

    static {
        filter.add(new ControllerPredicate(TargetController.YOU));
        filter.add(new SubtypePredicate("Dragon"));
    }

    public DayOfTheDragonsLeavesEffect() {
        super(Outcome.Neutral);
        staticText = "sacrifice all Dragons you control. Then return the exiled cards to the battlefield under your control";
    }

    public DayOfTheDragonsLeavesEffect(final DayOfTheDragonsLeavesEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = source.getSourceObject(game);
        if (controller != null) {
            for (Permanent dragon : game.getBattlefield().getActivePermanents(filter, source.getControllerId(), game)) {
                if (dragon != null) {
                    dragon.sacrifice(source.getSourceId(), game);
                }
            }
            int zoneChangeCounter = source.getSourceObjectZoneChangeCounter();
            if (zoneChangeCounter > 0 && !(sourceObject instanceof PermanentToken)) {
                zoneChangeCounter--;
            }
            ExileZone exile = game.getExile().getExileZone(CardUtil.getExileZoneId(game, source.getSourceId(), zoneChangeCounter));
            if (exile != null) {
                controller.moveCards(exile, Zone.BATTLEFIELD, source, game);
            }
            return true;
        }
        return false;
    }

    @Override
    public DayOfTheDragonsLeavesEffect copy() {
        return new DayOfTheDragonsLeavesEffect(this);
    }
}
