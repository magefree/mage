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
package mage.sets.coldsnap;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.effects.common.ManaEffect;
import mage.cards.CardImpl;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author Plopman
 */
public class RiteOfFlame extends CardImpl<RiteOfFlame> {

    public RiteOfFlame(UUID ownerId) {
        super(ownerId, 96, "Rite of Flame", Rarity.COMMON, new CardType[]{CardType.SORCERY}, "{R}");
        this.expansionSetCode = "CSP";

        this.color.setRed(true);

        // Add {R}{R} to your mana pool, then add {R} to your mana pool for each card named Rite of Flame in each graveyard.
        this.getSpellAbility().addEffect(new RiteOfFlameManaEffect());
    }

    public RiteOfFlame(final RiteOfFlame card) {
        super(card);
    }

    @Override
    public RiteOfFlame copy() {
        return new RiteOfFlame(this);
    }
}

class RiteOfFlameManaEffect extends ManaEffect<RiteOfFlameManaEffect> {
    private static final FilterCard filter = new FilterCard();

    static {
        filter.add(new NamePredicate("Rite of Flame"));
    }

    RiteOfFlameManaEffect() {
        super();
        staticText = "Add {R}{R} to your mana pool, then add {R} to your mana pool for each card named Rite of Flame in each graveyard";
    }

    RiteOfFlameManaEffect(final RiteOfFlameManaEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if(controller != null){
            int count = 0;
            for(Player player : game.getPlayers().values()){
                if(player != null){
                    count += player.getGraveyard().count(filter, game);
                }
            }
            controller.getManaPool().addMana(Mana.RedMana(count + 2), game, source);
        }
        return false;
    }

    @Override
    public RiteOfFlameManaEffect copy() {
        return new RiteOfFlameManaEffect(this);
    }
}