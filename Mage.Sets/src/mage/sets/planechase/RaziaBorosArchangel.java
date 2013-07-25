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
package mage.sets.planechase;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.PreventionEffectImpl;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author jeffwadsworth
 */
public class RaziaBorosArchangel extends CardImpl<RaziaBorosArchangel> {

    public RaziaBorosArchangel(UUID ownerId) {
        super(ownerId, 92, "Razia, Boros Archangel", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{W}");
        this.expansionSetCode = "HOP";
        this.supertype.add("Legendary");
        this.subtype.add("Angel");

        this.color.setRed(true);
        this.color.setWhite(true);
        this.power = new MageInt(6);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // {tap}: The next 3 damage that would be dealt to target creature you control this turn is dealt to another target creature instead.
        Effect effect = new RaziaBorosArchangelEffect(Duration.EndOfTurn, 3);
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, effect, new TapSourceCost());
        ability.addTarget(new TargetControlledCreaturePermanent());
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);

    }

    public RaziaBorosArchangel(final RaziaBorosArchangel card) {
        super(card);
    }

    @Override
    public RaziaBorosArchangel copy() {
        return new RaziaBorosArchangel(this);
    }
}

class RaziaBorosArchangelEffect extends PreventionEffectImpl<RaziaBorosArchangelEffect> {

    private int amount;

    public RaziaBorosArchangelEffect(Duration duration, int amount) {
        super(duration);
        this.amount = amount;
        staticText = "The next " + amount + " damage that would be dealt to target creature you control this turn is dealt to another target creature instead";
    }

    public RaziaBorosArchangelEffect(final RaziaBorosArchangelEffect effect) {
        super(effect);
        this.amount = effect.amount;
    }

    @Override
    public RaziaBorosArchangelEffect copy() {
        return new RaziaBorosArchangelEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        GameEvent preventEvent = new GameEvent(GameEvent.EventType.PREVENT_DAMAGE, source.getFirstTarget(), source.getId(), source.getControllerId(), event.getAmount(), false);
        if (!game.replaceEvent(preventEvent)) {
            int prevented = 0;
            if (event.getAmount() >= this.amount) {
                int damage = amount;
                event.setAmount(event.getAmount() - amount);
                this.used = true;
                game.fireEvent(GameEvent.getEvent(GameEvent.EventType.PREVENTED_DAMAGE, source.getFirstTarget(), source.getId(), source.getControllerId(), damage));
                prevented = damage;
            } else {
                int damage = event.getAmount();
                event.setAmount(0);
                amount -= damage;
                game.fireEvent(GameEvent.getEvent(GameEvent.EventType.PREVENTED_DAMAGE, source.getFirstTarget(), source.getId(), source.getControllerId(), damage));
                prevented = damage;
            }

            // deal damage now
            if (prevented > 0) {
                UUID redirectTo = source.getTargets().get(1).getFirstTarget();
                Permanent permanent = game.getPermanent(redirectTo);
                if (permanent != null) {
                    game.informPlayers("Dealing " + prevented + " to " + permanent.getName() + " instead");
                    // keep the original source id as it is redirecting
                    permanent.damage(prevented, event.getSourceId(), game, true, false);
                }
            }
        }
        return false;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (!this.used && super.applies(event, source, game)) {
            if (source.getTargets().getFirstTarget().equals(event.getTargetId())) {
                return true;
            }
        }
        return false;
    }
}