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

package mage.sets.dragonsmaze;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.CastFromHandCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.PopulateEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.game.permanent.token.Token;
import mage.watchers.common.CastFromHandWatcher;

/**
 *
 * @author LevelX2
 */


public class ScionOfVituGhazi extends CardImpl<ScionOfVituGhazi> {

    public ScionOfVituGhazi (UUID ownerId) {
        super(ownerId, 7, "Scion of Vitu-Ghazi", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{3}{W}{W}");
        this.expansionSetCode = "DGM";
        this.subtype.add("Elemental");
        this.color.setWhite(true);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        this.addWatcher(new CastFromHandWatcher());

        // When Scion of Vitu-Ghazi enters the battlefield, if you cast it from your hand, put a 1/1 white Bird creature token with flying onto the battlefield, then populate.
        Ability ability = new EntersBattlefieldTriggeredAbility(
                new ConditionalOneShotEffect(new CreateTokenEffect(new BirdToken()), new CastFromHandCondition(),
                "if you cast it from your hand, put a 1/1 white Bird creature token with flying onto the battlefield,"));
        ability.addEffect(new PopulateEffect("then"));
        this.addAbility(ability);
    }

    public ScionOfVituGhazi (final ScionOfVituGhazi card) {
        super(card);
    }

    @Override
    public ScionOfVituGhazi copy() {
        return new ScionOfVituGhazi(this);
    }

}


class BirdToken extends Token {
    public BirdToken() {
        super("Bird", "1/1 white Bird creature token with flying");
        cardType.add(CardType.CREATURE);
        color.setWhite(true);
        subtype.add("Bird");
        power = new MageInt(1);
        toughness = new MageInt(1);
        addAbility(FlyingAbility.getInstance());
    }
}