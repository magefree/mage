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
package mage.sets.eldritchmoon;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CopyEffect;
import mage.abilities.effects.common.ReturnFromExileEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author spjspj
 */
public class IdentityThief extends CardImpl {

    private final static FilterCreaturePermanent filter = new FilterCreaturePermanent("target nontoken creature");

    static {
        filter.add(Predicates.not(new TokenPredicate()));
    }

    public IdentityThief(UUID ownerId) {
        super(ownerId, 64, "Identity Thief", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{2}{U}{U}");
        this.expansionSetCode = "EMN";
        this.subtype.add("Shapeshifter");
        this.power = new MageInt(0);
        this.toughness = new MageInt(3);

        // Whenever Identity Thief attacks, you may exile another target nontoken creature.
        //   If you do, Identity Thief becomes a copy of that creature until end of turn.
        //   Return the exiled card to the battlefield under its owner's control at the beginning of the next end step.
        Ability ability = new IdentityThiefAbility();
        ability.addTarget(new TargetCreaturePermanent(0, 1, filter, false));
        this.addAbility(ability);
    }

    public IdentityThief(final IdentityThief card) {
        super(card);
    }

    @Override
    public IdentityThief copy() {
        return new IdentityThief(this);
    }
}

class IdentityThiefAbility extends TriggeredAbilityImpl {

    public IdentityThiefAbility() {
        super(Zone.BATTLEFIELD, null);
        this.addEffect(new IdentityThiefEffect());
    }

    public IdentityThiefAbility(final IdentityThiefAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.ATTACKER_DECLARED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return event.getSourceId().equals(this.getSourceId());
    }

    @Override
    public String getRule() {
        return new StringBuilder("Whenever {this} attacks, ").append(super.getRule()).toString();
    }

    @Override
    public IdentityThiefAbility copy() {
        return new IdentityThiefAbility(this);
    }
}

class IdentityThiefEffect extends OneShotEffect {

    public IdentityThiefEffect() {
        super(Outcome.Detriment);
        staticText = "you may exile another target nontoken creature. If you do, {this} becomes a copy of that creature until end of turn. "
                + "Return the exiled card to the battlefield under its owner's control at the beginning of the next end step";
    }

    public IdentityThiefEffect(final IdentityThiefEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        Player controller = game.getPlayer(source.getControllerId());
        Permanent sourcePermanent = game.getPermanentOrLKIBattlefield(source.getSourceId());
        if (controller != null && permanent != null && sourcePermanent != null) {
            Permanent permanentReset = permanent.copy();
            permanentReset.getCounters(game).clear();
            permanentReset.getPower().resetToBaseValue();
            permanentReset.getToughness().resetToBaseValue();
            CopyEffect copyEffect = new CopyEffect(Duration.EndOfTurn, permanentReset, source.getSourceId());
            if (controller.moveCardToExileWithInfo(permanent, source.getSourceId(), sourcePermanent.getIdName(), source.getSourceId(), game, Zone.BATTLEFIELD, true)) {
                // Copy exiled permanent
                game.addEffect(copyEffect, source);
                // Create delayed triggered ability
                AtTheBeginOfNextEndStepDelayedTriggeredAbility delayedAbility = new AtTheBeginOfNextEndStepDelayedTriggeredAbility(new ReturnFromExileEffect(source.getSourceId(), Zone.BATTLEFIELD, false));
                delayedAbility.setSourceId(source.getSourceId());
                delayedAbility.setControllerId(source.getControllerId());
                delayedAbility.setSourceObject(source.getSourceObject(game), game);
                game.addDelayedTriggeredAbility(delayedAbility);
                return true;
            }
        }
        return false;
    }

    @Override
    public IdentityThiefEffect copy() {
        return new IdentityThiefEffect(this);
    }
}
