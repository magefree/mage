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
package mage.sets.morningtide;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.abilityword.KinshipAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public class LeafCrownedElder extends CardImpl {

    public LeafCrownedElder(UUID ownerId) {
        super(ownerId, 128, "Leaf-Crowned Elder", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{2}{G}{G}");
        this.expansionSetCode = "MOR";
        this.subtype.add("Treefolk");
        this.subtype.add("Shaman");

        this.power = new MageInt(3);
        this.toughness = new MageInt(5);

        // Kinship - At the beginning of your upkeep, you may look at the top card of your library. If it shares a creature type with Leaf-Crowned Elder, you may reveal it. 
        // If you do, you may play that card without paying its mana cost.
        this.addAbility(new KinshipAbility(new LeafCrownedElderPlayEffect()));
        
    }

    public LeafCrownedElder(final LeafCrownedElder card) {
        super(card);
    }

    @Override
    public LeafCrownedElder copy() {
        return new LeafCrownedElder(this);
    }
}

class LeafCrownedElderPlayEffect extends OneShotEffect {

    public LeafCrownedElderPlayEffect() {
        super(Outcome.PlayForFree);
        staticText = "you may play that card without paying its mana cost";
    }

    public LeafCrownedElderPlayEffect(final LeafCrownedElderPlayEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Card card = game.getCard(getTargetPointer().getFirst(game, source));
        if (player != null && card != null) {            
            if (player.chooseUse(Outcome.PlayForFree, "Play " + card.getName() + " without paying its mana cost?", source, game)) {
                if (card.getCardType().contains(CardType.LAND)) {
                    // If the revealed card is a land, you can play it only if it's your turn and you haven't yet played a land this turn.
                    if (game.getActivePlayerId().equals(player.getId()) && player.canPlayLand()) {
                        player.playLand(card, game);
                    }
                } else {
                    player.cast(card.getSpellAbility(), game, true);
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public LeafCrownedElderPlayEffect copy() {
        return new LeafCrownedElderPlayEffect(this);
    }

}
