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
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.game.permanent.token.Token;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author LevelX2
 */
public class RitualOfTheReturned extends CardImpl<RitualOfTheReturned> {

    public RitualOfTheReturned(UUID ownerId) {
        super(ownerId, 80, "Ritual of the Returned", Rarity.UNCOMMON, new CardType[]{CardType.INSTANT}, "{3}{B}");
        this.expansionSetCode = "JOU";

        this.color.setBlack(true);

        // Exile target creature card from your graveyard. Put a black Zombie creature token onto the battlefield with power equal to the exiled card's power and toughness equal to the exiled card's toughness.
        this.getSpellAbility().addEffect(new RitualOfTheReturnedExileEffect());
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(new FilterCreatureCard(), true));
    }

    public RitualOfTheReturned(final RitualOfTheReturned card) {
        super(card);
    }

    @Override
    public RitualOfTheReturned copy() {
        return new RitualOfTheReturned(this);
    }
}

class RitualOfTheReturnedExileEffect extends OneShotEffect<RitualOfTheReturnedExileEffect> {
    
    public RitualOfTheReturnedExileEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "Exile target creature card from your graveyard. Put a black Zombie creature token onto the battlefield with power equal to the exiled card's power and toughness equal to the exiled card's toughness";
    }
    
    public RitualOfTheReturnedExileEffect(final RitualOfTheReturnedExileEffect effect) {
        super(effect);
    }
    
    @Override
    public RitualOfTheReturnedExileEffect copy() {
        return new RitualOfTheReturnedExileEffect(this);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Card card = game.getCard(getTargetPointer().getFirst(game, source));
            if (card != null && game.getState().getZone(card.getId()).equals(Zone.GRAVEYARD)) {
                controller.moveCardToExileWithInfo(card, null, null, source.getSourceId(), game, Zone.GRAVEYARD);
                return new CreateTokenEffect(
                        new RitualOfTheReturnedZombieToken(card.getPower().getValue(), card.getToughness().getValue())).apply(game, source);
            }
        }
        return false;
    }
}

class RitualOfTheReturnedZombieToken extends Token {

    public RitualOfTheReturnedZombieToken(int power, int toughness) {
        super("Zombie", "black Zombie creature token onto the battlefield with power equal to the exiled card's power and toughness equal to the exiled card's toughness");
        this.setOriginalExpansionSetCode("JOU");
        cardType.add(CardType.CREATURE);
        color = ObjectColor.BLACK;
        subtype.add("Zombie");
        this.power = new MageInt(power);
        this.toughness = new MageInt(toughness);
    }

}
