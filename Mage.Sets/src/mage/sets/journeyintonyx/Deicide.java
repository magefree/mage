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
package mage.sets.journeyintonyx;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.common.search.SearchTargetGraveyardHandLibraryForCardNameAndExileEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetEnchantmentPermanent;

/**
 *
 * @author LevelX2
 */
public class Deicide extends CardImpl {
    
    

    public Deicide(UUID ownerId) {
        super(ownerId, 7, "Deicide", Rarity.RARE, new CardType[]{CardType.INSTANT}, "{1}{W}");
        this.expansionSetCode = "JOU";


        // Exile target enchantment. If the exiled card is a God card, search its controller's graveyard, hand, and library for any number of cards with the same name as that card and exile them, then that player shuffles his or her library.
        this.getSpellAbility().addEffect(new DeicideExileEffect());
        this.getSpellAbility().addTarget(new TargetEnchantmentPermanent());
    }

    public Deicide(final Deicide card) {
        super(card);
    }

    @Override
    public Deicide copy() {
        return new Deicide(this);
    }
}

class DeicideExileEffect extends SearchTargetGraveyardHandLibraryForCardNameAndExileEffect {

    public DeicideExileEffect() {
        super(true, "its controller's","any number of cards with the same name as that card");
    }

    public DeicideExileEffect(final DeicideExileEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
       Player controller = game.getPlayer(source.getControllerId());
        Card sourceCard = game.getCard(source.getSourceId());
        if (controller != null && sourceCard != null) {
            Permanent targetEnchantment = game.getPermanent(getTargetPointer().getFirst(game, source));
            if (targetEnchantment != null) {
                controller.moveCardToExileWithInfo(targetEnchantment, null, "", source.getSourceId(), game, Zone.BATTLEFIELD, true);
                // 4/26/2014 	
                // Deicide looks at the card in exile, not the permanent that was exiled, to determine 
                // if it is a God. For each of the Gods in the Theros block, it won’t matter what your 
                // devotion to its color(s) was. The card is a God card when not on the battlefield.
                Card cardInExile = game.getExile().getCard(targetEnchantment.getId(), game);
                if (cardInExile != null && cardInExile.hasSubtype("God")) {
                    Player enchantmentController = game.getPlayer(targetEnchantment.getControllerId());                
                    return super.applySearchAndExile(game, source, cardInExile.getName(), enchantmentController.getId());
                }
            }
        }
        return false;
    }

    @Override
    public DeicideExileEffect copy() {
        return new DeicideExileEffect(this);
    }

    @Override
    public String getText(Mode mode) {
        StringBuilder sb = new StringBuilder();
        sb.append("Exile target enchantment. If the exiled card is a God card, ");
        sb.append(super.getText(mode));
        return sb.toString();
    }

}