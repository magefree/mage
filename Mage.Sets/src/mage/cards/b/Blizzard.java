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
package mage.cards.b;

import java.util.UUID;
import mage.abilities.common.CastOnlyIfConditionIsTrueAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DontUntapInControllersUntapStepAllEffect;
import mage.abilities.keyword.CumulativeUpkeepAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Duration;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterControlledLandPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.filter.predicate.mageobject.SupertypePredicate;

/**
 *
 * @author TheElk801
 */
public final class Blizzard extends CardImpl {

    private static final FilterControlledLandPermanent filter = new FilterControlledLandPermanent("a snow land");
    private static final FilterCreaturePermanent filter2 = new FilterCreaturePermanent("creatures with flying");

    static {
        filter.add(new SupertypePredicate(SuperType.SNOW));
        filter2.add(new AbilityPredicate(FlyingAbility.class));
    }

    public Blizzard(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{G}{G}");

        // Cast Blizzard only if you control a snow land.
        this.addAbility(new CastOnlyIfConditionIsTrueAbility(
                new PermanentsOnTheBattlefieldCondition(filter, ComparisonType.MORE_THAN, 0)
        ));

        // Cumulative upkeep {2}
        this.addAbility(new CumulativeUpkeepAbility(new ManaCostsImpl("{2}")));

        // Creatures with flying don't untap during their controllers' untap steps.
        this.addAbility(new SimpleStaticAbility(
                Zone.BATTLEFIELD,
                new DontUntapInControllersUntapStepAllEffect(
                        Duration.WhileOnBattlefield,
                        TargetController.ANY,
                        filter2
                )
        ));
    }

    public Blizzard(final Blizzard card) {
        super(card);
    }

    @Override
    public Blizzard copy() {
        return new Blizzard(this);
    }
}
