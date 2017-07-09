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
package mage.cards.u;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.permanent.AttackingPredicate;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public class UlrichsKindred extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("attacking Wolf or Werewolf");

    static {
        filter.add(Predicates.or(new SubtypePredicate(SubType.WOLF), new SubtypePredicate(SubType.WEREWOLF)));
        filter.add(new AttackingPredicate());
    }

    public UlrichsKindred(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{R}");
        this.subtype.add("Wolf");
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Trample
        this.addAbility(TrampleAbility.getInstance());
        // {3}{G}: Target attacking Wolf or Werewolf gains indestructible until end of turn.
        SimpleActivatedAbility ability = new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new GainAbilityTargetEffect(IndestructibleAbility.getInstance(), Duration.EndOfTurn),
                new ManaCostsImpl<>("{3}{G}"));
        ability.addTarget(new TargetCreaturePermanent(filter));
        this.addAbility(ability);

    }

    public UlrichsKindred(final UlrichsKindred card) {
        super(card);
    }

    @Override
    public UlrichsKindred copy() {
        return new UlrichsKindred(this);
    }
}
