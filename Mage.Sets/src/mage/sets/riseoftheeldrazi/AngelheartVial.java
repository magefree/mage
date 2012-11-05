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
import mage.Constants;
import mage.Constants.Outcome;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardControllerEffect;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.counters.CounterType;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

/**
 *
 * @author jeffwadsworth
 */
public class AngelheartVial extends CardImpl<AngelheartVial> {

    public AngelheartVial(UUID ownerId) {
        super(ownerId, 215, "Angelheart Vial", Rarity.RARE, new CardType[]{CardType.ARTIFACT}, "{5}");
        this.expansionSetCode = "ROE";

        // Whenever you're dealt damage, you may put that many charge counters on Angelheart Vial.
        this.addAbility(new AngelheartVialTriggeredAbility());

        // {2}, {tap}, Remove four charge counters from Angelheart Vial: You gain 2 life and draw a card.
        Ability ability = new SimpleActivatedAbility(Constants.Zone.BATTLEFIELD, new GainLifeEffect(2), new GenericManaCost(2));
        ability.addCost(new TapSourceCost());
        ability.addCost(new RemoveCountersSourceCost(new RemoveCountersSourceCost(CounterType.CHARGE.createInstance(4))));
        ability.addEffect(new DrawCardControllerEffect(1));
        this.addAbility(ability);
    }

    public AngelheartVial(final AngelheartVial card) {
        super(card);
    }

    @Override
    public AngelheartVial copy() {
        return new AngelheartVial(this);
    }
}

class AngelheartVialTriggeredAbility extends TriggeredAbilityImpl<AngelheartVialTriggeredAbility> {

    public AngelheartVialTriggeredAbility() {
        super(Constants.Zone.BATTLEFIELD, new AngelheartVialEffect(), true);
    }

    public AngelheartVialTriggeredAbility(final AngelheartVialTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public AngelheartVialTriggeredAbility copy() {
        return new AngelheartVialTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if ((event.getType() == GameEvent.EventType.DAMAGED_PLAYER && event.getTargetId().equals(this.getControllerId()))) {
                    this.getEffects().get(0).setValue("damageAmount", event.getAmount());
                    return true;
                }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever you are dealt damage, you may put that many charge counters on {this}.";
    }
}

class AngelheartVialEffect extends OneShotEffect<AngelheartVialEffect> {

    public AngelheartVialEffect() {
        super(Outcome.Benefit);
    }

    public AngelheartVialEffect(final AngelheartVialEffect effect) {
        super(effect);
    }

    @Override
    public AngelheartVialEffect copy() {
        return new AngelheartVialEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent != null) {
            permanent.addCounters(CounterType.CHARGE.createInstance((Integer) this.getValue("damageAmount")), game);
        }
        return true;
    }
}

