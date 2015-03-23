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
package mage.sets.fatereforged;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.abilities.effects.common.combat.BlocksIfAbleAllEffect;
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
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author LevelX2
 */
public class BrutalHordechief extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Creature your opponents control");

    static {
        filter.add(new ControllerPredicate(TargetController.OPPONENT));
    }

    public BrutalHordechief(UUID ownerId) {
        super(ownerId, 64, "Brutal Hordechief", Rarity.MYTHIC, new CardType[]{CardType.CREATURE}, "{3}{B}");
        this.expansionSetCode = "FRF";
        this.subtype.add("Orc");
        this.subtype.add("Warrior");
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever a creature you control attacks, defending player loses 1 life and you gain 1 life.
        this.addAbility(new BrutalHordechiefTriggeredAbility());

        // {3}{R/W}{R/W}: Creatures your opponents control block this turn if able, and you choose how those creatures block.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new BlocksIfAbleAllEffect(filter), new ManaCostsImpl("{3}{R/W}{R/W}"));
        ability.addEffect(new BrutalHordechiefReplacementEffect());
        this.addAbility(ability);
    }

    public BrutalHordechief(final BrutalHordechief card) {
        super(card);
    }

    @Override
    public BrutalHordechief copy() {
        return new BrutalHordechief(this);
    }
}

class BrutalHordechiefTriggeredAbility extends TriggeredAbilityImpl {

    public BrutalHordechiefTriggeredAbility() {
        super(Zone.BATTLEFIELD, new LoseLifeTargetEffect(1));
        this.addEffect(new GainLifeEffect(1));
    }

    public BrutalHordechiefTriggeredAbility(final BrutalHordechiefTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public BrutalHordechiefTriggeredAbility copy() {
        return new BrutalHordechiefTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ATTACKER_DECLARED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent source = game.getPermanent(event.getSourceId());
        if (source != null && source.getControllerId().equals(controllerId)) {
            UUID defendingPlayerId = game.getCombat().getDefendingPlayerId(event.getSourceId(), game);
            this.getEffects().get(0).setTargetPointer(new FixedTarget(defendingPlayerId));
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever a creature you control attacks, defending player loses 1 life and you gain 1 life.";
    }
}

class BrutalHordechiefReplacementEffect extends ReplacementEffectImpl {

    public BrutalHordechiefReplacementEffect() {
        super(Duration.EndOfCombat, Outcome.Benefit);
        staticText = ", and you choose how those creatures block";
    }

    public BrutalHordechiefReplacementEffect(final BrutalHordechiefReplacementEffect effect) {
        super(effect);
    }

    @Override
    public BrutalHordechiefReplacementEffect copy() {
        return new BrutalHordechiefReplacementEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DECLARING_BLOCKERS;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Player controller = game.getPlayer(source.getControllerId());
        return controller != null && controller.hasOpponent(event.getPlayerId(), game);
    }
    
    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Player blockController = game.getPlayer(source.getControllerId());
        if (blockController != null) {
            game.getCombat().selectBlockers(blockController, game);
            return true;
        }
        return false;
    }    
}
