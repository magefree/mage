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
package mage.cards.b;

import java.util.Set;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.IntCompareCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalActivatedAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetPlayer;
import mage.target.common.TargetOpponent;

/**
 *
 * @author spjspj
 */
public class BuzzingWhackADoodle extends CardImpl {

    public BuzzingWhackADoodle(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}");

        // As Buzzing Whack-a-Doodle enters the battlefield, you and an opponent each secretly choose Whack or Doodle. Then those choices are revealed. If the choices match, Buzzing Whack-a-Doodle has that ability. Otherwise it has Buzz.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new BuzzingWhackADoodleEffect(), false));

        // *Whack - T: Target player loses 2 life.
        Ability ability = new ConditionalActivatedAbility(Zone.BATTLEFIELD, new LoseLifeTargetEffect(2), new TapSourceCost(), new WhackCondition());
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);

        // *Doodle - T: You gain 3 life.
        Ability ability2 = new ConditionalActivatedAbility(Zone.BATTLEFIELD, new GainLifeEffect(3), new TapSourceCost(), new DoodleCondition());
        this.addAbility(ability2);

        // *Buzz - 2, T: Draw a card.
        Ability ability3 = new ConditionalActivatedAbility(Zone.BATTLEFIELD, new DrawCardSourceControllerEffect(1), new ManaCostsImpl("{2}"), new BuzzCondition());
        ability3.addCost(new TapSourceCost());
        this.addAbility(ability3);
    }

    public BuzzingWhackADoodle(final BuzzingWhackADoodle card) {
        super(card);
    }

    @Override
    public BuzzingWhackADoodle copy() {
        return new BuzzingWhackADoodle(this);
    }
}

class BuzzingWhackADoodleEffect extends OneShotEffect {

    BuzzingWhackADoodleEffect() {
        super(Outcome.Benefit);
        this.staticText = "You and an opponent each secretly choose Whack or Doodle. Then those choices are revealed. If the choices match, {this} has that ability. Otherwise it has Buzz";
    }

    BuzzingWhackADoodleEffect(final BuzzingWhackADoodleEffect effect) {
        super(effect);
    }

    @Override
    public BuzzingWhackADoodleEffect copy() {
        return new BuzzingWhackADoodleEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            int whackCount = 0;
            int doodleCount = 0;

            if (controller.chooseUse(Outcome.Benefit, "Choose Whack (yes) or Doodle (no)?", source, game)) {
                whackCount++;
            } else {
                doodleCount++;
            }

            Set<UUID> opponents = game.getOpponents(source.getControllerId());
            if (!opponents.isEmpty()) {
                Player opponent = game.getPlayer(opponents.iterator().next());
                if (opponents.size() > 1) {
                    Target targetOpponent = new TargetOpponent(true);
                    if (controller.chooseTarget(Outcome.Neutral, targetOpponent, source, game)) {
                        opponent = game.getPlayer(targetOpponent.getFirstTarget());
                        game.informPlayers(controller.getLogName() + " chose " + opponent.getLogName() + " to choose Whack or Doodle");
                    }
                }

                if (opponent != null) {
                    if (opponent.chooseUse(Outcome.Benefit, "Choose Whack (yes) or Doodle (no)?", source, game)) {
                        whackCount++;
                    } else {
                        doodleCount++;
                    }
                }
            }

            if (whackCount == 2) {
                game.informPlayers("Whack was chosen");
                game.getState().setValue("whack" + source.getSourceId(), Boolean.TRUE);
            } else if (doodleCount == 2) {
                game.informPlayers("Doodle was chosen");
                game.getState().setValue("doodle" + source.getSourceId(), Boolean.TRUE);
            } else {
                game.informPlayers("Buzz was chosen");
                game.getState().setValue("buzz" + source.getSourceId(), Boolean.TRUE);
            }
            return true;
        }
        return false;
    }
}

class WhackCondition extends IntCompareCondition {

    WhackCondition() {
        super(ComparisonType.MORE_THAN, 0);
    }

    @Override
    protected int getInputValue(Game game, Ability source) {
        Object object = game.getState().getValue("whack" + source.getSourceId());
        if (object != null && object instanceof Boolean && (Boolean) object) {
            return 1;
        }
        return 0;
    }

    @Override
    public String toString() {
        return "if both players picked 'Whack'";
    }
}

class DoodleCondition extends IntCompareCondition {

    DoodleCondition() {
        super(ComparisonType.MORE_THAN, 0);
    }

    @Override
    protected int getInputValue(Game game, Ability source) {
        Object object = game.getState().getValue("doodle" + source.getSourceId());
        if (object != null && object instanceof Boolean && (Boolean) object) {
            return 1;
        }
        return 0;
    }

    @Override
    public String toString() {
        return "if both players picked 'Doodle'";
    }
}

class BuzzCondition extends IntCompareCondition {

    BuzzCondition() {
        super(ComparisonType.MORE_THAN, 0);
    }

    @Override
    protected int getInputValue(Game game, Ability source) {
        Object object = game.getState().getValue("buzz" + source.getSourceId());
        if (object != null && object instanceof Boolean && (Boolean) object) {
            return 1;
        }
        return 0;
    }

    @Override
    public String toString() {
        return "if both players picked differently";
    }
}
