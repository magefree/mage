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
package mage.sets.magic2015;

import java.util.List;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DestroyAllEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

/**
 *
 * @author Quercitron
 */
public class SiegeDragon extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Walls your opponents control");
    
    static {
        filter.add(new SubtypePredicate("Wall"));
        filter.add(new ControllerPredicate(TargetController.OPPONENT));
    }
    
    public SiegeDragon(UUID ownerId) {
        super(ownerId, 162, "Siege Dragon", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{5}{R}{R}");
        this.expansionSetCode = "M15";
        this.subtype.add("Dragon");

        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        
        // When Siege Dragon enters the battlefield, destroy all Walls your opponents control.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new DestroyAllEffect(filter), false));
        
        // Whenever Siege Dragon attacks, if defending player controls no Walls, it deals 2 damage to each creature without flying that player controls.
        this.addAbility(new SiegeDragonAttacksTriggeredAbility());
    }

    public SiegeDragon(final SiegeDragon card) {
        super(card);
    }

    @Override
    public SiegeDragon copy() {
        return new SiegeDragon(this);
    }
}

class SiegeDragonAttacksTriggeredAbility extends TriggeredAbilityImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("wall");

    static {
        filter.add(new SubtypePredicate("Wall"));
    }

    public SiegeDragonAttacksTriggeredAbility() {
        super(Zone.BATTLEFIELD, new SiegeDragonDamageEffect());
    }

    public SiegeDragonAttacksTriggeredAbility(final SiegeDragonAttacksTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public SiegeDragonAttacksTriggeredAbility copy() {
        return new SiegeDragonAttacksTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return GameEvent.EventType.ATTACKER_DECLARED.equals(event.getType()) && event.getSourceId().equals(this.getSourceId());
    }

    @Override
    public boolean checkInterveningIfClause(Game game) {
        UUID defendingPlayerId = game.getCombat().getDefendingPlayerId(getSourceId(), game);
        return defendingPlayerId != null && game.getBattlefield().countAll(filter, defendingPlayerId, game) < 1;
    }

    @Override
    public String getRule() {
        return "Whenever {this} attacks, if defending player controls no Walls, it deals 2 damage to each creature without flying that player controls.";
    }
}

class SiegeDragonDamageEffect extends OneShotEffect {

    public SiegeDragonDamageEffect() {
        super(Outcome.Damage);
    }
    
    public SiegeDragonDamageEffect(final SiegeDragonDamageEffect effect) {
        super(effect);
    }

    @Override
    public SiegeDragonDamageEffect copy() {
        return new SiegeDragonDamageEffect(this);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        UUID defendingPlayerId = game.getCombat().getDefendingPlayerId(source.getSourceId(), game);
        if (defendingPlayerId != null) {
            FilterCreaturePermanent filter = new FilterCreaturePermanent();
            filter.add(new ControllerIdPredicate(defendingPlayerId));
            filter.add(Predicates.not(new AbilityPredicate(FlyingAbility.class)));
            List<Permanent> permanents = game.getBattlefield().getActivePermanents(filter, source.getControllerId(), source.getSourceId(), game);
            for (Permanent permanent : permanents) {
                permanent.damage(2, source.getSourceId(), game, false, true);
            }
            return true;
        }
        return false;
    }
    
}
