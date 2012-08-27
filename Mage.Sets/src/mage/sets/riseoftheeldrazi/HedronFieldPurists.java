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
package mage.sets.riseoftheeldrazi;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Duration;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.MageInt;
import mage.abilities.Abilities;
import mage.abilities.AbilitiesImpl;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.PreventionEffectImpl;
import mage.abilities.keyword.LevelUpAbility;
import mage.abilities.keyword.LevelerCardBuilder;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

/**
 *
 * @author North
 */
public class HedronFieldPurists extends CardImpl<HedronFieldPurists> {

    public HedronFieldPurists(UUID ownerId) {
        super(ownerId, 25, "Hedron-Field Purists", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{2}{W}");
        this.expansionSetCode = "ROE";
        this.subtype.add("Human");
        this.subtype.add("Cleric");

        this.color.setWhite(true);
        this.power = new MageInt(0);
        this.toughness = new MageInt(3);

        // Level up {2}{W}
        this.addAbility(new LevelUpAbility(new ManaCostsImpl("{2}{W}")));
        // LEVEL 1-4
        // 1/4
        // If a source would deal damage to you or a creature you control, prevent 1 of that damage.
        Abilities<Ability> abilities1 = new AbilitiesImpl<Ability>();
        abilities1.add(new SimpleStaticAbility(Zone.BATTLEFIELD, new HedronFieldPuristsEffect(1)));
        // LEVEL 5+
        // 2/5
        // If a source would deal damage to you or a creature you control, prevent 2 of that damage.
        Abilities<Ability> abilities2 = new AbilitiesImpl<Ability>();
        abilities2.add(new SimpleStaticAbility(Zone.BATTLEFIELD, new HedronFieldPuristsEffect(2)));

        LevelerCardBuilder.construct(this,
                new LevelerCardBuilder.LevelAbility(1, 4, abilities1, 1, 4),
                new LevelerCardBuilder.LevelAbility(5, -1, abilities2, 2, 5));
    }

    public HedronFieldPurists(final HedronFieldPurists card) {
        super(card);
    }

    @Override
    public HedronFieldPurists copy() {
        return new HedronFieldPurists(this);
    }
}

class HedronFieldPuristsEffect extends PreventionEffectImpl<HedronFieldPuristsEffect> {

    private int amount;

    public HedronFieldPuristsEffect(int amount) {
        super(Duration.WhileOnBattlefield);
        this.amount = amount;
        this.staticText = "If a source would deal damage to you or a creature you control, prevent " + amount + " of that damage";
    }

    public HedronFieldPuristsEffect(HedronFieldPuristsEffect effect) {
        super(effect);
        this.amount = effect.amount;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        GameEvent preventEvent = new GameEvent(GameEvent.EventType.PREVENT_DAMAGE, source.getFirstTarget(), source.getId(), source.getControllerId(), 1, false);
        if (!game.replaceEvent(preventEvent)) {
            int damage = event.getAmount();
            if (damage >= this.amount) {
                event.setAmount(damage - amount);
                damage = amount;
                this.used = true;
            } else {
                event.setAmount(0);
                amount -= damage;
            }
            game.informPlayers(damage + " damage has been prevented.");
            game.fireEvent(GameEvent.getEvent(GameEvent.EventType.PREVENTED_DAMAGE, source.getFirstTarget(), source.getId(), source.getControllerId(), damage));
        }
        return false;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getType().equals(GameEvent.EventType.DAMAGE_PLAYER)
                && event.getTargetId().equals(source.getControllerId())) {
            return super.applies(event, source, game);
        }

        if (event.getType().equals(GameEvent.EventType.DAMAGE_CREATURE)) {
            Permanent permanent = game.getPermanent(event.getTargetId());
            if (permanent != null && permanent.getControllerId().equals(source.getControllerId())) {
                return super.applies(event, source, game);
            }
        }
        return false;
    }

    @Override
    public HedronFieldPuristsEffect copy() {
        return new HedronFieldPuristsEffect(this);
    }
}
