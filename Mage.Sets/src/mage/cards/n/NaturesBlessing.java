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
package mage.cards.n;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.BandingAbility;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.Choice;
import mage.choices.ChoiceImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author L_J
 */
public class NaturesBlessing extends CardImpl {

    public NaturesBlessing(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{G}{W}");

        // {G}{W}, Discard a card: Put a +1/+1 counter on target creature or that creature gains banding, first strike, or trample.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new NaturesBlessingEffect(), new ManaCostsImpl("{G}{W}"));
        ability.addCost(new DiscardCardCost());
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    public NaturesBlessing(final NaturesBlessing card) {
        super(card);
    }

    @Override
    public NaturesBlessing copy() {
        return new NaturesBlessing(this);
    }
}

class NaturesBlessingEffect extends OneShotEffect {

    private static final Set<String> choices = new HashSet<>();
    private Ability gainedAbility;

    static {
        choices.add("+1/+1 counter");
        choices.add("Banding");
        choices.add("First strike");
        choices.add("Trample");
    }

    public NaturesBlessingEffect() {
        super(Outcome.AddAbility);
        this.staticText = "Put a +1/+1 counter on target creature or that creature gains banding, first strike, or trample";
    }

    public NaturesBlessingEffect(final NaturesBlessingEffect effect) {
        super(effect);
    }

    @Override
    public NaturesBlessingEffect copy() {
        return new NaturesBlessingEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent targetPermanent = game.getPermanent(this.getTargetPointer().getFirst(game, source));
        if (controller != null && targetPermanent != null) {
            Choice choice = new ChoiceImpl(true);
            choice.setMessage("Choose one");
            choice.setChoices(choices);
            if (controller.choose(outcome, choice, game)) {
                switch (choice.getChoice()) {
                    case "Banding":
                        gainedAbility = BandingAbility.getInstance();
                        break;
                    case "First strike":
                        gainedAbility = FirstStrikeAbility.getInstance();
                        break;
                    case "Trample":
                        gainedAbility = TrampleAbility.getInstance();
                }
            }
            if (gainedAbility != null) {
                game.addEffect(new GainAbilityTargetEffect(gainedAbility, Duration.Custom), source);
            } else {
                targetPermanent.getCounters(game).addCounter(CounterType.P1P1.createInstance());
                game.informPlayers(controller.getLogName() + " puts a +1/+1 counter on " + targetPermanent.getLogName());
            }
            return true;
        }
        return false;
    }
}
