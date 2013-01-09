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

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.Constants.TargetController;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfCombatTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continious.GainAbilityControlledEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.choices.Choice;
import mage.choices.ChoiceImpl;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public class AngelicSkirmisher extends CardImpl<AngelicSkirmisher> {

    private static final Choice abilityChoice = new ChoiceImpl(true);
    private static final Set<String> abilityChoices = new HashSet<String>();
    static {
        abilityChoice.setMessage("Choose ability for your creatures");
        abilityChoices.add("First strike");
        abilityChoices.add("Vigilance");
        abilityChoices.add("Lifelink");
        abilityChoice.setChoices(abilityChoices);
    }

    public AngelicSkirmisher(UUID ownerId) {
        super(ownerId, 3, "Angelic Skirmisher", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{4}{W}{W}");
        this.expansionSetCode = "GTC";
        this.subtype.add("Angel");

        this.color.setWhite(true);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // At the beginning of each combat, choose first strike, vigilance or lifelink. Creatures you control gain that ability until end of turn.
        Ability ability = new BeginningOfCombatTriggeredAbility(new AngelicSkirmisherEffect(), TargetController.ANY, false);
        ability.addChoice(abilityChoice);
        this.addAbility(ability);
    }

    public AngelicSkirmisher(final AngelicSkirmisher card) {
        super(card);
    }

    @Override
    public AngelicSkirmisher copy() {
        return new AngelicSkirmisher(this);
    }
}

class AngelicSkirmisherEffect extends OneShotEffect<AngelicSkirmisherEffect> {
    AngelicSkirmisherEffect() {
        super(Constants.Outcome.AddAbility);
        staticText = "choose first strike, vigilance or lifelink. Creatures you control gain that ability until end of turn";
    }

    AngelicSkirmisherEffect(final AngelicSkirmisherEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Choice abilityChoice = source.getChoices().get(0);
        Player controller = game.getPlayer(source.getControllerId());
        Permanent sourcePermanent = game.getPermanent(source.getSourceId());
        if (controller != null && sourcePermanent != null && abilityChoice != null && abilityChoice.isChosen()) {
            Ability ability = null;
            if (abilityChoice.getChoice().equals("First strike")) {
                ability = FirstStrikeAbility.getInstance();
            } else if (abilityChoice.getChoice().equals("Vigilance")) {
                ability = VigilanceAbility.getInstance();
            } else if (abilityChoice.getChoice().equals("Lifelink")) {
                ability = LifelinkAbility.getInstance();
            }
            if (ability != null) {
                GainAbilityControlledEffect effect = new GainAbilityControlledEffect(ability, Constants.Duration.EndOfTurn, new FilterControlledCreaturePermanent());
                game.addEffect(effect, source);
                game.informPlayers(new StringBuilder(sourcePermanent.getName())
                        .append(": ")
                        .append(controller.getName())
                        .append(" has chosen ")
                        .append(abilityChoice.getChoice().toLowerCase()).toString());
                return true;
            }
        }
        return false;
    }

    @Override
    public AngelicSkirmisherEffect copy() {
        return new AngelicSkirmisherEffect(this);
    }
}