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
package mage.sets.gatecrash;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.RemoveCounterCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.Effects;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.DrawCardControllerEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author LevelX2
 */
public class ZameckGuildmage extends CardImpl<ZameckGuildmage> {

    private static final String ruleText = "This turn, each creature you control enters the battlefield with an additional +1/+1 counter on it";

    public ZameckGuildmage(UUID ownerId) {
        super(ownerId, 209, "Zameck Guildmage", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{G}{U}");
        this.expansionSetCode = "GTC";
        this.subtype.add("Elf");
        this.subtype.add("Wizard");

        this.color.setGreen(true);
        this.color.setBlue(true);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);


        // {G}{U}: This turn, each creature you control enters the battlefield with an additional +1/+1 counter on it.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new EntersBattlefieldEffect(new AddCountersTargetEffect(CounterType.P1P1.createInstance(1)), ruleText),new ManaCostsImpl("{G}{U}")));

        // {G}{U}, Remove a +1/+1 counter from a creature you control: Draw a card.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new DrawCardControllerEffect(1),new RemoveCounterCost(new TargetControlledCreaturePermanent(), CounterType.P1P1)));
    }

    public ZameckGuildmage(final ZameckGuildmage card) {
        super(card);
    }

    @Override
    public ZameckGuildmage copy() {
        return new ZameckGuildmage(this);
    }
}

class EntersBattlefieldEffect extends ReplacementEffectImpl<EntersBattlefieldEffect> {

    protected Effects baseEffects = new Effects();
    protected String text;

    public EntersBattlefieldEffect(Effect baseEffect, String text) {
        super(Constants.Duration.EndOfTurn, baseEffect.getOutcome());
        this.baseEffects.add(baseEffect);
        this.text = text;
    }

    public EntersBattlefieldEffect(EntersBattlefieldEffect effect) {
        super(effect);
        this.baseEffects = effect.baseEffects.copy();
        this.text = effect.text;
    }

    public void addEffect(Effect effect) {
        baseEffects.add(effect);
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD) {
            Permanent permanent = game.getPermanent(event.getTargetId());
            if (permanent != null && permanent.getControllerId().equals(source.getControllerId()) && permanent.getCardType().contains(CardType.CREATURE)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        for (Effect effect: baseEffects) {
            Permanent target = game.getPermanent(event.getTargetId());
            if (target != null) {
                effect.setTargetPointer(new FixedTarget(target.getId()));
                effect.apply(game, source);
            }
        }
        return false;
    }

    @Override
    public String getText(Mode mode) {
        return (text == null || text.isEmpty()) ? baseEffects.getText(mode) : text;
    }

    @Override
    public EntersBattlefieldEffect copy() {
        return new EntersBattlefieldEffect(this);
    }
}
