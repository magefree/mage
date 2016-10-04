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
package mage.cards.l;

import java.util.HashSet;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.SunburstAbility;
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
import mage.players.Player;

/**
 *
 * @author LoneFox
 */
public class LunarAvenger extends CardImpl {

    public LunarAvenger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{7}");
        this.subtype.add("Golem");
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Sunburst
        this.addAbility(new SunburstAbility(this));
        // Remove a +1/+1 counter from Lunar Avenger: Lunar Avenger gains your choice of flying, first strike, or haste until end of turn.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new LunarAvengerEffect(),
                new RemoveCountersSourceCost(CounterType.P1P1.createInstance(1))));
    }

    public LunarAvenger(final LunarAvenger card) {
        super(card);
    }

    @Override
    public LunarAvenger copy() {
        return new LunarAvenger(this);
    }
}

class LunarAvengerEffect extends OneShotEffect {

    private static final HashSet<String> choices = new HashSet<>();

    static {
        choices.add("Flying");
        choices.add("First Strike");
        choices.add("Haste");
    }

    public LunarAvengerEffect() {
        super(Outcome.AddAbility);
        staticText = "{this} gains your choice of flying, first strike, or haste until end of turn";
    }

    public LunarAvengerEffect(final LunarAvengerEffect effect) {
        super(effect);
    }

    @Override
    public LunarAvengerEffect copy() {
        return new LunarAvengerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Choice choice = new ChoiceImpl(true);
            choice.setMessage("Choose ability to add");
            choice.setChoices(choices);
            while (!controller.choose(outcome, choice, game)) {
                if (!controller.canRespond()) {
                    return false;
                }
            }

            Ability gainedAbility;
            String chosen = choice.getChoice();
            if (chosen.equals("Flying")) {
                gainedAbility = FlyingAbility.getInstance();
            } else if (chosen.equals("First strike")) {
                gainedAbility = FirstStrikeAbility.getInstance();
            } else {
                gainedAbility = HasteAbility.getInstance();
            }

            game.addEffect(new GainAbilitySourceEffect(gainedAbility, Duration.EndOfTurn), source);
            return true;
        }
        return false;
    }
}
