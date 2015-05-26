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
package mage.sets.iceage;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.UnblockedPredicate;
import mage.game.Game;
import mage.game.events.DamageEvent;
import mage.game.events.DamagePlayerEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

/**
 *
 * @author Quercitron
 */
public class KjeldoranRoyalGuard extends CardImpl {

    public KjeldoranRoyalGuard(UUID ownerId) {
        super(ownerId, 262, "Kjeldoran Royal Guard", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{3}{W}{W}");
        this.expansionSetCode = "ICE";
        this.subtype.add("Human");
        this.subtype.add("Soldier");

        this.power = new MageInt(2);
        this.toughness = new MageInt(5);

        // {tap}: All combat damage that would be dealt to you by unblocked creatures this turn is dealt to Kjeldoran Royal Guard instead.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new KjeldoranRoyalGuardEffect(), new TapSourceCost()));
    }

    public KjeldoranRoyalGuard(final KjeldoranRoyalGuard card) {
        super(card);
    }

    @Override
    public KjeldoranRoyalGuard copy() {
        return new KjeldoranRoyalGuard(this);
    }
}

class KjeldoranRoyalGuardEffect extends ReplacementEffectImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("unblocked creatures");
    
    static {
        filter.add(new UnblockedPredicate());
    }
    
    KjeldoranRoyalGuardEffect() {
        super(Duration.EndOfTurn, Outcome.RedirectDamage);
        staticText = "All combat damage that would be dealt to you by unblocked creatures this turn is dealt to {source} instead";
    }

    KjeldoranRoyalGuardEffect(final KjeldoranRoyalGuardEffect effect) {
        super(effect);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        DamagePlayerEvent damageEvent = (DamagePlayerEvent) event;
        Permanent p = game.getPermanent(source.getSourceId());
        if (p != null) {
            p.damage(damageEvent.getAmount(), event.getSourceId(), game, damageEvent.isCombatDamage(), damageEvent.isPreventable());
            return true;
        }
        return true;
    }
    
    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGE_PLAYER;
    }
    
    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getPlayerId().equals(source.getControllerId())
                && ((DamageEvent)event).isCombatDamage()) {
            Permanent p = game.getPermanent(source.getSourceId());
            if (p != null) {
                for (Permanent permanent : game.getBattlefield().getActivePermanents(filter, source.getControllerId(), source.getSourceId(), game)) {
                    if (event.getSourceId().equals(permanent.getId())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public KjeldoranRoyalGuardEffect copy() {
        return new KjeldoranRoyalGuardEffect(this);
    }
}
