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
package mage.sets.innistrad;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Duration;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.PreventionEffectImpl;
import mage.cards.CardImpl;
import mage.counters.CounterType;
import mage.filter.common.FilterCreatureCard;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author BetaSteward
 */
public class UnbreathingHorde extends CardImpl<UnbreathingHorde> {

    public UnbreathingHorde(UUID ownerId) {
        super(ownerId, 121, "Unbreathing Horde", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{2}{B}");
        this.expansionSetCode = "ISD";
        this.subtype.add("Zombie");

        this.color.setBlack(true);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Unbreathing Horde enters the battlefield with a +1/+1 counter on it for each other Zombie you control and each Zombie card in your graveyard.
 		this.addAbility(new EntersBattlefieldAbility(new UnbreathingHordeEffect1(), "with a +1/+1 counter on it for each other Zombie you control and each Zombie card in your graveyard"));
       
        // If Unbreathing Horde would be dealt damage, prevent that damage and remove a +1/+1 counter from it.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new UnbreathingHordeEffect2()));
    }

    public UnbreathingHorde(final UnbreathingHorde card) {
        super(card);
    }

    @Override
    public UnbreathingHorde copy() {
        return new UnbreathingHorde(this);
    }
}

class UnbreathingHordeEffect1 extends OneShotEffect<UnbreathingHordeEffect1> {

    private static final FilterCreaturePermanent filter1 = new FilterCreaturePermanent();
    private static final FilterCreatureCard filter2 = new FilterCreatureCard();
    
    static {
        filter1.getSubtype().add("Zombie");
        filter2.getSubtype().add("Zombie");
    }
    
    public UnbreathingHordeEffect1() {
        super(Outcome.BoostCreature);
        staticText = "{this} enters the battlefield with a +1/+1 counter on it for each other Zombie you control and each Zombie card in your graveyard";
    }

    public UnbreathingHordeEffect1(final UnbreathingHordeEffect1 effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent != null && player != null) {
            int amount = game.getBattlefield().countAll(filter1, source.getControllerId()) - 1;
            amount += player.getGraveyard().count(filter2, game);
            if (amount > 0) {
                permanent.addCounters(CounterType.P1P1.createInstance(amount), game);
            }
            return true;
        }
        return false;
    }

    @Override
    public UnbreathingHordeEffect1 copy() {
        return new UnbreathingHordeEffect1(this);
    }

}

class UnbreathingHordeEffect2 extends PreventionEffectImpl<UnbreathingHordeEffect2> {

    public UnbreathingHordeEffect2() {
        super(Duration.WhileOnBattlefield);
    }

    public UnbreathingHordeEffect2(final UnbreathingHordeEffect2 effect) {
        super(effect);
        staticText = "If damage would be dealt to {this}, prevent that damage and remove that many +1/+1 counters from it";
    }

    @Override
    public UnbreathingHordeEffect2 copy() {
        return new UnbreathingHordeEffect2(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        boolean retValue = false;
        GameEvent preventEvent = new GameEvent(GameEvent.EventType.PREVENT_DAMAGE, source.getFirstTarget(), source.getId(), source.getControllerId(), event.getAmount(), false);
        int damage = event.getAmount();
        if (!game.replaceEvent(preventEvent)) {
            event.setAmount(0);
            game.fireEvent(GameEvent.getEvent(GameEvent.EventType.PREVENTED_DAMAGE, source.getFirstTarget(), source.getId(), source.getControllerId(), damage));
            retValue = true;
        }
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent != null) {
            permanent.removeCounters(CounterType.P1P1.createInstance(damage), game);
        }
        return retValue;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (super.applies(event, source, game)) {
            if (event.getTargetId().equals(source.getSourceId())) {
                return true;
            }
        }
        return false;
    }

}
