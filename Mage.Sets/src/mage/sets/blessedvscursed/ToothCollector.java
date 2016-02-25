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
package mage.sets.blessedvscursed;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.DeliriumCondition;
import mage.abilities.decorator.ConditionalTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Rarity;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author fireshoes
 */
public class ToothCollector extends CardImpl {

    private static final FilterCreaturePermanent FILTER = new FilterCreaturePermanent("creature an opponent controls");

    static {
        FILTER.add(new ControllerPredicate(TargetController.OPPONENT));
    }

    public ToothCollector(UUID ownerId) {
        super(ownerId, 64, "Tooth Collector", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{2}{B}");
        this.expansionSetCode = "DDQ";
        this.subtype.add("Human");
        this.subtype.add("Rogue");
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // When Tooth Collector enters the battlefield, target creature an opponent controls gets -1/-1 until end of turn.
        Ability ability = new EntersBattlefieldTriggeredAbility(new BoostTargetEffect(-1, -1, Duration.EndOfTurn));
        ability.addTarget(new TargetCreaturePermanent(FILTER));
        this.addAbility(ability);

        // {<i>Delirium</i> &mdash; At the beginning of each opponent's upkeep, if there are four or more card types among cards in your graveyard,
        // target creature that player controls gets -1/-1 until end of turn.
        ability = new ConditionalTriggeredAbility(
                new ToothCollectorAbility(),
                new DeliriumCondition(),
                "<i>Delirium</i> &mdash; At the beginning of each opponent's upkeep, if there are four or more card types among cards in your graveyard, "
                        + "target creature that player controls gets -1/-1 until end of turn.");
        this.addAbility(ability);
    }

    public ToothCollector(final ToothCollector card) {
        super(card);
    }

    @Override
    public ToothCollector copy() {
        return new ToothCollector(this);
    }
}

class ToothCollectorAbility extends TriggeredAbilityImpl {

    public ToothCollectorAbility() {
        super(Zone.BATTLEFIELD, new BoostTargetEffect(-1, -1, Duration.EndOfTurn));
    }

    public ToothCollectorAbility(final ToothCollectorAbility ability) {
        super(ability);
    }

    @Override
    public ToothCollectorAbility copy() {
        return new ToothCollectorAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.UPKEEP_STEP_PRE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (game.getOpponents(controllerId).contains(event.getPlayerId())) {
            Player opponent = game.getPlayer(event.getPlayerId());
            if (opponent != null) {
                FilterCreaturePermanent FILTER = new FilterCreaturePermanent("creature " + opponent.getLogName() + " controls");
                FILTER.add(new ControllerIdPredicate(opponent.getId()));
                this.getTargets().clear();
                this.addTarget(new TargetCreaturePermanent(FILTER));
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "<i>Delirium</i> &mdash; At the beginning of each opponent's upkeep, if there are four or more card types among cards in your graveyard, "
                + "target creature that player controls gets -1/-1 until end of turn.";
    }
}
