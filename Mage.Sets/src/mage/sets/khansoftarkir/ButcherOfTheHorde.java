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
package mage.sets.khansoftarkir;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HasteAbility;
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
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.permanent.AnotherPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public class ButcherOfTheHorde extends CardImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("another creature");

    static {
        filter.add(new AnotherPredicate());
    }

    public ButcherOfTheHorde(UUID ownerId) {
        super(ownerId, 168, "Butcher of the Horde", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{1}{R}{W}{B}");
        this.expansionSetCode = "KTK";
        this.subtype.add("Demon");

        this.color.setRed(true);
        this.color.setBlack(true);
        this.color.setWhite(true);
        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Sacrifice another creature: Butcher of the Horde gains your choice of vigilance, lifelink, or haste until end of turn.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new ButcherOfTheHordeEffect(), new SacrificeTargetCost(new TargetControlledCreaturePermanent(1,1,filter, false))));
    }

    public ButcherOfTheHorde(final ButcherOfTheHorde card) {
        super(card);
    }

    @Override
    public ButcherOfTheHorde copy() {
        return new ButcherOfTheHorde(this);
    }
}

class ButcherOfTheHordeEffect extends OneShotEffect {
    ButcherOfTheHordeEffect() {
        super(Outcome.AddAbility);
        staticText = "{this} gains your choice of vigilance, lifelink, or haste until end of turn";
    }

    ButcherOfTheHordeEffect(final ButcherOfTheHordeEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source.getSourceId());
        if (sourceObject != null && controller != null) {
            Choice abilityChoice = new ChoiceImpl();
            abilityChoice.setMessage("Choose an ability to add");

            Set<String> abilities = new HashSet<>();
            abilities.add(VigilanceAbility.getInstance().getRule());
            abilities.add(LifelinkAbility.getInstance().getRule());
            abilities.add(HasteAbility.getInstance().getRule());
            abilityChoice.setChoices(abilities);
            while (!abilityChoice.isChosen()) {
                controller.choose(Outcome.AddAbility, abilityChoice, game);
                if (!controller.isInGame()) {
                    return false;
                }
            }

            String chosen = abilityChoice.getChoice();
            Ability ability = null;
            if (VigilanceAbility.getInstance().getRule().equals(chosen)) {
                ability = VigilanceAbility.getInstance();
            } else if (LifelinkAbility.getInstance().getRule().equals(chosen)) {
                ability = LifelinkAbility.getInstance();
            } else if (HasteAbility.getInstance().getRule().equals(chosen)) {
                ability = HasteAbility.getInstance();
            }

            if (ability != null) {
                game.informPlayers(sourceObject.getLogName() + ": " + controller.getName() + " has chosen: " + chosen);
                ContinuousEffect effect = new GainAbilitySourceEffect(ability, Duration.EndOfTurn);
                game.addEffect(effect, source);
                return true;
            }
        }
        return false;
    }

    @Override
    public ButcherOfTheHordeEffect copy() {
        return new ButcherOfTheHordeEffect(this);
    }

}