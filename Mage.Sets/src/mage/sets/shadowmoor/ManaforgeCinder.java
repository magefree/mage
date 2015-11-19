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
package mage.sets.shadowmoor;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;
import mage.MageInt;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.LimitedTimesPerTurnActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.choices.Choice;
import mage.choices.ChoiceImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author jeffwadsworth
 */
public class ManaforgeCinder extends CardImpl {

    public ManaforgeCinder(UUID ownerId) {
        super(ownerId, 191, "Manaforge Cinder", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{B/R}");
        this.expansionSetCode = "SHM";
        this.subtype.add("Elemental");
        this.subtype.add("Shaman");
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {1}: Add {B} or {R} to your mana pool. Activate this ability no more than three times each turn.
        this.addAbility(new LimitedTimesPerTurnActivatedAbility(Zone.BATTLEFIELD, new ManaforgeCinderManaEffect(), new ManaCostsImpl("{1}"), 3));
        
    }

    public ManaforgeCinder(final ManaforgeCinder card) {
        super(card);
    }

    @Override
    public ManaforgeCinder copy() {
        return new ManaforgeCinder(this);
    }
}



class ManaforgeCinderManaEffect extends OneShotEffect {

    public ManaforgeCinderManaEffect() {
        super(Outcome.PutManaInPool);
        this.staticText = "Add {B} or {R} to your mana pool";
    }

    public ManaforgeCinderManaEffect(final ManaforgeCinderManaEffect effect) {
        super(effect);
    }

    @Override
    public ManaforgeCinderManaEffect copy() {
        return new ManaforgeCinderManaEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Choice manaChoice = new ChoiceImpl();
            Set<String> choices = new LinkedHashSet<>();
            choices.add("Black");
            choices.add("Red");
            manaChoice.setChoices(choices);
            manaChoice.setMessage("Select black or red mana to add to your mana pool");
            Mana mana = new Mana();
            while (!controller.choose(Outcome.Benefit, manaChoice, game)) {
                if (!controller.canRespond()) {
                    return false;
                }
            }
            if (manaChoice.getChoice() == null) {
                return false;
            }
            switch (manaChoice.getChoice()) {
                case "Black":
                    mana.increaseBlack();
                    break;
                case "Red":
                    mana.increaseRed();
                    break;
            }
            controller.getManaPool().addMana(mana, game, source);
            return true;
        }
        return false;
    }
}
