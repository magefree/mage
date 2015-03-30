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
package mage.sets.dragonsoftarkir;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.DamageMultiEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.constants.TargetController;
import mage.filter.common.FilterCreatureOrPlaneswalkerPermanent;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.target.common.TargetCreatureOrPlaneswalkerAmount;

/**
 *
 * @author fireshoes
 */
public class DragonlordAtarka extends CardImpl {

    private static final FilterCreatureOrPlaneswalkerPermanent filter = new FilterCreatureOrPlaneswalkerPermanent("creatures and/or planeswalkers your opponents control");

    static {
        filter.add(new ControllerPredicate(TargetController.OPPONENT));
    }

    public DragonlordAtarka(UUID ownerId) {
        super(ownerId, 216, "Dragonlord Atarka", Rarity.MYTHIC, new CardType[]{CardType.CREATURE}, "{5}{R}{G}");
        this.expansionSetCode = "DTK";
        this.supertype.add("Legendary");
        this.subtype.add("Elder");
        this.subtype.add("Dragon");
        this.power = new MageInt(8);
        this.toughness = new MageInt(8);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // When Dragonlord Atarka enters the battlefield, it deals 5 damage divided as you choose among any number of target creatures and/or planeswalkers your opponents control.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DamageMultiEffect(5), false);
        ability.addTarget(new TargetCreatureOrPlaneswalkerAmount(5, filter));
        this.addAbility(ability);
    }

    public DragonlordAtarka(final DragonlordAtarka card) {
        super(card);
    }

    @Override
    public DragonlordAtarka copy() {
        return new DragonlordAtarka(this);
    }
}
