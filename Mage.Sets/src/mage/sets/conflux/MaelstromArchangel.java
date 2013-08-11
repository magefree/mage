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
package mage.sets.conflux;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.filter.FilterCard;
import mage.filter.common.FilterNonlandCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetCardInHand;

/**
 *
 * @author jeffwadsworth
 */
public class MaelstromArchangel extends CardImpl<MaelstromArchangel> {

    public MaelstromArchangel(UUID ownerId) {
        super(ownerId, 115, "Maelstrom Archangel", Rarity.MYTHIC, new CardType[]{CardType.CREATURE}, "{W}{U}{B}{R}{G}");
        this.expansionSetCode = "CON";
        this.subtype.add("Angel");

        this.color.setRed(true);
        this.color.setBlue(true);
        this.color.setGreen(true);
        this.color.setBlack(true);
        this.color.setWhite(true);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        
        // Whenever Maelstrom Archangel deals combat damage to a player, you may cast a nonland card from your hand without paying its mana cost.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new MaelstromArchangelCastEffect(), false));
        
    }

    public MaelstromArchangel(final MaelstromArchangel card) {
        super(card);
    }

    @Override
    public MaelstromArchangel copy() {
        return new MaelstromArchangel(this);
    }
}

class MaelstromArchangelCastEffect extends OneShotEffect<MaelstromArchangelCastEffect> {

    private static final FilterCard filter = new FilterNonlandCard("nonland card from your hand");

    public MaelstromArchangelCastEffect() {
        super(Outcome.PlayForFree);
        this.staticText = "you may cast a nonland card from your hand without paying its mana cost";
    }

    public MaelstromArchangelCastEffect(final MaelstromArchangelCastEffect effect) {
        super(effect);
    }

    @Override
    public MaelstromArchangelCastEffect copy() {
        return new MaelstromArchangelCastEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Target target = new TargetCardInHand(filter);
            target.setRequired(true);
            if (target.canChoose(source.getSourceId(), controller.getId(), game) &&
              controller.chooseUse(outcome, "Cast a nonland card from your hand without paying its mana cost?", game)) {
                Card cardToCast = null;
                boolean cancel = false;
                while (!cancel) {
                    if (controller.chooseTarget(outcome, target, source, game)) {
                        cardToCast = game.getCard(target.getFirstTarget());
                        if (cardToCast != null && cardToCast.getSpellAbility().canChooseTarget(game)) {
                            cancel = true;
                        }
                    } else {
                        cancel = true;
                    }
                }
                if (cardToCast != null) {
                    controller.cast(cardToCast.getSpellAbility(), game, true);
                }
            }
            return true;
        }
        return false;
    }
}
