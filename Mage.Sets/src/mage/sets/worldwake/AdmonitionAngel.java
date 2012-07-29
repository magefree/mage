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
package mage.sets.worldwake;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.LandfallAbility;
import mage.abilities.common.LeavesBattlefieldTriggeredAbility;
import mage.abilities.effects.common.ExileTargetForSourceEffect;
import mage.abilities.effects.common.ReturnFromExileForSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.permanent.AnotherPredicate;
import mage.target.TargetPermanent;

/**
 *
 * @author jeffwadsworth
 */
public class AdmonitionAngel extends CardImpl<AdmonitionAngel> {

    private static final FilterPermanent filter = new FilterPermanent("nonland permanent other than Admonition Angel");

    static {
        filter.add(new AnotherPredicate());
        filter.add(Predicates.not(new CardTypePredicate(CardType.LAND)));
    }

    public AdmonitionAngel(UUID ownerId) {
        super(ownerId, 1, "Admonition Angel", Rarity.MYTHIC, new CardType[]{CardType.CREATURE}, "{3}{W}{W}{W}");
        this.expansionSetCode = "WWK";
        this.subtype.add("Angel");

        this.color.setWhite(true);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Landfall - Whenever a land enters the battlefield under your control, you may exile target nonland permanent other than Admonition Angel.
        TriggeredAbility ability = new LandfallAbility(Constants.Zone.BATTLEFIELD, new ExileTargetForSourceEffect("Admonition Angel Exile"), true);
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);

        // When Admonition Angel leaves the battlefield, return all cards exiled with it to the battlefield under their owners' control.
        Ability ability2 = new LeavesBattlefieldTriggeredAbility(new ReturnFromExileForSourceEffect(Constants.Zone.BATTLEFIELD), false);
        this.addAbility(ability2);
    }

    public AdmonitionAngel(final AdmonitionAngel card) {
        super(card);
    }

    @Override
    public AdmonitionAngel copy() {
        return new AdmonitionAngel(this);
    }
}
