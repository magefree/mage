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
package mage.cards.l;

import java.util.UUID;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.PlanswalkerEntersWithLoyalityCountersAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DestroyAllEffect;
import mage.abilities.effects.common.PutTopCardOfLibraryIntoGraveControllerEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.effects.common.continuous.BecomesBlackZombieAdditionEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterCreatureCard;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.permanent.token.ZombieToken;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author JRHerlehy
 */
public class LilianaDeathsMajesty extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("non-Zombie creatures");

    static {
        filter.add(Predicates.not(new SubtypePredicate(SubType.ZOMBIE)));
    }

    public LilianaDeathsMajesty(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{3}{B}{B}");

        this.subtype.add("Liliana");

        //Starting Loyalty: 5
        this.addAbility(new PlanswalkerEntersWithLoyalityCountersAbility(5));

        // +1: Create a 2/2 black Zombie creature token. Put the top two cards of your library into your graveyard.
        LoyaltyAbility ability = new LoyaltyAbility(new CreateTokenEffect(new ZombieToken()), 1);
        ability.addEffect(new PutTopCardOfLibraryIntoGraveControllerEffect(2));
        this.addAbility(ability);

        // -3: Return target creature card from your graveyard to the battlefield. That creature is a black Zombie in addition to its other colors and types.
        ability = new LoyaltyAbility(new BecomesBlackZombieAdditionEffect() // because the effect has to be active for triggered effects that e.g. check if the creature entering is a Zombie, the continuous effect needs to be added before the card moving effect is applied
                .setText(""), -3);
        ability.addTarget(new TargetCardInYourGraveyard(new FilterCreatureCard("creature card from your graveyard")));
        ability.addEffect(new ReturnFromGraveyardToBattlefieldTargetEffect()
                .setText("Return target creature card from your graveyard to the battlefield. That creature is a black Zombie in addition to its other colors and types"));
        this.addAbility(ability);

        // -7: Destroy all non-Zombie creatures.
        ability = new LoyaltyAbility(new DestroyAllEffect(filter), -7);
        this.addAbility(ability);
    }

    public LilianaDeathsMajesty(final LilianaDeathsMajesty card) {
        super(card);
    }

    @Override
    public LilianaDeathsMajesty copy() {
        return new LilianaDeathsMajesty(this);
    }
}
