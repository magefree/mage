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
package mage.sets.commander;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.effects.common.continious.BoostAllEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.common.FilterPermanentCard;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.target.Target;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author LevelX2
 */
public class BladewingTheRisen extends CardImpl<BladewingTheRisen> {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Dragon creatures");
    private static final FilterPermanentCard filterCard = new FilterPermanentCard("Dragon permanent card from your graveyard");
    static {
        filter.add(new SubtypePredicate("Dragon"));
        filterCard.add(new SubtypePredicate("Dragon"));
    }

    public BladewingTheRisen(UUID ownerId) {
        super(ownerId, 185, "Bladewing the Risen", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{3}{B}{B}{R}{R}");
        this.expansionSetCode = "CMD";
        this.supertype.add("Legendary");
        this.subtype.add("Zombie");
        this.subtype.add("Dragon");

        this.color.setRed(true);
        this.color.setBlack(true);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // When Bladewing the Risen enters the battlefield, you may return target Dragon permanent card from your graveyard to the battlefield.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ReturnFromGraveyardToBattlefieldTargetEffect(), true);
        Target target = new TargetCardInYourGraveyard(filterCard);
        target.setRequired(true);
        ability.addTarget(target);
         this.addAbility(ability);
        // {B}{R}: Dragon creatures get +1/+1 until end of turn.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new BoostAllEffect(1,1, Duration.EndOfTurn, filter, false), new ManaCostsImpl("{B}{R}")));
    }

    public BladewingTheRisen(final BladewingTheRisen card) {
        super(card);
    }

    @Override
    public BladewingTheRisen copy() {
        return new BladewingTheRisen(this);
    }
}
