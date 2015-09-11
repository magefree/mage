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
import mage.MageObject;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.DamageEverythingEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.PermanentIdPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public class Arcbond extends CardImpl {

    public Arcbond(UUID ownerId) {
        super(ownerId, 91, "Arcbond", Rarity.RARE, new CardType[]{CardType.INSTANT}, "{2}{R}");
        this.expansionSetCode = "FRF";

        // Choose target creature. Whenever that creature is dealt damage this turn, it deals that much damage to each other creature and each player.
        this.getSpellAbility().addEffect(new CreateDelayedTriggeredAbilityEffect(new ArcbondDelayedTriggeredAbility(), true, true));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    public Arcbond(final Arcbond card) {
        super(card);
    }

    @Override
    public Arcbond copy() {
        return new Arcbond(this);
    }
}

class ArcbondDelayedTriggeredAbility extends DelayedTriggeredAbility {

    MageObjectReference targetObject;

    public ArcbondDelayedTriggeredAbility() {
        super(new ArcbondEffect(), Duration.EndOfTurn, false);
    }

    public ArcbondDelayedTriggeredAbility(ArcbondDelayedTriggeredAbility ability) {
        super(ability);
        this.targetObject = ability.targetObject;
    }

    @Override
    public void init(Game game) {
        super.init(game);
        // because target can already be gone from battlefield if triggered ability resolves, we need to hold an own object reference
        targetObject = new MageObjectReference(getTargets().getFirstTarget(), game);
        if (targetObject != null) {
            for (Effect effect : this.getEffects()) {
                effect.setValue("sourceId", targetObject.getSourceId());
            }
            this.getTargets().clear();
        }
    }

    @Override
    public boolean isInactive(Game game) {
        if (targetObject == null) {
            return true;
        }
        return super.isInactive(game);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_CREATURE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getTargetId().equals(targetObject.getSourceId())
                && targetObject.getPermanentOrLKIBattlefield(game) != null) {
            for (Effect effect : this.getEffects()) {
                effect.setValue("damage", event.getAmount());
            }
            return true;
        }
        return false;
    }

    @Override
    public ArcbondDelayedTriggeredAbility copy() {
        return new ArcbondDelayedTriggeredAbility(this);
    }

    @Override
    public String getRule() {
        return "Choose target creature. Whenever that creature is dealt damage this turn, " + modes.getText();
    }
}

class ArcbondEffect extends OneShotEffect {

    public ArcbondEffect() {
        super(Outcome.Benefit);
        this.staticText = "it deals that much damage to each other creature and each player";
    }

    public ArcbondEffect(final ArcbondEffect effect) {
        super(effect);
    }

    @Override
    public ArcbondEffect copy() {
        return new ArcbondEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int damage = (Integer) this.getValue("damage");
        UUID sourceId = (UUID) this.getValue("sourceId");
        MageObject sourceObject = game.getObject(source.getSourceId());
        if (sourceObject != null && damage > 0 && sourceId != null) {
            Permanent targetObject = game.getPermanentOrLKIBattlefield(sourceId);
            if (targetObject != null) {
                game.informPlayers(sourceObject.getLogName() + ": " + targetObject.getLogName() + " deals " + damage + " damage to each other creature and each player");
            }
            FilterPermanent filter = new FilterCreaturePermanent("each other creature");
            filter.add(Predicates.not(new PermanentIdPredicate(sourceId)));
            return new DamageEverythingEffect(new StaticValue(damage), filter, sourceId).apply(game, source);
        }
        return false;
    }
}
