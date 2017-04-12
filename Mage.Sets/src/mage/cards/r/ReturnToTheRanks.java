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
package mage.cards.r;

import mage.abilities.Ability;
import mage.constants.ComparisonType;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.keyword.ConvokeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.mageobject.ConvertedManaCostPredicate;
import mage.game.Game;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public class ReturnToTheRanks extends CardImpl {

    private static final FilterCreatureCard filter = new FilterCreatureCard("creature cards with converted mana cost 2 or less from your graveyard");

    static {
        filter.add(new ConvertedManaCostPredicate(ComparisonType.FEWER_THAN, 3));
    }

    public ReturnToTheRanks(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{X}{W}{W}");

        // Convoke
        this.addAbility(new ConvokeAbility());
        // Return X target creature cards with converted mana cost 2 or less from your graveyard to the battlefield.
        Effect effect = new ReturnFromGraveyardToBattlefieldTargetEffect();
        effect.setText("Return X target creature cards with converted mana cost 2 or less from your graveyard to the battlefield");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(0,Integer.MAX_VALUE, filter));

    }

    @Override
    public void adjustTargets(Ability ability, Game game) {
        for (Effect effect : ability.getEffects()) {
            if (effect instanceof ReturnFromGraveyardToBattlefieldTargetEffect) {
                int xValue = ability.getManaCostsToPay().getX();
                ability.getTargets().clear();
                ability.addTarget(new TargetCardInYourGraveyard(xValue,xValue, filter));
            }
        }
    }

    public ReturnToTheRanks(final ReturnToTheRanks card) {
        super(card);
    }

    @Override
    public ReturnToTheRanks copy() {
        return new ReturnToTheRanks(this);
    }
}
