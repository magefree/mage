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
package mage.sets.gatecrash;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.Constants.TargetController;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.effects.common.TapTargetEffect;
import mage.cards.CardImpl;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.filter.predicate.permanent.AnotherPredicate;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public class CourtStreetDenizen extends CardImpl<CourtStreetDenizen> {

    private static final FilterPermanent filter = new FilterCreaturePermanent("another white creature");
    private static final FilterCreaturePermanent filterOpponentCreature = new FilterCreaturePermanent("creature an opponent controls");
    static {
        filter.add(new AnotherPredicate());
        filter.add(new ColorPredicate(ObjectColor.WHITE));
        filter.add(new ControllerPredicate(TargetController.YOU));
        filterOpponentCreature.add(new ControllerPredicate(Constants.TargetController.OPPONENT));
    }
    public CourtStreetDenizen(UUID ownerId) {
        super(ownerId, 8, "Court Street Denizen", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{2}{W}");
        this.expansionSetCode = "GTC";
        this.subtype.add("Human");
        this.subtype.add("Soldier");

        this.color.setWhite(true);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever another white creature enters the battlefield under your control, tap target creature an opponent controls.
        Ability ability = new EntersBattlefieldAllTriggeredAbility(Constants.Zone.BATTLEFIELD, new TapTargetEffect(),filter,false);
        ability.addTarget(new TargetCreaturePermanent(filterOpponentCreature));
        this.addAbility(ability);
    }

    public CourtStreetDenizen(final CourtStreetDenizen card) {
        super(card);
    }

    @Override
    public CourtStreetDenizen copy() {
        return new CourtStreetDenizen(this);
    }
}
