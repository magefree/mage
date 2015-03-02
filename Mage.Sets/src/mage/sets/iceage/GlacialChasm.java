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
package mage.sets.iceage;

import java.util.UUID;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.effects.common.PreventAllDamageToControllerEffect;
import mage.abilities.effects.common.SacrificeControllerEffect;
import mage.abilities.effects.common.combat.CantAttackAllAnyPlayerEffect;
import mage.abilities.keyword.CumulativeUpkeepAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Rarity;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterControlledLandPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerPredicate;

/**
 *
 * @author Plopman
 */
public class GlacialChasm extends CardImpl {

    public GlacialChasm(UUID ownerId) {
        super(ownerId, 331, "Glacial Chasm", Rarity.UNCOMMON, new CardType[]{CardType.LAND}, "");
        this.expansionSetCode = "ICE";

        // Cumulative upkeep-Pay 2 life.
        this.addAbility(new CumulativeUpkeepAbility(new PayLifeCost(2)));
        // When Glacial Chasm enters the battlefield, sacrifice a land.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new SacrificeControllerEffect(new FilterControlledLandPermanent(), 1, "")));
        // Creatures you control can't attack.
        FilterCreaturePermanent filter = new FilterCreaturePermanent("creatures you control");
        filter.add(new ControllerPredicate(TargetController.YOU));
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new CantAttackAllAnyPlayerEffect(Duration.WhileOnBattlefield, filter)));
        // Prevent all damage that would be dealt to you.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new PreventAllDamageToControllerEffect(Duration.WhileOnBattlefield)));
    }

    public GlacialChasm(final GlacialChasm card) {
        super(card);
    }

    @Override
    public GlacialChasm copy() {
        return new GlacialChasm(this);
    }
}
