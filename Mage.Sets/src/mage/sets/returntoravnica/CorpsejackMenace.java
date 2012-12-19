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
package mage.sets.returntoravnica;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.cards.CardImpl;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

/**
 *  http://www.wizards.com/magic/magazine/article.aspx?x=mtg/faq/rtr
 *
 *  If a creature you control would enter the battlefield with a number of +1/+1
 *  counters on it, it enters with twice that many instead.
 *
 *  If you control two Corpsejack Menaces, the number of +1/+1 counters placed
 *  is four times the original number. Three Corpsejack Menaces multiplies the
 *  original number by eight, and so on.
 *
 * @author LevelX2
 */
public class CorpsejackMenace extends CardImpl<CorpsejackMenace> {

    public CorpsejackMenace(UUID ownerId) {
        super(ownerId, 152, "Corpsejack Menace", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{2}{B}{G}");
        this.expansionSetCode = "RTR";
        this.subtype.add("Fungus");

        this.color.setBlack(true);
        this.color.setGreen(true);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // If one or more +1/+1 counters would be placed on a creature you control, twice that many +1/+1 counters are placed on it instead.
        this.addAbility(new SimpleStaticAbility(Constants.Zone.BATTLEFIELD, new CorpsejackMenaceReplacementEffect()));

    }

    public CorpsejackMenace(final CorpsejackMenace card) {
        super(card);
    }

    @Override
    public CorpsejackMenace copy() {
        return new CorpsejackMenace(this);
    }
}


class CorpsejackMenaceReplacementEffect extends ReplacementEffectImpl<CorpsejackMenaceReplacementEffect> {
    CorpsejackMenaceReplacementEffect() {
        super(Constants.Duration.WhileOnBattlefield, Constants.Outcome.BoostCreature);
        staticText = "If one or more +1/+1 counters would be placed on a creature you control, twice that many +1/+1 counters are placed on it instead";
    }

    CorpsejackMenaceReplacementEffect(final CorpsejackMenaceReplacementEffect effect) {
        super(effect);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Permanent p = game.getPermanent(event.getTargetId());
        if (p != null) {
            event.getAppliedEffects().add(getId()); // because replaced events are droped, this keeps track of consumed replacement effects for one origin event
            p.addCounters(CounterType.P1P1.createInstance(event.getAmount()*2), game, event.getAppliedEffects());
        }
        return true;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getType() == GameEvent.EventType.ADD_COUNTER && !event.getAppliedEffects().contains(getId())
                                                               && event.getData().equals(CounterType.P1P1.getName())) {
            Permanent target = game.getPermanent(event.getTargetId());
            if (target != null && target.getControllerId().equals(source.getControllerId())
                               && target.getCardType().contains(CardType.CREATURE)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public CorpsejackMenaceReplacementEffect copy() {
        return new CorpsejackMenaceReplacementEffect(this);
    }
}