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
package mage.cards.n;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.PlanswalkerEntersWithLoyalityCountersAbility;
import mage.abilities.effects.common.GetEmblemEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.abilities.effects.common.continuous.BecomesCreatureTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.TargetController;
import mage.filter.common.FilterLandPermanent;
import mage.filter.common.FilterPermanentCard;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.game.permanent.token.Token;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.common.TargetLandPermanent;
import mage.game.command.emblems.NissaVitalForceEmblem;

/**
 *
 * @author fireshoes
 */
public class NissaVitalForce extends CardImpl {

    private static final FilterLandPermanent filter = new FilterLandPermanent("land you control");

    static {
        filter.add(new ControllerPredicate(TargetController.YOU));
    }

    public NissaVitalForce(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{3}{G}{G}");
        this.subtype.add("Nissa");

        this.addAbility(new PlanswalkerEntersWithLoyalityCountersAbility(5));

        // +1: Untap target land you control. Until your next turn, it becomes a 5/5 Elemental creature with haste. It's still a land.
        LoyaltyAbility ability = new LoyaltyAbility(new UntapTargetEffect(), 1);
        ability.addEffect(new BecomesCreatureTargetEffect(new NissaVitalForceToken(), false, true, Duration.UntilYourNextTurn));
        ability.addTarget(new TargetLandPermanent(filter));
        this.addAbility(ability);

        // -3: Return target permanent card from your graveyard to your hand.
        ability = new LoyaltyAbility(new ReturnToHandTargetEffect(), -3);
        ability.addTarget(new TargetCardInYourGraveyard(new FilterPermanentCard("permanent card from your graveyard")));
        this.addAbility(ability);

        // -6: You get an emblem with "Whenever a land enters the battlefield under your control, you may draw a card."
        this.addAbility(new LoyaltyAbility(new GetEmblemEffect(new NissaVitalForceEmblem()), -6));
    }

    public NissaVitalForce(final NissaVitalForce card) {
        super(card);
    }

    @Override
    public NissaVitalForce copy() {
        return new NissaVitalForce(this);
    }
}

class NissaVitalForceToken extends Token {

    public NissaVitalForceToken() {
        super("", "5/5 Elemental creature with haste");
        this.cardType.add(CardType.CREATURE);

        this.subtype.add("Elemental");
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);
        this.addAbility(HasteAbility.getInstance());
    }
}
