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
package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesTriggeredAbility;
import mage.abilities.condition.OrCondition;
import mage.abilities.condition.common.CardsInControllerGraveCondition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalTriggeredAbility;
import mage.abilities.effects.common.discard.DiscardTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.target.TargetPlayer;

/**
 *
 * @author spjspj
 */
public class WretchedCamel extends CardImpl {

    private static final FilterControlledPermanent filterDesertPermanent = new FilterControlledPermanent("Desert");
    private static final FilterCard filterDesertCard = new FilterCard("Desert card");

    static {
        filterDesertPermanent.add(new SubtypePredicate(SubType.DESERT));
        filterDesertCard.add(new SubtypePredicate(SubType.DESERT));
    }

    public WretchedCamel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");

        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.CAMEL);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // When Wretched Camel dies, if you control a Desert or there is a Desert card in your graveyard, target player discards a card.
        Ability ability = new ConditionalTriggeredAbility(
                new DiesTriggeredAbility(new DiscardTargetEffect(1)),
                new OrCondition(
                        new PermanentsOnTheBattlefieldCondition(new FilterControlledPermanent(filterDesertPermanent)),
                        new CardsInControllerGraveCondition(1, filterDesertCard)),
                "When {this} dies, if you control a Desert or there is a Desert card in your graveyard, target player discards a card.");

        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
    }

    public WretchedCamel(final WretchedCamel card) {
        super(card);
    }

    @Override
    public WretchedCamel copy() {
        return new WretchedCamel(this);
    }
}
