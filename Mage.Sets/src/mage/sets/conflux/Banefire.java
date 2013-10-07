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
package mage.sets.conflux;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.common.TargetCreatureOrPlayer;

/**
 *
 * @author LevelX2
 */
public class Banefire extends CardImpl<Banefire> {

    public Banefire(UUID ownerId) {
        super(ownerId, 58, "Banefire", Rarity.RARE, new CardType[]{CardType.SORCERY}, "{X}{R}");
        this.expansionSetCode = "CON";

        this.color.setRed(true);

        // Banefire deals X damage to target creature or player.       
        this.getSpellAbility().addEffect(new BaneFireEffect());
        this.getSpellAbility().addTarget(new TargetCreatureOrPlayer());
        // If X is 5 or more, Banefire can't be countered by spells or abilities and the damage can't be prevented.
        this.addAbility(new SimpleStaticAbility(Zone.STACK, new BanefireCantCounterEffect()));

    }

    public Banefire(final Banefire card) {
        super(card);
    }

    @Override
    public Banefire copy() {
        return new Banefire(this);
    }
}

class testCondition implements Condition {

    private DynamicValue xValue;
    private int limit;

    public testCondition(DynamicValue xValue, int limit) {
        this.xValue = xValue;
        this.limit = limit;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Spell spell = (Spell) game.getStack().getStackObject(source.getSourceId());
        if (spell != null) {
            return (xValue.calculate(game, spell.getSpellAbility()) >= limit);
        }
        return false;

    }
}

class BaneFireEffect extends OneShotEffect<BaneFireEffect> {

    public BaneFireEffect() {
        super(Outcome.Damage);
        staticText = "{this} deals X damage to target creature or player";
    }

    public BaneFireEffect(final BaneFireEffect effect) {
        super(effect);
    }

    @Override
    public BaneFireEffect copy() {
        return new BaneFireEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player targetPlayer = game.getPlayer(source.getFirstTarget());
        Permanent targetCreature = game.getPermanent(source.getFirstTarget());
        int damage = source.getManaCostsToPay().getX();
        boolean preventable = damage >= 5;
        if (targetPlayer != null) {
            targetPlayer.damage(damage, source.getSourceId(), game, false, preventable);
            return true;
        }
        if (targetCreature != null) {
            targetCreature.damage(damage, source.getSourceId(), game, false, preventable);
            return true;
        }
        return false;
    }
}

class BanefireCantCounterEffect extends ReplacementEffectImpl<BanefireCantCounterEffect> {

    Condition condition = new testCondition(new ManacostVariableValue(), 5);

    public BanefireCantCounterEffect() {
        super(Duration.WhileOnStack, Outcome.Benefit);
        staticText = "If X is 5 or more, {this} can't be countered by spells or abilities and the damage can't be prevented";
    }

    public BanefireCantCounterEffect(final BanefireCantCounterEffect effect) {
        super(effect);
        this.condition = effect.condition;
    }

    @Override
    public BanefireCantCounterEffect copy() {
        return new BanefireCantCounterEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        return true;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getType() == EventType.COUNTER) {
            Card card = game.getCard(source.getSourceId());
            if (card != null) {
                UUID spellId = card.getSpellAbility().getId();
                if (event.getTargetId().equals(spellId)) {
                    if (condition.apply(game, source)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

}