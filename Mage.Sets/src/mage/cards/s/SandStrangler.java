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
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.OrCondition;
import mage.abilities.condition.common.CardsInControllerGraveCondition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalTriggeredAbility;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author spjspj
 */
public class SandStrangler extends CardImpl {

    private static final FilterControlledPermanent filterDesertPermanent = new FilterControlledPermanent("Desert");
    private static final FilterCard filterDesertCard = new FilterCard("Desert card");

    static {
        filterDesertPermanent.add(new SubtypePredicate(SubType.DESERT));
        filterDesertCard.add(new SubtypePredicate(SubType.DESERT));
    }

    public SandStrangler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");

        this.subtype.add("Beast");
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // When Sand Strangler enters the battlefield, if you control a Desert or there is a Desert card in your graveyard, you may have Sand Strangler deal 3 damage to target creature.
        Ability ability = new ConditionalTriggeredAbility(
                new EntersBattlefieldTriggeredAbility(new DamageTargetEffect(3)),
                new OrCondition(
                        new PermanentsOnTheBattlefieldCondition(new FilterControlledPermanent(filterDesertPermanent)),
                        new CardsInControllerGraveCondition(1, filterDesertCard)),
                "When {this} enters the battlefield, if you control a Desert or there is a Desert card in your graveyard, you may have {this} deal 3 damage to target creature.");
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);

    }

    public SandStrangler(final SandStrangler card) {
        super(card);
    }

    @Override
    public SandStrangler copy() {
        return new SandStrangler(this);
    }
}
