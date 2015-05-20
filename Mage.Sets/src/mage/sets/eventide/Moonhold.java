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
package mage.sets.eventide;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.condition.LockedInCondition;
import mage.abilities.condition.common.ManaWasSpentCondition;
import mage.abilities.decorator.ConditionalContinuousRuleModifyingEffect;
import mage.abilities.effects.ContinuousRuleModifyingEffect;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.common.InfoEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.ColoredManaSymbol;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.target.TargetPlayer;
import mage.watchers.common.ManaSpentToCastWatcher;

/**
 *
 * @author jeffwadsworth
 */
public class Moonhold extends CardImpl {

    public Moonhold(UUID ownerId) {
        super(ownerId, 143, "Moonhold", Rarity.UNCOMMON, new CardType[]{CardType.INSTANT}, "{2}{R/W}");
        this.expansionSetCode = "EVE";


        // Target player can't play land cards this turn if {R} was spent to cast Moonhold and can't play creature cards this turn if {W} was spent to cast it.
        ContinuousRuleModifyingEffect effect = new MoonholdEffect();
        ContinuousRuleModifyingEffect effect2 = new MoonholdEffect2();
        effect.setText("Target player can't play land cards this turn if {R} was spent to cast {this} ");
        effect2.setText("and can't play creature cards this turn if {W} was spent to cast it.");
        this.getSpellAbility().addEffect(new ConditionalContinuousRuleModifyingEffect(
                effect,
                new LockedInCondition(new ManaWasSpentCondition(ColoredManaSymbol.R))));
        this.getSpellAbility().addEffect(new ConditionalContinuousRuleModifyingEffect(
                effect2,
                new LockedInCondition(new ManaWasSpentCondition(ColoredManaSymbol.W))));
        this.getSpellAbility().addTarget(new TargetPlayer());
        this.getSpellAbility().addEffect(new InfoEffect("<i>(Do both if {R}{W} was spent.)</i>"));
        this.getSpellAbility().addWatcher(new ManaSpentToCastWatcher());
    }

    public Moonhold(final Moonhold card) {
        super(card);
    }

    @Override
    public Moonhold copy() {
        return new Moonhold(this);
    }
}

class MoonholdEffect extends ContinuousRuleModifyingEffectImpl {

    public MoonholdEffect() {
        super(Duration.EndOfTurn, Outcome.Detriment);
    }

    public MoonholdEffect(final MoonholdEffect effect) {
        super(effect);
    }

    @Override
    public MoonholdEffect copy() {
        return new MoonholdEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public String getInfoMessage(Ability source, GameEvent event, Game game) {
        MageObject mageObject = game.getObject(source.getSourceId());
        if (mageObject != null) {
            return "you can't play land cards this turn (" + mageObject.getLogName() + ").";
        }
        return null;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.PLAY_LAND;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getPlayerId().equals(source.getFirstTarget())) {
            return true;
        }
        return false;
    }
}

class MoonholdEffect2 extends ContinuousRuleModifyingEffectImpl {

    public MoonholdEffect2() {
        super(Duration.EndOfTurn, Outcome.Detriment);
    }

    public MoonholdEffect2(final MoonholdEffect2 effect) {
        super(effect);
    }

    @Override
    public MoonholdEffect2 copy() {
        return new MoonholdEffect2(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public String getInfoMessage(Ability source, GameEvent event, Game game) {
        MageObject mageObject = game.getObject(source.getSourceId());
        if (mageObject != null) {
            return "You can't play creature cards this turn (" + mageObject.getLogName() + ").";
        }
        return null;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.CAST_SPELL;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getPlayerId().equals(source.getFirstTarget())) {
            Card card = game.getCard(event.getSourceId());
            if (card != null && card.getCardType().contains(CardType.CREATURE)) {
                return true;
            }
        }
        return false;
    }
}
