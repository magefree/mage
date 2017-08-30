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

import mage.ObjectColor;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.CanBeYourCommanderAbility;
import mage.abilities.common.PlanswalkerEntersWithLoyalityCountersAbility;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.permanent.token.FreyaliseLlanowarsFuryToken;
import mage.target.TargetPermanent;

import java.util.UUID;
import mage.constants.SuperType;

/**
 *
 * @author LevelX2
 */
public class FreyaliseLlanowarsFury extends CardImpl {

    private static final FilterControlledCreaturePermanent filterGreen = new FilterControlledCreaturePermanent("green creature you control");

    static {
        filterGreen.add(new ColorPredicate(ObjectColor.GREEN));
    }

    public FreyaliseLlanowarsFury(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{3}{G}{G}");
        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add("Freyalise");

        this.addAbility(new PlanswalkerEntersWithLoyalityCountersAbility(3));

        // +2: Create a 1/1 green Elf Druid creature token with "{T}: Add {G} to your mana pool."
        this.addAbility(new LoyaltyAbility(new CreateTokenEffect(new FreyaliseLlanowarsFuryToken()), 2));
        // -2: Destroy target artifact or enchantment.
        LoyaltyAbility loyaltyAbility = new LoyaltyAbility(new DestroyTargetEffect(), -2);
        loyaltyAbility.addTarget(new TargetPermanent(StaticFilters.ARTIFACT_OR_ENCHANTMENT_PERMANENT));
        this.addAbility(loyaltyAbility);
        // -6: Draw a card for each green creature you control.
        this.addAbility(new LoyaltyAbility(new DrawCardSourceControllerEffect(new PermanentsOnBattlefieldCount(filterGreen)), -6));

        // Freyalise, Llanowar's Fury can be your commander.
        this.addAbility(CanBeYourCommanderAbility.getInstance());
    }

    public FreyaliseLlanowarsFury(final FreyaliseLlanowarsFury card) {
        super(card);
    }

    @Override
    public FreyaliseLlanowarsFury copy() {
        return new FreyaliseLlanowarsFury(this);
    }
}
