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
package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.constants.ComparisonType;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.RaidCondition;
import mage.abilities.decorator.ConditionalTriggeredAbility;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.mageobject.ConvertedManaCostPredicate;
import mage.target.common.TargetCardInYourGraveyard;
import mage.watchers.common.PlayerAttackedWatcher;

import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public class TimelyHordemate extends CardImpl {

    private static final FilterCreatureCard filter = new FilterCreatureCard("creature card with converted mana cost 2 or less from your graveyard");

    static {
        filter.add(new ConvertedManaCostPredicate(ComparisonType.FEWER_THAN, 3));
    }

    public TimelyHordemate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{W}");
        this.subtype.add("Human");
        this.subtype.add("Warrior");

        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // <i>Raid</i> - When Timely Hordemate enters the battlefield, if you attacked this turn, return target creature card with converted mana cost 2 or less from your graveyard to the battlefield.
        Ability ability = new ConditionalTriggeredAbility(new EntersBattlefieldTriggeredAbility(new ReturnFromGraveyardToBattlefieldTargetEffect()), RaidCondition.instance,
                "<i>Raid</i> - When {this} enters the battlefield, if you attacked with a creature this turn, return target creature card with converted mana cost 2 or less from your graveyard to the battlefield.");
        ability.addTarget(new TargetCardInYourGraveyard(filter));
        this.addAbility(ability, new PlayerAttackedWatcher());

    }

    public TimelyHordemate(final TimelyHordemate card) {
        super(card);
    }

    @Override
    public TimelyHordemate copy() {
        return new TimelyHordemate(this);
    }
}
