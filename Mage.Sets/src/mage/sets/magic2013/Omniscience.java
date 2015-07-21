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
package mage.sets.magic2013;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.CompoundCondition;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.SourceIsSpellCondition;
import mage.abilities.costs.AlternativeCostSourceAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.SplitCardHalf;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.SubLayer;
import mage.constants.Zone;
import mage.filter.common.FilterNonlandCard;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.players.Player;

/**
 *
 * @author North
 */
public class Omniscience extends CardImpl {

    public Omniscience(UUID ownerId) {
        super(ownerId, 63, "Omniscience", Rarity.MYTHIC, new CardType[]{CardType.ENCHANTMENT}, "{7}{U}{U}{U}");
        this.expansionSetCode = "M13";

        // You may cast nonland cards from your hand without paying their mana costs.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new OmniscienceCastingEffect()));
    }

    public Omniscience(final Omniscience card) {
        super(card);
    }

    @Override
    public Omniscience copy() {
        return new Omniscience(this);
    }
}

class OmniscienceCastingEffect extends ContinuousEffectImpl {

    public OmniscienceCastingEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Detriment);
        staticText = "You may cast nonland cards from your hand without paying their mana costs";
    }

    public OmniscienceCastingEffect(final OmniscienceCastingEffect effect) {
        super(effect);
    }

    @Override
    public OmniscienceCastingEffect copy() {
        return new OmniscienceCastingEffect(this);
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            controller.getAlternativeSourceCosts().add(new AlternativeCostSourceAbility(
                    null, new CompoundCondition(SourceIsSpellCondition.getInstance(), new IsBeingCastFromHandCondition()), null, new FilterNonlandCard(), true));
            return true;
        }
        return false;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public boolean hasLayer(Layer layer) {
        return layer == Layer.RulesEffects;
    }
}

class IsBeingCastFromHandCondition implements Condition {

    @Override
    public boolean apply(Game game, Ability source) {
        MageObject object = game.getObject(source.getSourceId());
        if (object instanceof SplitCardHalf) {
            UUID splitCardId = ((Card) object).getMainCard().getId();
            object = game.getObject(splitCardId);
        }
        if (object instanceof Spell) { // needed to check if it can be cast by alternate cost
            Spell spell = (Spell) object;
            return spell.getFromZone() == Zone.HAND;
        }
        if (object instanceof Card) { // needed for the check what's playable
            Card card = (Card) object;
            return game.getPlayer(card.getOwnerId()).getHand().get(card.getId(), game) != null;
        }
        return false;
    }

}
