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
package mage.sets.magic2014;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalContinousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continious.BoostAllEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;

/**
 *
 * @author jeffwadsworth
 */
public class PathOfBravery extends CardImpl<PathOfBravery> {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creatures you control");

    static {
        filter.add(new ControllerPredicate(TargetController.YOU));
    }
    final String rule = "As long as your life total is greater than or equal to your starting life total, creatures you control get +1/+1";

    public PathOfBravery(UUID ownerId) {
        super(ownerId, 26, "Path of Bravery", Rarity.RARE, new CardType[]{CardType.ENCHANTMENT}, "{2}{W}");
        this.expansionSetCode = "M14";

        this.color.setWhite(true);

        // As long as your life total is greater than or equal to your starting life total, creatures you control get +1/+1.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new ConditionalContinousEffect(new BoostAllEffect(1, 1, Duration.WhileOnBattlefield, filter, true), new LifeCondition(), rule)));

        // Whenever one or more creatures you control attack, you gain life equal to the number of attacking creatures.
        this.addAbility(new PathOfBraveryTriggeredAbility());

    }

    public PathOfBravery(final PathOfBravery card) {
        super(card);
    }

    @Override
    public PathOfBravery copy() {
        return new PathOfBravery(this);
    }
}

class LifeCondition implements Condition {

    private static LifeCondition fInstance = new LifeCondition();

    public static Condition getInstance() {
        return fInstance;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player you = game.getPlayer(source.getControllerId());
        if (you != null) {
            return you.getLife() >= game.getLife();
        }
        return false;
    }
}

class PathOfBraveryTriggeredAbility extends TriggeredAbilityImpl<PathOfBraveryTriggeredAbility> {

    public PathOfBraveryTriggeredAbility() {
        super(Zone.BATTLEFIELD, new PathOfBraveryEffect(), false);
    }

    public PathOfBraveryTriggeredAbility(final PathOfBraveryTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.DECLARED_ATTACKERS
                && !game.getCombat().noAttackers()
                && event.getPlayerId().equals(controllerId)) {
            return true;
        }
        return false;
    }

    @Override
    public PathOfBraveryTriggeredAbility copy() {
        return new PathOfBraveryTriggeredAbility(this);
    }

    @Override
    public String getRule() {
        return "Whenever one or more creatures you control attack, " + super.getRule();
    }
}

class PathOfBraveryEffect extends OneShotEffect<PathOfBraveryEffect> {

    private int attackers;

    public PathOfBraveryEffect() {
        super(Outcome.GainLife);
        staticText = "you gain life equal to the number of attacking creatures";
    }

    public PathOfBraveryEffect(final PathOfBraveryEffect effect) {
        super(effect);
    }

    @Override
    public PathOfBraveryEffect copy() {
        return new PathOfBraveryEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player you = game.getPlayer(source.getControllerId());
        attackers = game.getCombat().getAttackers().size();
        if (you != null) {
            you.gainLife(attackers, game);
            attackers = 0;
            return true;
        }
        return false;
    }
}
