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
package mage.cards.p;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.common.LeavesBattlefieldTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalTriggeredAbility;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ReturnFromExileForSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SetTargetPointer;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.util.CardUtil;

/**
 *
 * @author spjspj
 */
public class Portcullis extends CardImpl {

    private final static FilterCreaturePermanent filter = new FilterCreaturePermanent("a creature");

    public Portcullis(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{4}");

        // Whenever a creature enters the battlefield, if there are two or more other creatures on the battlefield, exile that creature.
        String rule = "Whenever a creature enters the battlefield, if there are two or more other creatures on the battlefield, exile that creature";
        TriggeredAbility ability = new EntersBattlefieldAllTriggeredAbility(Zone.BATTLEFIELD, new PortcullisExileEffect(), filter, false, SetTargetPointer.PERMANENT, rule);

        MoreThanXCreaturesOnBFCondition condition = new MoreThanXCreaturesOnBFCondition(2);
        this.addAbility(new ConditionalTriggeredAbility(ability, condition, rule));

        // Return that card to the battlefield under its owner's control when Portcullis leaves the battlefield.
        Ability ability2 = new LeavesBattlefieldTriggeredAbility(new ReturnFromExileForSourceEffect(Zone.BATTLEFIELD), false);
        this.addAbility(ability2);
    }

    public Portcullis(final Portcullis card) {
        super(card);
    }

    @Override
    public Portcullis copy() {
        return new Portcullis(this);
    }
}

class MoreThanXCreaturesOnBFCondition implements Condition {

    protected final int value;
    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creatures on field");

    public MoreThanXCreaturesOnBFCondition(int value) {
        this.value = value;
    }

    @Override
    public final boolean apply(Game game, Ability source) {
        PermanentsOnBattlefieldCount amount = new PermanentsOnBattlefieldCount(filter);
        int count = amount.calculate(game, source, null);
        return count > value;
    }
}

class PortcullisExileEffect extends OneShotEffect {

    public PortcullisExileEffect() {
        super(Outcome.Neutral);
        this.staticText = "Whenever a creature enters the battlefield, if there are two or more other creatures on the battlefield, exile that creature.";
    }

    public PortcullisExileEffect(final PortcullisExileEffect effect) {
        super(effect);
    }

    @Override
    public PortcullisExileEffect copy() {
        return new PortcullisExileEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        Permanent creature = game.getPermanent(targetPointer.getFirst(game, source));

        if (permanent != null && creature != null) {
            Player controller = game.getPlayer(creature.getControllerId());
            Zone currentZone = game.getState().getZone(creature.getId());
            if (currentZone.equals(Zone.BATTLEFIELD)) {
                controller.moveCardsToExile(creature, source, game, true, CardUtil.getCardExileZoneId(game, source), permanent.getIdName());
            }
        }
        return false;
    }
}
