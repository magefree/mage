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
package mage.sets.kaladesh;

import java.util.HashSet;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.PayEnergyCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.effects.common.counter.GetEnergyCountersControllerEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.choices.Choice;
import mage.choices.ChoiceImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public class MultiformWonder extends CardImpl {

    public MultiformWonder(UUID ownerId) {
        super(ownerId, 223, "Multiform Wonder", Rarity.RARE, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{5}");
        this.expansionSetCode = "KLD";
        this.subtype.add("Construct");
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // When Multiform Wonder enters the battlefield, you get {E}{E}{E}.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new GetEnergyCountersControllerEffect(3), false));

        // Pay {E}: Multiform Wonder gains your choice of flying, vigilance, or lifelink until end of turn.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new MultiformWonderEffect(), new PayEnergyCost(1)));

        // Pay {E}: Multiform Wonder gets +2/-2 or -2/+2 until end of turn.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new MultiformWonder2Effect(), new PayEnergyCost(1)));
    }

    public MultiformWonder(final MultiformWonder card) {
        super(card);
    }

    @Override
    public MultiformWonder copy() {
        return new MultiformWonder(this);
    }
}

class MultiformWonderEffect extends OneShotEffect {

    private static final HashSet<String> choices = new HashSet<>();

    static {
        choices.add("Flying");
        choices.add("Vigilance");
        choices.add("Lifelink");
    }

    public MultiformWonderEffect() {
        super(Outcome.AddAbility);
        staticText = "{this} gains your choice of flying, vigilance, or lifelink until end of turn";
    }

    public MultiformWonderEffect(final MultiformWonderEffect effect) {
        super(effect);
    }

    @Override
    public MultiformWonderEffect copy() {
        return new MultiformWonderEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Choice choice = new ChoiceImpl(true);
            choice.setMessage("Choose ability to add");
            choice.setChoices(choices);
            while (!controller.choose(outcome, choice, game)) {
                if (controller.canRespond()) {
                    return false;
                }
            }

            Ability gainedAbility;
            String chosen = choice.getChoice();
            switch (chosen) {
                case "Flying":
                    gainedAbility = FlyingAbility.getInstance();
                    break;
                case "Vigilance":
                    gainedAbility = VigilanceAbility.getInstance();
                    break;
                default:
                    gainedAbility = LifelinkAbility.getInstance();
                    break;
            }

            game.addEffect(new GainAbilitySourceEffect(gainedAbility, Duration.EndOfTurn), source);
            return true;
        }
        return false;
    }

}

class MultiformWonder2Effect extends OneShotEffect {

    private static final HashSet<String> choices = new HashSet<>();
    private BoostSourceEffect boost;

    static {
        choices.add("-2/+2");
        choices.add("+2/-2");
    }

    public MultiformWonder2Effect() {
        super(Outcome.AddAbility);

        staticText = "{this} gains either +2/-2 or -2/+2 until end of turn";
    }

    public MultiformWonder2Effect(final MultiformWonder2Effect effect) {
        super(effect);
    }

    @Override
    public MultiformWonder2Effect copy() {
        return new MultiformWonder2Effect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Choice choice = new ChoiceImpl(true);
            choice.setMessage("Choose mode");
            choice.setChoices(choices);
            while (!controller.choose(outcome, choice, game)) {
                if (controller.canRespond()) {
                    return false;
                }
            }

            String chosen = choice.getChoice();
            switch (chosen) {
                case "+2/-2":
                    boost = new BoostSourceEffect(2, -2, Duration.EndOfTurn);
                    break;
                default: //"-2/+2":
                    boost = new BoostSourceEffect(-2, +2, Duration.EndOfTurn);
                    break;
            }
            game.addEffect(boost, source);
            return true;
        }
        return false;
    }
}