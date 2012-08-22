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


import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.Effects;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author nantuko
 */
public class DearlyDeparted extends CardImpl<DearlyDeparted> {

    private static final FilterCreaturePermanent filterHuman = new FilterCreaturePermanent("Human creatures");

    private static final String ruleText = "As long as Dearly Departed is in your graveyard, each Human creature you control enters the battlefield with an additional +1/+1 counter on it";

    static {
        filterHuman.add(new SubtypePredicate("Human"));
    }

    public DearlyDeparted(UUID ownerId) {
        super(ownerId, 9, "Dearly Departed", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{4}{W}{W}");
        this.expansionSetCode = "ISD";
        this.subtype.add("Spirit");

        this.color.setWhite(true);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        this.addAbility(FlyingAbility.getInstance());

        // As long as Dearly Departed is in your graveyard, each Human creature you control enters the battlefield with an additional +1/+1 counter on it.
        this.addAbility(new SimpleStaticAbility(Constants.Zone.GRAVEYARD,
                new EntersBattlefieldEffect(new AddCountersTargetEffect(CounterType.P1P1.createInstance(1)), ruleText)));
    }

    public DearlyDeparted(final DearlyDeparted card) {
        super(card);
    }

    @Override
    public DearlyDeparted copy() {
        return new DearlyDeparted(this);
    }
}

class EntersBattlefieldEffect extends ReplacementEffectImpl<EntersBattlefieldEffect> {

    protected Effects baseEffects = new Effects();
    protected String text;

    public static final String SOURCE_CAST_SPELL_ABILITY = "sourceCastSpellAbility";

    public EntersBattlefieldEffect(Effect baseEffect) {
        this(baseEffect, "");
    }

    public EntersBattlefieldEffect(Effect baseEffect, String text) {
        super(Constants.Duration.OneUse, baseEffect.getOutcome());
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
            if (permanent != null && permanent.getControllerId().equals(source.getControllerId()) && permanent.hasSubtype("Human")) {
                setValue("target", permanent);
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
        Spell spell = game.getStack().getSpell(event.getSourceId());
        for (Effect effect: baseEffects) {
            Object target = getValue("target");
            if (target != null && target instanceof Permanent) {
                effect.setTargetPointer(new FixedTarget(((Permanent)target).getId()));
                if (effect instanceof ContinuousEffect) {
                    game.addEffect((ContinuousEffect) effect, source);
                }
                else {
                    if (spell != null) {
                        effect.setValue(SOURCE_CAST_SPELL_ABILITY, spell.getSpellAbility());
                    }
                    effect.apply(game, source);
                }
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
