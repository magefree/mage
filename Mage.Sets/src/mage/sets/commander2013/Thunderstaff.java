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
package mage.sets.commander2013;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.PreventionEffectImpl;
import mage.abilities.effects.common.continious.BoostAllEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.AttackingPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

/**
 *
 * @author LevelX2
 */
public class Thunderstaff extends CardImpl<Thunderstaff> {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Attacking creatures");
    static {
        filter.add(new AttackingPredicate());
    }

    public Thunderstaff(UUID ownerId) {
        super(ownerId, 267, "Thunderstaff", Rarity.UNCOMMON, new CardType[]{CardType.ARTIFACT}, "{3}");
        this.expansionSetCode = "C13";

        // As long as Thunderstaff is untapped, if a creature would deal combat damage to you, prevent 1 of that damage.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new ThunderstaffPreventionEffect()));
        // {2}, {tap}: Attacking creatures get +1/+0 until end of turn.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new BoostAllEffect(1,0,Duration.EndOfTurn, filter, false), new GenericManaCost(2));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);

    }

    public Thunderstaff(final Thunderstaff card) {
        super(card);
    }

    @Override
    public Thunderstaff copy() {
        return new Thunderstaff(this);
    }
}

class ThunderstaffPreventionEffect extends PreventionEffectImpl<ThunderstaffPreventionEffect> {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent();

    public ThunderstaffPreventionEffect() {
        super(Duration.WhileOnBattlefield);
        staticText = "As long as {this} is untapped, if a creature would deal combat damage to you, prevent 1 of that damage";
    }

    public ThunderstaffPreventionEffect(final ThunderstaffPreventionEffect effect) {
        super(effect);
    }

    @Override
    public ThunderstaffPreventionEffect copy() {
        return new ThunderstaffPreventionEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        GameEvent preventEvent = new GameEvent(GameEvent.EventType.PREVENT_DAMAGE, source.getControllerId(), source.getSourceId(), source.getControllerId(), 1, false);
        if (!game.replaceEvent(preventEvent)) {
            event.setAmount(event.getAmount() - 1);
            Permanent permanent = game.getPermanent(event.getSourceId());
            Permanent sourcePermanent = game.getPermanent(source.getSourceId());
            if (permanent != null && sourcePermanent != null) {
                StringBuilder sb = new StringBuilder();
                sb.append(sourcePermanent.getName()).append(": ");
                sb.append(1).append(" damage").append(" from ").append(permanent.getName());
                sb.append(" has been prevented");
                game.informPlayers(sb.toString());
            }
            game.fireEvent(GameEvent.getEvent(GameEvent.EventType.PREVENTED_DAMAGE, source.getControllerId(), source.getSourceId(), source.getControllerId(), 1));
        }
        return false;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (super.applies(event, source, game)) {
            if (event.getTargetId().equals(source.getControllerId())){
                Permanent sourcePermanent = game.getPermanent(source.getSourceId());
                if (sourcePermanent != null && !sourcePermanent.isTapped()) {
                    Permanent damageSource = game.getPermanent(event.getSourceId());
                    if (damageSource != null && filter.match(damageSource, game)) {
                        return true;
                    }
                }
            }

        }
        return false;
    }

}
