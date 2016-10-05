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
package mage.cards.f;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SacrificeSourceTriggeredAbility;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.abilities.keyword.EmergeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.token.EldraziHorrorToken;
import mage.game.stack.Spell;

/**
 *
 * @author LevelX2
 */
public class FoulEmissary extends CardImpl {

    private static final FilterCard filter = new FilterCard("a creature card");

    static {
        filter.add(new CardTypePredicate(CardType.CREATURE));
    }

    public FoulEmissary(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{G}");
        this.subtype.add("Human");
        this.subtype.add("Horror");
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // When Foul Emissary enters the battlefield, look at the top four cards of your library. You may reveal a creature card from among them and put it into your hand. Put the rest on the bottom of your library in any order.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new LookLibraryAndPickControllerEffect(new StaticValue(4), false, new StaticValue(1), filter, false)));

        // When you sacrifice Foul Emissary while casting a spell with emerge, put a 3/2 colorless Eldrazi Horror creature token onto the battlefield.
        this.addAbility(new FoulEmissaryTriggeredAbility(new CreateTokenEffect(new EldraziHorrorToken()), false));
    }

    public FoulEmissary(final FoulEmissary card) {
        super(card);
    }

    @Override
    public FoulEmissary copy() {
        return new FoulEmissary(this);
    }
}

class FoulEmissaryTriggeredAbility extends SacrificeSourceTriggeredAbility {

    public FoulEmissaryTriggeredAbility(Effect effect, boolean optional) {
        super(effect, optional);
    }

    public FoulEmissaryTriggeredAbility(final FoulEmissaryTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (super.checkTrigger(event, game)) {
            Spell spell = game.getStack().getSpell(event.getSourceId());
            return spell != null && spell.getSpellAbility() instanceof EmergeAbility;
        }
        return false;
    }

    @Override
    public FoulEmissaryTriggeredAbility copy() {
        return new FoulEmissaryTriggeredAbility(this);
    }

    @Override
    public String getRule() {
        return "When you sacrifice {this} while casting a spell with emerge, put a 3/2 colorless Eldrazi Horror creature token onto the battlefield.";
    }
}
