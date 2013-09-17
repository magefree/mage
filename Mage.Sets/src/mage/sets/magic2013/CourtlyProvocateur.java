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
package mage.sets.magic2013;

import mage.constants.CardType;
import mage.constants.Rarity;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.combat.AttacksIfAbleTargetEffect;
import mage.abilities.effects.common.combat.BlocksIfAbleTargetEffect;
import mage.abilities.effects.common.continious.GainAbilityTargetEffect;
import mage.abilities.keyword.AttacksThisTurnMarkerAbility;
import mage.abilities.keyword.BlocksThisTurnMarkerAbility;
import mage.cards.CardImpl;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author magenoxx_at_gmail.com
 */
public class CourtlyProvocateur extends CardImpl<CourtlyProvocateur> {

    public CourtlyProvocateur(UUID ownerId) {
        super(ownerId, 46, "Courtly Provocateur", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{2}{U}");
        this.expansionSetCode = "M13";
        this.subtype.add("Human");
        this.subtype.add("Wizard");

        this.color.setBlue(true);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {tap}: Target creature attacks this turn if able.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new AttacksIfAbleTargetEffect(Duration.EndOfTurn), new TapSourceCost());
        ability.addEffect(new GainAbilityTargetEffect(AttacksThisTurnMarkerAbility.getInstance(), Duration.EndOfTurn, null));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);

        // {tap}: Target creature blocks this turn if able.
        Ability ability2 = new SimpleActivatedAbility(Zone.BATTLEFIELD, new BlocksIfAbleTargetEffect(Duration.EndOfTurn), new TapSourceCost());
        ability2.addEffect(new GainAbilityTargetEffect(BlocksThisTurnMarkerAbility.getInstance(), Duration.EndOfTurn, null));
        ability2.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability2);
    }

    public CourtlyProvocateur(final CourtlyProvocateur card) {
        super(card);
    }

    @Override
    public CourtlyProvocateur copy() {
        return new CourtlyProvocateur(this);
    }
}
