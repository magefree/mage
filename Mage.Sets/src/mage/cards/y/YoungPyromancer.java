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
package mage.cards.y;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterSpell;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.permanent.token.Token;
import mage.util.RandomUtil;

/**
 *
 * @author jeffwadsworth
 */
public class YoungPyromancer extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("instant or sorcery spell");

    static {
        filter.add(Predicates.or(
                new CardTypePredicate(CardType.INSTANT),
                new CardTypePredicate(CardType.SORCERY)));
    }

    public YoungPyromancer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{R}");
        this.subtype.add("Human");
        this.subtype.add("Shaman");

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Whenever you cast an instant or sorcery spell, create a 1/1 red Elemental creature token.
        this.addAbility(new SpellCastControllerTriggeredAbility(new CreateTokenEffect(new ElementalToken()), filter, false));

    }

    public YoungPyromancer(final YoungPyromancer card) {
        super(card);
    }

    @Override
    public YoungPyromancer copy() {
        return new YoungPyromancer(this);
    }

    class ElementalToken extends Token {

        public ElementalToken() {
            super("Elemental", "1/1 red Elemental creature");
            if (getOriginalExpansionSetCode() != null && getOriginalExpansionSetCode().equals("M14")) {
                setTokenType(RandomUtil.nextInt(2) + 1);
            }
            if (getOriginalExpansionSetCode() != null && getOriginalExpansionSetCode().equals("EMA")) {
                setTokenType(1);
            }
            cardType.add(CardType.CREATURE);
            color.setRed(true);
            subtype.add("Elemental");
            power = new MageInt(1);
            toughness = new MageInt(1);
        }
    }
}