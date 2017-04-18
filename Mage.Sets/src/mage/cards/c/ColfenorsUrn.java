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
package mage.cards.c;

import mage.MageObject;
import mage.abilities.Ability;
import mage.constants.ComparisonType;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileTargetForSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.ToughnessPredicate;
import mage.game.ExileZone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.UUID;

/**
 *
 * @author Styxo
 */
public class ColfenorsUrn extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("a creature with toughness 4 or greater");

    static {
        filter.add(new ToughnessPredicate(ComparisonType.MORE_THAN, 3));
    }

    public ColfenorsUrn(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        // Whenever a creature with toughness 4 or greater is put into your graveyard from the battlefield, you may exile it.
        this.addAbility(new DiesCreatureTriggeredAbility(new ExileTargetForSourceEffect(), true, filter, true));

        // At the beginning of the end step, if three or more cards have been exiled with Colfenor's Urn, sacrifice it. If you do, return those cards to the battlefield under their owner's control.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(Zone.BATTLEFIELD, new ColfenorsUrnEffect(), TargetController.ANY, new ColfenorsUrnCondition(), false));
    }

    public ColfenorsUrn(final ColfenorsUrn card) {
        super(card);
    }

    @Override
    public ColfenorsUrn copy() {
        return new ColfenorsUrn(this);
    }
}

class ColfenorsUrnEffect extends OneShotEffect {

    public ColfenorsUrnEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "If you do, return those cards to the battlefield under their owner's control";
    }

    public ColfenorsUrnEffect(final ColfenorsUrnEffect effect) {
        super(effect);
    }

    @Override
    public ColfenorsUrnEffect copy() {
        return new ColfenorsUrnEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent permanent = (Permanent) source.getSourceObjectIfItStillExists(game);
        if (controller != null && permanent != null) {
            UUID exileId = CardUtil.getCardExileZoneId(game, source);
            ExileZone exile = game.getExile().getExileZone(exileId);
            if (permanent.sacrifice(source.getSourceId(), game)) {
                controller.moveCards(exile.getCards(game), Zone.BATTLEFIELD, source, game);
            }
            return true;

        }
        return false;
    }
}

class ColfenorsUrnCondition implements Condition {

    @Override
    public final boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source.getSourceId());
        if (controller != null && sourceObject != null) {
            UUID exileId = CardUtil.getCardExileZoneId(game, source);
            ExileZone exile = game.getExile().getExileZone(exileId);
            if (exile != null) {
                return exile.size() > 2;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "if three or more cards have been exiled with Colfenor's Urn, sacrifice it.";
    }
}
