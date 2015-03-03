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
package mage.sets.fatereforged;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalContinousEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.common.FilterInstantOrSorceryCard;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public class BloodfireEnforcers extends CardImpl {

    public BloodfireEnforcers(UUID ownerId) {
        super(ownerId, 93, "Bloodfire Enforcers", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{3}{R}");
        this.expansionSetCode = "FRF";
        this.subtype.add("Human");
        this.subtype.add("Monk");
        this.power = new MageInt(5);
        this.toughness = new MageInt(2);

        // Bloodfire Enforcers has first strike and trample as long as an instant card and a sorcery card are in your graveyard.
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD,
                new ConditionalContinousEffect(new GainAbilitySourceEffect(FirstStrikeAbility.getInstance(), Duration.WhileOnBattlefield),
                new BloodfireEnforcersCondition(), "{this} has first strike"));
        ability.addEffect(new ConditionalContinousEffect(new GainAbilitySourceEffect(TrampleAbility.getInstance(), Duration.WhileOnBattlefield),
                new BloodfireEnforcersCondition(), "and trample as long as an instant card and a sorcery card are in your graveyard"));
        this.addAbility(ability);
     
    }

    public BloodfireEnforcers(final BloodfireEnforcers card) {
        super(card);
    }

    @Override
    public BloodfireEnforcers copy() {
        return new BloodfireEnforcers(this);
    }
}


class BloodfireEnforcersCondition implements Condition {

    private static final FilterInstantOrSorceryCard filter = new FilterInstantOrSorceryCard();

    @Override
    public boolean apply(Game game, Ability source) {
        boolean instantFound = false;
        boolean sorceryFound = false;
        Player player = game.getPlayer(source.getControllerId());
        if  (player != null) {
            for(Card card : player.getGraveyard().getCards(game)) {
                if (card.getCardType().contains(CardType.INSTANT)) {
                    if (sorceryFound) {
                        return true;
                    }
                    instantFound = true;                    
                } else if (card.getCardType().contains(CardType.SORCERY)) {
                    if (instantFound) {
                        return true;
                    }
                    sorceryFound = true;
                }
            }            
        }
        return false;
    }
}