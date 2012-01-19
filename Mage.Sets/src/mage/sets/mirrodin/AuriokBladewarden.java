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
package mage.sets.mirrodin;

import java.util.UUID;

import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.dynamicvalue.common.SourcePermanentPowerCount;
import mage.abilities.effects.common.continious.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Loki
 */
public class AuriokBladewarden extends CardImpl<AuriokBladewarden> {

    public AuriokBladewarden(UUID ownerId) {
        super(ownerId, 3, "Auriok Bladewarden", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{1}{W}");
        this.expansionSetCode = "MRD";
        this.subtype.add("Human");
        this.subtype.add("Soldier");

        this.color.setWhite(true);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {tap}: Target creature gets +X/+X until end of turn, where X is Auriok Bladewarden's power.
        Ability ability = new SimpleActivatedAbility(Constants.Zone.BATTLEFIELD, new BoostTargetEffect(new SourcePermanentPowerCount(), new SourcePermanentPowerCount(), Constants.Duration.EndOfTurn), new TapSourceCost());
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    public AuriokBladewarden(final AuriokBladewarden card) {
        super(card);
    }

    @Override
    public AuriokBladewarden copy() {
        return new AuriokBladewarden(this);
    }
}
