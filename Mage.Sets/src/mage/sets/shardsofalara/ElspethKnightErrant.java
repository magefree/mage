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
package mage.sets.shardsofalara;

import java.util.UUID;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.PlanswalkerEntersWithLoyalityCountersAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.Effects;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.GetEmblemEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.command.Emblem;
import mage.game.permanent.token.SoldierToken;
import mage.game.permanent.token.Token;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class ElspethKnightErrant extends CardImpl {

    public ElspethKnightErrant(UUID ownerId) {
        super(ownerId, 9, "Elspeth, Knight-Errant", Rarity.MYTHIC, new CardType[]{CardType.PLANESWALKER}, "{2}{W}{W}");
        this.expansionSetCode = "ALA";
        this.subtype.add("Elspeth");

        this.addAbility(new PlanswalkerEntersWithLoyalityCountersAbility(4));

        // +1: Put a 1/1 white Soldier creature token onto the battlefield.
        Token token = new SoldierToken();
        token.setOriginalExpansionSetCode("ALA"); // to get the right image
        this.addAbility(new LoyaltyAbility(new CreateTokenEffect(token), 1));

        // +1: Target creature gets +3/+3 and gains flying until end of turn.
        Effects effects1 = new Effects();
        Effect effect = new BoostTargetEffect(3, 3, Duration.EndOfTurn);
        effect.setText("Target creature gets +3/+3");
        effects1.add(effect);
        effect = new GainAbilityTargetEffect(FlyingAbility.getInstance(), Duration.EndOfTurn);
        effect.setText("and gains flying until end of turn");
        effects1.add(effect);
        LoyaltyAbility ability1 = new LoyaltyAbility(effects1, 1);
        ability1.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability1);

        // -8: You get an emblem with "Artifacts, creatures, enchantments, and lands you control are indestructible."
        this.addAbility(new LoyaltyAbility(new GetEmblemEffect(new ElspethKnightErrantEmblem()), -8));
    }

    public ElspethKnightErrant(final ElspethKnightErrant card) {
        super(card);
    }

    @Override
    public ElspethKnightErrant copy() {
        return new ElspethKnightErrant(this);
    }

}

class ElspethKnightErrantEmblem extends Emblem {

    public ElspethKnightErrantEmblem() {
        this.setName("EMBLEM: Elspeth, Knight-Errant");
        FilterControlledPermanent filter = new FilterControlledPermanent("Artifacts, creatures, enchantments, and lands you control");
        filter.add(Predicates.or(
                new CardTypePredicate(CardType.ARTIFACT),
                new CardTypePredicate(CardType.CREATURE),
                new CardTypePredicate(CardType.ENCHANTMENT),
                new CardTypePredicate(CardType.LAND)));
        Effect effect = new GainAbilityAllEffect(IndestructibleAbility.getInstance(), Duration.WhileOnBattlefield, filter, false);
        effect.setText("Artifacts, creatures, enchantments, and lands you control are indestructible");
        this.getAbilities().add(new SimpleStaticAbility(Zone.COMMAND, effect));
        this.setExpansionSetCodeForImage("MMA");
    }
}
