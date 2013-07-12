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
package mage.sets.magic2014;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.DiesTriggeredAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.dynamicvalue.common.CountersCount;
import mage.abilities.effects.EntersBattlefieldEffect;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.Target;
import mage.target.common.TargetCreaturePermanentAmount;

/**
 *
 * @author LevelX2
 */
public class VastwoodHydra extends CardImpl<VastwoodHydra> {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creatures you control");
    static {
        filter.add(new ControllerPredicate(TargetController.YOU));
    }

    public VastwoodHydra(UUID ownerId) {
        super(ownerId, 198, "Vastwood Hydra", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{X}{G}{G}");
        this.expansionSetCode = "M14";
        this.subtype.add("Hydra");

        this.color.setGreen(true);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Vastwood Hydra enters the battlefield with X +1/+1 counters on it.
        this.addAbility(new EntersBattlefieldAbility(new VastwoodHydraEffect()));
        // When Vastwood Hydra dies, you may distribute a number of +1/+1 counters equal to the number of +1/+1 counters on Vastwood Hydra among any number of creatures you control.
        Ability ability = new DiesTriggeredAbility(new VastwoodHydraDistributeEffect(), true);
        ability.addTarget(new TargetCreaturePermanentAmount(new CountersCount(CounterType.P1P1), filter));
        this.addAbility(ability);
    }

    public VastwoodHydra(final VastwoodHydra card) {
        super(card);
    }

    @Override
    public VastwoodHydra copy() {
        return new VastwoodHydra(this);
    }
}

class VastwoodHydraEffect extends OneShotEffect<VastwoodHydraEffect> {

    public VastwoodHydraEffect() {
        super(Outcome.BoostCreature);
        staticText = "with X +1/+1 counters on it";
    }

    public VastwoodHydraEffect(final VastwoodHydraEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent != null) {
            Object obj = getValue(EntersBattlefieldEffect.SOURCE_CAST_SPELL_ABILITY);
            if (obj != null && obj instanceof SpellAbility) {
                int amount = ((SpellAbility) obj).getManaCostsToPay().getX();
                if (amount > 0) {
                    permanent.addCounters(CounterType.P1P1.createInstance(amount), game);
                }
            }
        }
        return true;
    }

    @Override
    public VastwoodHydraEffect copy() {
        return new VastwoodHydraEffect(this);
    }

}

class VastwoodHydraDistributeEffect extends OneShotEffect<VastwoodHydraDistributeEffect> {

    public VastwoodHydraDistributeEffect() {
        super(Outcome.BoostCreature);
                this.staticText = "distribute a number of +1/+1 counters equal to the number of +1/+1 counters on {this} among any number of creatures you control";
    }

    public VastwoodHydraDistributeEffect(final VastwoodHydraDistributeEffect effect) {
        super(effect);
    }

    @Override
    public VastwoodHydraDistributeEffect copy() {
        return new VastwoodHydraDistributeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        if (source.getTargets().size() > 0) {
            Target multiTarget = source.getTargets().get(0);
            for (UUID target: multiTarget.getTargets()) {
                Permanent permanent = game.getPermanent(target);
                if (permanent != null) {
                    permanent.addCounters(CounterType.P1P1.createInstance(multiTarget.getTargetAmount(target)), game);
                }
            }
        }
        return true;
    }

}
