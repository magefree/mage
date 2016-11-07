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
package mage.cards.e;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.DeliriumCondition;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.decorator.ConditionalTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.keyword.TransformAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.permanent.AnotherPredicate;
import mage.game.permanent.token.EldraziHorrorToken;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author LevelX2
 */
public class ExtricatorOfSin extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("another permanent");

    static {
        filter.add(new AnotherPredicate());
    }

    public ExtricatorOfSin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{W}");
        this.subtype.add("Human");
        this.subtype.add("Cleric");
        this.power = new MageInt(0);
        this.toughness = new MageInt(3);

        this.transformable = true;
        this.secondSideCardClazz = ExtricatorOfFlesh.class;

        // When Extricator of Sin enters the battlefield, you may sacrifice another permanent. If you do, create a 3/2 colorless Eldrazi Horror creature token.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new DoIfCostPaid(new CreateTokenEffect(new EldraziHorrorToken()),
                new SacrificeTargetCost(new TargetControlledPermanent(filter))), false));

        // <i>Delirium</i> &mdash; At the beginning of your upkeep, if there are four or more card types among cards in your graveyard, transform Extricator of Sin.
        this.addAbility(new TransformAbility());
        this.addAbility(new ConditionalTriggeredAbility(
                new BeginningOfUpkeepTriggeredAbility(Zone.BATTLEFIELD, new TransformSourceEffect(true), TargetController.YOU, false),
                DeliriumCondition.getInstance(),
                "<i>Delirium</i> &mdash; At the beginning of your upkeep, if there are four or more card types among cards in your graveyard, "
                + " transform {this}."));
    }

    public ExtricatorOfSin(final ExtricatorOfSin card) {
        super(card);
    }

    @Override
    public ExtricatorOfSin copy() {
        return new ExtricatorOfSin(this);
    }
}
