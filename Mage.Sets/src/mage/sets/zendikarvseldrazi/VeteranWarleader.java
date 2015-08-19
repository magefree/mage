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
package mage.sets.zendikarvseldrazi;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.effects.common.continuous.SetPowerToughnessSourceEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.TrampleAbility;
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
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.permanent.AnotherPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author fireshoes
 */
public class VeteranWarleader extends CardImpl {
    
    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("another untapped Ally");

    static {
        filter.add(new AnotherPredicate());
        filter.add(new SubtypePredicate("Ally"));
    }

    public VeteranWarleader(UUID ownerId) {
        super(ownerId, 27, "Veteran Warleader", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{1}{G}{W}");
        this.expansionSetCode = "DDP";
        this.subtype.add("Human");
        this.subtype.add("Soldier");
        this.subtype.add("Ally");
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Veteran Warleader's power and toughness are each equal to the number of creatures you control.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new SetPowerToughnessSourceEffect(
                new PermanentsOnBattlefieldCount(new FilterControlledCreaturePermanent()), Duration.EndOfGame)));
        
        // Tap another untapped Ally you control: Veteran Warleader gains your choice of first strike, vigilance, or trample until end of turn.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, 
                new VeteranWarleaderEffect(), new TapTargetCost(new TargetControlledPermanent(1, 1, filter, true))));
    }

    public VeteranWarleader(final VeteranWarleader card) {
        super(card);
    }

    @Override
    public VeteranWarleader copy() {
        return new VeteranWarleader(this);
    }
}

class VeteranWarleaderEffect extends OneShotEffect {
    VeteranWarleaderEffect() {
        super(Outcome.AddAbility);
        staticText = "{this} gains your choice of first strike, vigilance, or trample until end of turn";
    }

    VeteranWarleaderEffect(final VeteranWarleaderEffect effect) {
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
            abilities.add(FirstStrikeAbility.getInstance().getRule());
            abilities.add(VigilanceAbility.getInstance().getRule());
            abilities.add(TrampleAbility.getInstance().getRule());
            abilityChoice.setChoices(abilities);
            while (!abilityChoice.isChosen()) {
                controller.choose(Outcome.AddAbility, abilityChoice, game);
                if (!controller.canRespond()) {
                    return false;
                }
            }

            String chosen = abilityChoice.getChoice();
            Ability ability = null;
            if (FirstStrikeAbility.getInstance().getRule().equals(chosen)) {
                ability = FirstStrikeAbility.getInstance();
            } else if (VigilanceAbility.getInstance().getRule().equals(chosen)) {
                ability = VigilanceAbility.getInstance();
            } else if (TrampleAbility.getInstance().getRule().equals(chosen)) {
                ability = TrampleAbility.getInstance();
            }

            if (ability != null) {
                game.informPlayers(sourceObject.getLogName() + ": " + controller.getLogName() + " has chosen: " + chosen);
                ContinuousEffect effect = new GainAbilitySourceEffect(ability, Duration.EndOfTurn);
                game.addEffect(effect, source);
                return true;
            }
        }
        return false;
    }

    @Override
    public VeteranWarleaderEffect copy() {
        return new VeteranWarleaderEffect(this);
    }

}
