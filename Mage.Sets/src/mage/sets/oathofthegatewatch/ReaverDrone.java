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
package mage.sets.oathofthegatewatch;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.condition.InvertCondition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition.CountType;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.abilities.keyword.DevoidAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.constants.TargetController;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.ColorlessPredicate;
import mage.filter.predicate.permanent.AnotherPredicate;

/**
 *
 * @author fireshoes
 */
public class ReaverDrone extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("another colorless creature");

    static {
        filter.add(new AnotherPredicate());
        filter.add(new ColorlessPredicate());
    }

    public ReaverDrone(UUID ownerId) {
        super(ownerId, 76, "Reaver Drone", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{B}");
        this.expansionSetCode = "OGW";
        this.subtype.add("Eldrazi");
        this.subtype.add("Drone");
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Devoid
        this.addAbility(new DevoidAbility(this.color));

        // At the beginning of your upkeep, you lose 1 life unless you control another colorless creature.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new ConditionalOneShotEffect(
                new LoseLifeSourceControllerEffect(1),
                new InvertCondition(new PermanentsOnTheBattlefieldCondition(filter, CountType.MORE_THAN, 0, true)),
                "you lose 1 life unless you control another colorless creature"), TargetController.YOU, false));
    }

    public ReaverDrone(final ReaverDrone card) {
        super(card);
    }

    @Override
    public ReaverDrone copy() {
        return new ReaverDrone(this);
    }
}
