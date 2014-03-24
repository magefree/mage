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
package mage.sets.futuresight;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlashAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetSpellOrPermanent;

/**
 *
 * @author LevelX2
 */
public class VenserShaperSavant extends CardImpl<VenserShaperSavant> {

    public VenserShaperSavant(UUID ownerId) {
        super(ownerId, 46, "Venser, Shaper Savant", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{2}{U}{U}");
        this.expansionSetCode = "FUT";
        this.supertype.add("Legendary");
        this.subtype.add("Human");
        this.subtype.add("Wizard");

        this.color.setBlue(true);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flash
        this.addAbility(FlashAbility.getInstance());
        // When Venser, Shaper Savant enters the battlefield, return target spell or permanent to its owner's hand.
        Ability ability = new EntersBattlefieldTriggeredAbility(new VenserShaperSavantEffect(), false);
        Target target = new TargetSpellOrPermanent();
        target.setRequired(true);
        ability.addTarget(target);
        this.addAbility(ability);
    }

    public VenserShaperSavant(final VenserShaperSavant card) {
        super(card);
    }

    @Override
    public VenserShaperSavant copy() {
        return new VenserShaperSavant(this);
    }
}

class VenserShaperSavantEffect extends OneShotEffect<VenserShaperSavantEffect> {

    public VenserShaperSavantEffect() {
        super(Outcome.ReturnToHand);
        this.staticText = "return target spell or permanent to its owner's hand";
    }

    public VenserShaperSavantEffect(final VenserShaperSavantEffect effect) {
        super(effect);
    }

    @Override
    public VenserShaperSavantEffect copy() {
        return new VenserShaperSavantEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Permanent permanent = game.getPermanent(this.getTargetPointer().getFirst(game, source));
            if (permanent != null) {
                return controller.moveCardToHandWithInfo(permanent, source.getSourceId(), game, Zone.BATTLEFIELD);
            }

            /**
             * 01.05.2007 	If a spell is returned to its owner's hand, it's removed from
             * the stack and thus will not resolve. The spell isn't countered; it just no longer exists.
             * 01.05.2007 	If a copy of a spell is returned to its owner's hand, it's moved there,
             * then it will cease to exist as a state-based action.
             * 01.05.2007 	If Venser's enters-the-battlefield ability targets a spell cast with flashback,
             * that spell will be exiled instead of returning to its owner's hand.
             */

            Spell spell = game.getStack().getSpell(this.getTargetPointer().getFirst(game, source));
            if (spell != null) {
                Card card = null;
                if (!spell.isCopy()) {
                    card = spell.getCard();
                }
                game.getStack().remove(spell);
                if (card != null) {
                    controller.moveCardToHandWithInfo(card, source.getSourceId(), game, Zone.STACK);
                }
                return true;
            }

        }
        return false;
    }
}
