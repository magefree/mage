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
package mage.sets.newphyrexia;

import mage.ConditionalMana;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.condition.Condition;
import mage.abilities.effects.common.BasicManaEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.game.Game;

import java.util.UUID;

/**
 *
 * @author North
 */
public class Geosurge extends CardImpl<Geosurge> {

    public Geosurge(UUID ownerId) {
        super(ownerId, 85, "Geosurge", Rarity.UNCOMMON, new CardType[]{CardType.SORCERY}, "{R}{R}{R}{R}");
        this.expansionSetCode = "NPH";

        this.color.setRed(true);

        this.getSpellAbility().addEffect(new BasicManaEffect(new GeosurgeConditionalMana()));
    }

    public Geosurge(final Geosurge card) {
        super(card);
    }

    @Override
    public Geosurge copy() {
        return new Geosurge(this);
    }
}

class GeosurgeConditionalMana extends ConditionalMana {

    public GeosurgeConditionalMana() {
        super(Mana.RedMana(7));
        staticText = "Spend this mana only to cast artifact or creature spells";
        addCondition(new GeosurgeManaCondition());
    }
}

class GeosurgeManaCondition implements Condition {

    @Override
    public boolean apply(Game game, Ability source) {
        if (source instanceof SpellAbility) {
            Card card = game.getCard(source.getSourceId());
            if (card != null && (card.getCardType().contains(CardType.ARTIFACT) || card.getCardType().contains(CardType.CREATURE))) {
                return true;
            }
        }
        return false;
    }
}
