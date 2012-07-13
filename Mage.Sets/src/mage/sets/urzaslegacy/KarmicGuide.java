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
package mage.sets.urzaslegacy;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.keyword.EchoAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author Backfir3
 */
public class KarmicGuide extends CardImpl<KarmicGuide> {

    private static final FilterCard filter = new FilterCard("Black");

    static {
        filter.add(new ColorPredicate(ObjectColor.BLACK));
    }

    public KarmicGuide(UUID ownerId) {
        super(ownerId, 11, "Karmic Guide", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{3}{W}{W}");
        this.expansionSetCode = "ULG";
        this.subtype.add("Angel");
        this.subtype.add("Spirit");
        this.color.setWhite(true);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        this.addAbility(FlyingAbility.getInstance());
        // protection from black
        this.addAbility(new ProtectionAbility(filter));
        // Echo {3}{W}{W}
        this.addAbility(new EchoAbility("{3}{W}{W}"));
        // When Karmic Guide enters the battlefield, return target creature card from your graveyard to the battlefield.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ReturnFromGraveyardToBattlefieldTargetEffect(), false);
        ability.addTarget(new TargetCardInYourGraveyard(new FilterCreatureCard("creature card from your graveyard")));
        this.addAbility(ability);
    }

    public KarmicGuide(final KarmicGuide card) {
        super(card);
    }

    @Override
    public KarmicGuide copy() {
        return new KarmicGuide(this);
    }
}
