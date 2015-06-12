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
package mage.sets.mastersedition;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.PreventionEffectImpl;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.UnblockedPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author emerald000
 */
public class Forcefield extends CardImpl {

    public Forcefield(UUID ownerId) {
        super(ownerId, 157, "Forcefield", Rarity.RARE, new CardType[]{CardType.ARTIFACT}, "{3}");
        this.expansionSetCode = "MED";

        // {1}: The next time an unblocked creature of your choice would deal combat damage to you this turn, prevent all but 1 of that damage.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new ForcefieldEffect(), new GenericManaCost(1)));
    }

    public Forcefield(final Forcefield card) {
        super(card);
    }

    @Override
    public Forcefield copy() {
        return new Forcefield(this);
    }
}

class ForcefieldEffect extends OneShotEffect {
    
    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("an unblocked creature");
    static {
        filter.add(new UnblockedPredicate());
    }
    
    ForcefieldEffect() {
        super(Outcome.PreventDamage);
        this.staticText = "The next time an unblocked creature of your choice would deal combat damage to you this turn, prevent all but 1 of that damage";
    }
    
    ForcefieldEffect(final ForcefieldEffect effect) {
        super(effect);
    }
    
    @Override
    public ForcefieldEffect copy() {
        return new ForcefieldEffect(this);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Target target = new TargetCreaturePermanent(1, 1, filter, true);
            if (controller.choose(Outcome.PreventDamage, target, source.getSourceId(), game)) {
                ContinuousEffect effect = new ForcefieldPreventionEffect();
                effect.setTargetPointer(new FixedTarget(target.getFirstTarget()));
                game.addEffect(effect, source);
            }
            return true;
        }
        return false;
    }
}

class ForcefieldPreventionEffect extends PreventionEffectImpl {

    ForcefieldPreventionEffect() {
        super(Duration.EndOfTurn, Integer.MAX_VALUE, true, false);
        this.staticText = "Prevent all but 1 of that damage";
    }

    ForcefieldPreventionEffect(ForcefieldPreventionEffect effect) {
        super(effect);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        int damage = event.getAmount();
        if (damage > 0) {
            this.amountToPrevent = damage - 1;
            preventDamageAction(event, source, game);
            this.discard();
        }
        return false;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == EventType.DAMAGE_PLAYER;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return super.applies(event, source, game)
                && event.getSourceId().equals(this.getTargetPointer().getFirst(game, source));
    }

    @Override
    public ForcefieldPreventionEffect copy() {
        return new ForcefieldPreventionEffect(this);
    }
}
