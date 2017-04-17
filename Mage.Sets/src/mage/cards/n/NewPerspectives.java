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
package mage.cards.n;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.CardsInHandCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalReplacementEffect;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.CostEvent;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public class NewPerspectives extends CardImpl {

    public NewPerspectives(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{5}{U}");

        // When New Perspectives enters the battlefield, draw three cards.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new DrawCardSourceControllerEffect(3), false));

        // As long as you have seven or more cards in hand, you may pay {0} rather than pay cycling costs.
        Condition condition = new CardsInHandCondition(ComparisonType.MORE_THAN, 6);
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD,
                new ConditionalReplacementEffect(
                        new PerspectivesReplaceCylcingCosts(), condition));
        this.addAbility(ability);
    }

    public NewPerspectives(final NewPerspectives card) {
        super(card);
    }

    @Override
    public NewPerspectives copy() {
        return new NewPerspectives(this);
    }
}

class PerspectivesReplaceCylcingCosts extends ReplacementEffectImpl {

    public PerspectivesReplaceCylcingCosts() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "As long as you have seven or more cards in hand, you may pay {0} rather than pay cycling costs";
    }

    public PerspectivesReplaceCylcingCosts(final PerspectivesReplaceCylcingCosts effect) {
        super(effect);
    }

    @Override
    public PerspectivesReplaceCylcingCosts copy() {
        return new PerspectivesReplaceCylcingCosts(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        if (event.getType() == EventType.CAN_PAY_CYCLE_COST) {
            ((CostEvent) event).setCost(new ManaCostsImpl<>("{0}"));
            return false;
        }
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null && controller.chooseUse(outcome, "Pay {0} rather than normal cycling costs?", source, game)) {
            ((CostEvent) event).setCost(new ManaCostsImpl<>("{0}"));
        }
        return false;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == EventType.PAY_CYCLE_COST || event.getType() == EventType.CAN_PAY_CYCLE_COST;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return event.getPlayerId().equals(source.getControllerId());
    }

}
