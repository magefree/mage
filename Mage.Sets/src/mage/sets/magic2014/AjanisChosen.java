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
package mage.sets.magic2014;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.Token;
import mage.players.Player;

/**
 *
 * @author Plopman
 */
public class AjanisChosen extends CardImpl<AjanisChosen> {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent();
    static {
        filter.add(new CardTypePredicate(CardType.ENCHANTMENT));
    }
    
    public AjanisChosen(UUID ownerId) {
        super(ownerId, 2, "Ajani's Chosen", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{2}{W}{W}");
        this.expansionSetCode = "M14";
        this.subtype.add("Cat");
        this.subtype.add("Soldier");

        this.color.setWhite(true);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever an enchantment enters the battlefield under your control, put a 2/2 white Cat creature token onto the battlefield. If that enchantment is an Aura, you may attach it to the token.
        this.addAbility(new EntersBattlefieldAllTriggeredAbility(Zone.BATTLEFIELD, new AjanisChosenEffect(), filter, false, true, "Whenever an enchantment enters the battlefield under your control, put a 2/2 white Cat creature token onto the battlefield. If that enchantment is an Aura, you may attach it to the token"));
    }

    public AjanisChosen(final AjanisChosen card) {
        super(card);
    }

    @Override
    public AjanisChosen copy() {
        return new AjanisChosen(this);
    }
}

class AjanisChosenEffect extends OneShotEffect<AjanisChosenEffect> {


    public AjanisChosenEffect() {
        super(Outcome.PutCreatureInPlay);
        staticText = "put a 2/2 white Cat creature token onto the battlefield. If that enchantment is an Aura, you may attach it to the token";
    }

    public AjanisChosenEffect(final AjanisChosenEffect effect) {
        super(effect);
    }

    @Override
    public AjanisChosenEffect copy() {
        return new AjanisChosenEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Token token = new CatToken();
        if(token.putOntoBattlefield(1, game, source.getSourceId(), source.getControllerId())){
            Player player = game.getPlayer(source.getControllerId());
            Permanent enchantement = game.getPermanent(this.getTargetPointer().getFirst(game, source));
            Permanent tokenPermanent = game.getPermanent(token.getLastAddedToken());
            if(player != null && enchantement != null && tokenPermanent != null && enchantement.getSubtype().contains("Aura"))
            {
                Permanent oldCreature = game.getPermanent(enchantement.getAttachedTo());

                if(oldCreature != null && enchantement.getSpellAbility().getTargets().get(0).canTarget(tokenPermanent.getId(), game) && player.chooseUse(Outcome.Neutral, "Attach " + enchantement.getName() + " to the token ?", game))
                {
                    if(oldCreature.removeAttachment(enchantement.getId(), game)){
                        tokenPermanent.addAttachment(enchantement.getId(), game);
                    }
                }
            }
            return true;
        }
        return false;
    }

}


class CatToken extends Token {
    public CatToken() {
        super("Cat", "2/2 white Cat creature token");
        cardType.add(CardType.CREATURE);
        color = ObjectColor.WHITE;
        subtype.add("Cat");
        power = new MageInt(2);
        toughness = new MageInt(2);
    }
}