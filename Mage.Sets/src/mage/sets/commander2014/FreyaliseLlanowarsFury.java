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
package mage.sets.commander2014;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.CanBeYourCommanderAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.mana.GreenManaAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.counters.CounterType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterArtifactOrEnchantmentPermanent;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.permanent.token.Token;
import mage.target.TargetPermanent;

/**
 *
 * @author LevelX2
 */
public class FreyaliseLlanowarsFury extends CardImpl {

    private static final FilterControlledCreaturePermanent filterGreen = new FilterControlledCreaturePermanent("green creature you control");
    static {
        filterGreen.add(new ColorPredicate(ObjectColor.GREEN));
    }

    public FreyaliseLlanowarsFury(UUID ownerId) {
        super(ownerId, 43, "Freyalise, Llanowar's Fury", Rarity.MYTHIC, new CardType[]{CardType.PLANESWALKER}, "{3}{G}{G}");
        this.expansionSetCode = "C14";
        this.subtype.add("Freyalise");

        this.addAbility(new EntersBattlefieldAbility(new AddCountersSourceEffect(CounterType.LOYALTY.createInstance(3)), false));
        
        // +2: Put a 1/1 green Elf Druid creature token onto the battlefield with "{T}: Add {G} to your mana pool."
        this.addAbility(new LoyaltyAbility(new CreateTokenEffect(new FreyaliseLlanowarsFuryToken()), 2));
        // -2: Destroy target artifact or enchantment.
        LoyaltyAbility loyaltyAbility = new LoyaltyAbility(new DestroyTargetEffect(), -2);
        loyaltyAbility.addTarget(new TargetPermanent(new FilterArtifactOrEnchantmentPermanent()));
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

class FreyaliseLlanowarsFuryToken extends Token {

    FreyaliseLlanowarsFuryToken() {
        super("Elf Druid", "1/1 green Elf Druid creature token with \"{t}: Add {G} to your mana pool.\"");
        this.setOriginalExpansionSetCode("C14");
        this.cardType.add(CardType.CREATURE);
        this.color = ObjectColor.GREEN;
        this.subtype.add("Elf");
        this.subtype.add("Druid");

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {T}: Add {G} to your mana pool.
        this.addAbility(new GreenManaAbility());
    }
}