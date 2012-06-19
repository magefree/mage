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
package mage.sets.guildpact;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastTriggeredAbility;
import mage.abilities.effects.common.DamageAllEffect;
import mage.abilities.effects.common.continious.GainAbilityTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.filter.FilterSpell;
import mage.filter.common.FilterCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Loki
 */
public class TiborAndLumia extends CardImpl<TiborAndLumia> {

    private final static FilterSpell filterBlue = new FilterSpell("a blue spell");
    private final static FilterCreaturePermanent filter = new FilterCreaturePermanent("creature without flying");
    private final static FilterSpell filterRed = new FilterSpell("a red spell");

    static {
        filterBlue.setUseColor(true);
        filterBlue.getColor().setBlue(true);
        filter.getAbilities().add(FlyingAbility.getInstance());
        filter.setNotAbilities(true);
        filterRed.setUseColor(true);
        filterRed.getColor().setRed(true);
    }

    public TiborAndLumia(UUID ownerId) {
        super(ownerId, 135, "Tibor and Lumia", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{2}{U}{R}");
        this.expansionSetCode = "GPT";
        this.supertype.add("Legendary");
        this.subtype.add("Human");
        this.subtype.add("Wizard");
        this.color.setRed(true);
        this.color.setBlue(true);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);
        Ability firstAbility = new SpellCastTriggeredAbility(new GainAbilityTargetEffect(FlyingAbility.getInstance(), Constants.Duration.EndOfTurn), filterBlue, false);
        firstAbility.addTarget(new TargetCreaturePermanent());
        this.addAbility(firstAbility);
        this.addAbility(new SpellCastTriggeredAbility(new DamageAllEffect(1, filter), filterRed, false));

    }

    public TiborAndLumia(final TiborAndLumia card) {
        super(card);
    }

    @Override
    public TiborAndLumia copy() {
        return new TiborAndLumia(this);
    }
}
