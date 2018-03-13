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
package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.PlanswalkerEntersWithLoyalityCountersAbility;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.DynamicManaEffect;
import mage.abilities.effects.common.GetEmblemEffect;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.abilities.effects.common.continuous.BecomesCreatureTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.filter.common.FilterLandPermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.game.command.emblems.KothOfTheHammerEmblem;
import mage.game.permanent.token.Token;
import mage.target.common.TargetLandPermanent;

/**
 *
 * @author Loki, North
 */
public class KothOfTheHammer extends CardImpl {

    static final FilterLandPermanent filter = new FilterLandPermanent(SubType.MOUNTAIN, "Mountain");
    static final FilterLandPermanent filterCount = new FilterLandPermanent("Mountain you control");

    static {
        filterCount.add(new SubtypePredicate(SubType.MOUNTAIN));
        filterCount.add(new ControllerPredicate(TargetController.YOU));
    }

    public KothOfTheHammer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{2}{R}{R}");
        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.KOTH);

        this.addAbility(new PlanswalkerEntersWithLoyalityCountersAbility(3));

        // +1: Untap target Mountain. It becomes a 4/4 red Elemental creature until end of turn. It's still a land.
        Ability ability = new LoyaltyAbility(new UntapTargetEffect(), 1);
        ability.addEffect(new BecomesCreatureTargetEffect(new KothOfTheHammerToken(), false, true, Duration.EndOfTurn));
        ability.addTarget(new TargetLandPermanent(filter));
        this.addAbility(ability);

        // -2: Add {R} to your mana pool for each Mountain you control.
        this.addAbility(new LoyaltyAbility(new DynamicManaEffect(Mana.RedMana(1), new PermanentsOnBattlefieldCount(filterCount)), -2));

        // -5: You get an emblem with "Mountains you control have '{T}: This land deals 1 damage to target creature or player.'
        this.addAbility(new LoyaltyAbility(new GetEmblemEffect(new KothOfTheHammerEmblem()), -5));
    }

    public KothOfTheHammer(final KothOfTheHammer card) {
        super(card);
    }

    @Override
    public KothOfTheHammer copy() {
        return new KothOfTheHammer(this);
    }
}

class KothOfTheHammerToken extends Token {

    public KothOfTheHammerToken() {
        super("Elemental", "4/4 red Elemental");
        this.cardType.add(CardType.CREATURE);
        this.subtype.add(SubType.ELEMENTAL);

        this.color.setRed(true);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);
    }
}
