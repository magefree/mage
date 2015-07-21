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
package mage.sets.planeshift;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.dynamicvalue.common.DomainValue;
import mage.abilities.effects.PreventionEffectImpl;
import mage.cards.CardImpl;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Simown
 */
public class SamitePilgrim extends CardImpl {

    public SamitePilgrim(UUID ownerId) {
        super(ownerId, 15, "Samite Pilgrim", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{1}{W}");
        this.expansionSetCode = "PLS";
        this.subtype.add("Human");
        this.subtype.add("Cleric");
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Domain - {tap}: Prevent the next X damage that would be dealt to target creature this turn, where X is the number of basic land types among lands you control.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new SamitePilgrimPreventDamageToTargetEffect(), new TapSourceCost());
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    public SamitePilgrim(final SamitePilgrim card) {
        super(card);
    }

    @Override
    public SamitePilgrim copy() {
        return new SamitePilgrim(this);
    }
}

class SamitePilgrimPreventDamageToTargetEffect extends PreventionEffectImpl {

    protected int amount = 0;

    public SamitePilgrimPreventDamageToTargetEffect() {
        super(Duration.EndOfTurn);
        staticText = "Prevent the next X damage that would be dealt to target creature this turn, where X is the number of basic land types among lands you control.";
    }

    public SamitePilgrimPreventDamageToTargetEffect(final SamitePilgrimPreventDamageToTargetEffect effect) {
        super(effect);
        this.amount = effect.amount;
    }

    @Override
    public SamitePilgrimPreventDamageToTargetEffect copy() {
        return new SamitePilgrimPreventDamageToTargetEffect(this);
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        amount = new DomainValue().calculate(game, source, this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        boolean result = false;
        int toPrevent = amount;
        if (event.getAmount() < this.amount) {
            toPrevent = event.getAmount();
            amount -= event.getAmount();
        } else {
            amount = 0;
        }
        GameEvent preventEvent = new GameEvent(GameEvent.EventType.PREVENT_DAMAGE, source.getControllerId(), source.getSourceId(), source.getControllerId(), toPrevent, false);
        if (!game.replaceEvent(preventEvent)) {
            Permanent targetCreature = game.getPermanent(source.getFirstTarget());
            if (targetCreature != null) {
                if (amount == 0) {
                    this.used = true;
                    this.discard();
                }
                if (event.getAmount() >= toPrevent) {
                    event.setAmount(event.getAmount() - toPrevent);
                } else {
                    event.setAmount(0);
                    result = true;
                }
                if (toPrevent > 0) {
                    game.informPlayers(new StringBuilder("Samite Pilgrim ").append("prevented ").append(toPrevent).append(" to ").append(targetCreature.getName()).toString());
                    game.fireEvent(GameEvent.getEvent(GameEvent.EventType.PREVENTED_DAMAGE,
                            source.getControllerId(), source.getSourceId(), source.getControllerId(), toPrevent));
                }
            }
        }
        return result;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return !this.used && super.applies(event, source, game) && event.getTargetId().equals(source.getFirstTarget());
    }

}
