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

package mage.sets.betrayersofkamigawa;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastTriggeredAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.RemoveVariableCountersSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.ManaEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.mana.BasicManaAbility;
import mage.cards.CardImpl;
import mage.choices.ChoiceColor;
import mage.counters.CounterType;
import mage.filter.common.FilterSpiritOrArcaneCard;
import mage.game.Game;
import mage.players.Player;

/**
 * @author LevelX2
 */
public class PetalmaneBaku extends CardImpl<PetalmaneBaku> {

    private static final FilterSpiritOrArcaneCard filter = new FilterSpiritOrArcaneCard();

    public PetalmaneBaku(UUID ownerId) {
        super(ownerId, 139, "Petalmane Baku", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{1}{G}");
        this.expansionSetCode = "BOK";
        this.subtype.add("Spirit");
        this.color.setGreen(true);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);
        
        // Whenever you cast a Spirit or Arcane spell, you may put a ki counter on Skullmane Baku.
        this.addAbility(new SpellCastTriggeredAbility(new AddCountersSourceEffect(CounterType.KI.createInstance()), filter, true));

        // {1}, Remove X ki counters from Petalmane Baku: Add X mana of any one color to your mana pool.
        Ability ability = new PetalmaneBakuManaAbility();
        ability.addCost(new GenericManaCost(1));
        ability.addCost(new RemoveVariableCountersSourceCost(CounterType.KI.createInstance(1)));
        this.addAbility(ability);
    }

    public PetalmaneBaku(final PetalmaneBaku card) {
        super(card);
    }

    @Override
    public PetalmaneBaku copy() {
        return new PetalmaneBaku(this);
    }
    
    private class PetalmaneBakuManaAbility extends BasicManaAbility<PetalmaneBakuManaAbility> {
        PetalmaneBakuManaAbility() {
            super(new PetalmaneBakuManaEffect());
            this.addChoice(new ChoiceColor());
        }

        PetalmaneBakuManaAbility(final PetalmaneBakuManaAbility ability) {
            super(ability);
        }

        @Override
        public PetalmaneBakuManaAbility copy() {
            return new PetalmaneBakuManaAbility(this);
        }
}

    private class PetalmaneBakuManaEffect extends ManaEffect<PetalmaneBakuManaEffect> {

        PetalmaneBakuManaEffect() {
            super();
            staticText = "Add X mana of any one color to your mana pool";
        }

        PetalmaneBakuManaEffect(final PetalmaneBakuManaEffect effect) {
            super(effect);
        }

        @Override
        public boolean apply(Game game, Ability source) {
            ChoiceColor choice = (ChoiceColor) source.getChoices().get(0);
            Player player = game.getPlayer(source.getControllerId());

            if (player != null && choice != null) {
                int numberOfMana = 0;
                for (Cost cost : source.getCosts()) {
                    if (cost instanceof RemoveVariableCountersSourceCost) {
                        numberOfMana = ((RemoveVariableCountersSourceCost)cost).getAmount();
                    }
                }                
                if (choice.getColor().isBlack()) {
                    player.getManaPool().addMana(new Mana(0, 0, 0, 0, numberOfMana, 0, 0), game, source);
                    return true;
                } else if (choice.getColor().isBlue()) {
                    player.getManaPool().addMana(new Mana(0, 0, numberOfMana, 0, 0, 0, 0), game, source);
                    return true;
                } else if (choice.getColor().isRed()) {
                    player.getManaPool().addMana(new Mana(numberOfMana, 0, 0, 0, 0, 0, 0), game, source);
                    return true;
                } else if (choice.getColor().isGreen()) {
                    player.getManaPool().addMana(new Mana(0, numberOfMana, 0, 0, 0, 0, 0), game, source);
                    return true;
                } else if (choice.getColor().isWhite()) {
                    player.getManaPool().addMana(new Mana(0, 0, 0, numberOfMana, 0, 0, 0), game, source);
                    return true;
                }
            }
            return false;
        }

        @Override
        public PetalmaneBakuManaEffect copy() {
            return new PetalmaneBakuManaEffect(this);
        }
    }
}