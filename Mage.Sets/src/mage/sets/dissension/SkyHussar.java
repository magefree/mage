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
package mage.sets.dissension;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.UntapAllControllerEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.ForecastAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author emerald000
 */
public class SkyHussar extends CardImpl {
    
    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("untapped white and/or blue creatures you control");
    static {
        filter.add(Predicates.or(new ColorPredicate(ObjectColor.WHITE), new ColorPredicate(ObjectColor.BLUE)));
        filter.add(Predicates.not(new TappedPredicate()));
    }

    public SkyHussar(UUID ownerId) {
        super(ownerId, 131, "Sky Hussar", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{3}{W}{U}");
        this.expansionSetCode = "DIS";
        this.subtype.add("Human");
        this.subtype.add("Knight");

        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        
        // When Sky Hussar enters the battlefield, untap all creatures you control.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new UntapAllControllerEffect(new FilterControlledCreaturePermanent(), "untap all creatures you control"), false));

        // Forecast - Tap two untapped white and/or blue creatures you control, Reveal Sky Hussar from your hand: Draw a card.
        this.addAbility(new ForecastAbility(new DrawCardSourceControllerEffect(1), new TapTargetCost(new TargetControlledCreaturePermanent(2, 2, filter, true))));
    }

    public SkyHussar(final SkyHussar card) {
        super(card);
    }

    @Override
    public SkyHussar copy() {
        return new SkyHussar(this);
    }
}
