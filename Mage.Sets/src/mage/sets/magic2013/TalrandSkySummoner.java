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
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.common.SpellCastTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.filter.Filter;
import mage.filter.FilterSpell;
import mage.game.permanent.token.DrakeToken;

/**
 *
 * @author North
 */
public class TalrandSkySummoner extends CardImpl<TalrandSkySummoner> {

    private static final FilterSpell filter = new FilterSpell("instant or sorcery card");

    static {
        filter.getCardType().add(CardType.INSTANT);
        filter.getCardType().add(CardType.SORCERY);
        filter.setScopeSubtype(Filter.ComparisonScope.Any);
    }

    public TalrandSkySummoner(UUID ownerId) {
        super(ownerId, 72, "Talrand, Sky Summoner", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{2}{U}{U}");
        this.expansionSetCode = "M13";
        this.supertype.add("Legendary");
        this.subtype.add("Merfolk");
        this.subtype.add("Wizard");

        this.color.setBlue(true);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever you cast an instant or sorcery spell, put a 2/2 blue Drake creature token with flying onto the battlefield.
        this.addAbility(new SpellCastTriggeredAbility(new CreateTokenEffect(new DrakeToken()), filter, false));
    }

    public TalrandSkySummoner(final TalrandSkySummoner card) {
        super(card);
    }

    @Override
    public TalrandSkySummoner copy() {
        return new TalrandSkySummoner(this);
    }
}
