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
package mage.cards.c;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.condition.common.SuspendedCondition;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalTriggeredAbility;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.SuspendAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetPlayer;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author anonymous
 */
public class CurseOfTheCabal extends CardImpl {

    public CurseOfTheCabal(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{9}{B}");

        // Target player sacrifices half the permanents he or she controls, rounded down.
        this.getSpellAbility().addTarget(new TargetPlayer());
        this.getSpellAbility().addEffect(new CurseOfTheCabalSacrificeEffect());
        // Suspend 2-{2}{B}{B}
        this.addAbility(new SuspendAbility(2, new ManaCostsImpl("{2}{B}{B}"), this));
        // At the beginning of each player's upkeep, if Curse of the Cabal is suspended, that player may sacrifice a permanent. If he or she does, put two time counters on Curse of the Cabal.
        this.addAbility(new CurseOfTheCabalTriggeredAbility());
    }

    public CurseOfTheCabal(final CurseOfTheCabal card) {
        super(card);
    }

    @Override
    public CurseOfTheCabal copy() {
        return new CurseOfTheCabal(this);
    }
}

class CurseOfTheCabalSacrificeEffect extends OneShotEffect{

    private static final FilterControlledPermanent FILTER = new FilterControlledPermanent(); // ggf filter.FilterPermanent

    public CurseOfTheCabalSacrificeEffect() {
        super(Outcome.Sacrifice);
        this.staticText = "Target player sacrifices half the permanents he or she controls, rounded down.";
    }

    public CurseOfTheCabalSacrificeEffect(final CurseOfTheCabalSacrificeEffect effect) {
        super(effect);
    }

    @Override
    public CurseOfTheCabalSacrificeEffect copy() {
        return new CurseOfTheCabalSacrificeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player targetPlayer = game.getPlayer(source.getFirstTarget());
        if(targetPlayer != null) {
            int amount = game.getBattlefield().countAll(FILTER, targetPlayer.getId(), game) / 2;
            if(amount < 1)
                return true;
            Target target = new TargetControlledPermanent(amount, amount, FILTER, true);
            if (target.canChoose(targetPlayer.getId(), game)) {
                while (!target.isChosen() && target.canChoose(targetPlayer.getId(), game) && targetPlayer.canRespond()) {
                    targetPlayer.choose(Outcome.Sacrifice, target, source.getSourceId(), game);
                }
                for (int idx = 0; idx < target.getTargets().size(); idx++) {
                    Permanent permanent = game.getPermanent(target.getTargets().get(idx));
                    if (permanent != null) {
                        permanent.sacrifice(source.getSourceId(), game);
                    }
                }
            }
            return true;
        }
        return false;
    }
}

class CurseOfTheCabalTriggeredAbility extends ConditionalTriggeredAbility {

    public CurseOfTheCabalTriggeredAbility() {
        super(new BeginningOfUpkeepTriggeredAbility(
                        Zone.EXILED, new CurseOfTheCabalTriggeredAbilityConditionalDelay(),
                        TargetController.ANY, false, true
                ),
                SuspendedCondition.instance,
                "At the beginning of each player's upkeep, if {this} is suspended, that player may sacrifice a permanent. If he or she does, put two time counters on {this}."
        );
        // controller has to sac a permanent
        // counters aren't placed
    }

    public CurseOfTheCabalTriggeredAbility(final CurseOfTheCabalTriggeredAbility effect) {
        super(effect);
    }

    @Override
    public CurseOfTheCabalTriggeredAbility copy() {
        return new CurseOfTheCabalTriggeredAbility(this);
    }
}

class CurseOfTheCabalTriggeredAbilityConditionalDelay extends AddCountersSourceEffect{

    public CurseOfTheCabalTriggeredAbilityConditionalDelay(){
        super(CounterType.TIME.createInstance(), new StaticValue(2), false, true);
    }

    public boolean apply(Game game, Ability source) {
        UUID id = game.getActivePlayerId();
        Player target = game.getPlayer(id);
        Cost cost = new SacrificeTargetCost(new TargetControlledPermanent(new FilterControlledPermanent()));
        if(target == null)
            return false;
        if (cost.canPay(source, source.getSourceId(), id, game)
                && target.chooseUse(Outcome.Sacrifice, "Sacrifice a permanent to delay Curse of the Cabal?", source, game)
                && cost.pay(source, game, source.getSourceId(), id, true, null)) {
            return super.apply(game, source);
        }
        return true;
    }

    public CurseOfTheCabalTriggeredAbilityConditionalDelay(final CurseOfTheCabalTriggeredAbilityConditionalDelay effect) {
        super(effect);
    }

    @Override
    public CurseOfTheCabalTriggeredAbilityConditionalDelay copy() {
        return new CurseOfTheCabalTriggeredAbilityConditionalDelay(this);
    }
}
