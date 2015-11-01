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
package mage.sets.magicorigins;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.condition.common.SpellMasteryCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.AddContinuousEffectToGame;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Rarity;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.permanent.token.KnightToken;

/**
 *
 * @author fireshoes
 */
public class GideonsPhalanx extends CardImpl {

    public GideonsPhalanx(UUID ownerId) {
        super(ownerId, 14, "Gideon's Phalanx", Rarity.RARE, new CardType[]{CardType.INSTANT}, "{5}{W}{W}");
        this.expansionSetCode = "ORI";

        // Put four 2/2 white Knight creature tokens with vigilance onto the battlefield.
        this.getSpellAbility().addEffect(new CreateTokenEffect(new KnightToken(), 4));

        // <i>Spell mastery</i> - If there are two or more instant and/or sorcery cards in your graveyard, creatures you control gain indestructible until end of turn.
        Effect effect = new ConditionalOneShotEffect(
                new AddContinuousEffectToGame(new GainAbilityAllEffect(IndestructibleAbility.getInstance(), Duration.EndOfTurn, new FilterControlledCreaturePermanent())),
                SpellMasteryCondition.getInstance(),
                "<br><i>Spell mastery</i> - If there are two or more instant and/or sorcery cards in your graveyard, creatures you control gain indestructible until end of turn");
        this.getSpellAbility().addEffect(effect);
    }

    public GideonsPhalanx(final GideonsPhalanx card) {
        super(card);
    }

    @Override
    public GideonsPhalanx copy() {
        return new GideonsPhalanx(this);
    }
}
