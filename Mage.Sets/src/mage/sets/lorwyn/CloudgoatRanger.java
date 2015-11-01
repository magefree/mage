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
package mage.sets.lorwyn;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.game.permanent.token.KithkinToken;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author Loki
 */
public class CloudgoatRanger extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("untapped Kithkin you control");

    static {
        filter.add(Predicates.not(new TappedPredicate()));
        filter.add(new SubtypePredicate("Kithkin"));
    }

    public CloudgoatRanger(UUID ownerId) {
        super(ownerId, 10, "Cloudgoat Ranger", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{3}{W}{W}");
        this.expansionSetCode = "LRW";
        this.subtype.add("Giant");
        this.subtype.add("Warrior");

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // When Cloudgoat Ranger enters the battlefield, put three 1/1 white Kithkin Soldier creature tokens onto the battlefield.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new KithkinToken(), 3), false));
        //Tap three untapped Kithkin you control: Cloudgoat Ranger gets +2/+0 and gains flying until end of turn.
        Effect effect = new BoostSourceEffect(2, 0, Duration.EndOfTurn);
        effect.setText("{this} gets +2/+0");
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, effect, new TapTargetCost(new TargetControlledPermanent(3, 3, filter, false)));
        effect = new GainAbilitySourceEffect(FlyingAbility.getInstance(), Duration.EndOfTurn);
        effect.setText("and gains flying until end of turn");
        ability.addEffect(effect);
        this.addAbility(ability);
    }

    public CloudgoatRanger(final CloudgoatRanger card) {
        super(card);
    }

    @Override
    public CloudgoatRanger copy() {
        return new CloudgoatRanger(this);
    }
}
