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
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.condition.common.SurgedCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.ReturnToHandFromBattlefieldAllEffect;
import mage.abilities.keyword.SurgeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterNonlandPermanent;
import mage.game.permanent.token.Token;

/**
 *
 * @author LevelX2
 */
public class CrushOfTentacles extends CardImpl {

    public CrushOfTentacles(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{U}{U}");

        // Return all nonland permanents to their owners' hands. If Crush of Tentacles surge cost was paid, create an 8/8 blue Octopus creature token.
        getSpellAbility().addEffect(new ReturnToHandFromBattlefieldAllEffect(new FilterNonlandPermanent("nonland permanents")));
        Effect effect = new ConditionalOneShotEffect(new CreateTokenEffect(new CrushOfTentaclesToken()), SurgedCondition.instance);
        effect.setText("If {this} surge cost was paid, create an 8/8 blue Octopus creature token");
        getSpellAbility().addEffect(effect);

        // Surge {3}{U}{U} (You may cast this spell for its surge cost if you or a teammate has cast another spell this turn)
        addAbility(new SurgeAbility(this, "{3}{U}{U}"));
    }

    public CrushOfTentacles(final CrushOfTentacles card) {
        super(card);
    }

    @Override
    public CrushOfTentacles copy() {
        return new CrushOfTentacles(this);
    }
}

class CrushOfTentaclesToken extends Token {

    public CrushOfTentaclesToken() {
        super("Octopus", "8/8 blue Octopus creature");
        this.setExpansionSetCodeForImage("BFZ");
        this.cardType.add(CardType.CREATURE);
        this.color.setBlue(true);
        this.subtype.add("Octopus");
        this.power = new MageInt(8);
        this.toughness = new MageInt(8);
    }
}
