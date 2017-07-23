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
package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.dynamicvalue.common.DevotionCount;
import mage.abilities.dynamicvalue.common.SourcePermanentToughnessValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.PutTopCardOfLibraryIntoGraveTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.effects.common.continuous.LoseCreatureTypeSourceEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.target.TargetPlayer;

/**
 *
 * @author LevelX2
 */
public class PhenaxGodOfDeception extends CardImpl {

    public PhenaxGodOfDeception(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT,CardType.CREATURE},"{3}{U}{B}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add("God");

        this.power = new MageInt(4);
        this.toughness = new MageInt(7);

        // Indestructible
        this.addAbility(IndestructibleAbility.getInstance());
        // As long as your devotion to blue and black is less than seven, Phenax isn't a creature.
        Effect effect = new LoseCreatureTypeSourceEffect(new DevotionCount(ColoredManaSymbol.U, ColoredManaSymbol.B), 7);
        effect.setText("As long as your devotion to blue and black is less than seven, Phenax isn't a creature");
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, effect));           
        // Creatures you control have "{T}: Target player puts the top X cards of his or her library into his or her graveyard, where X is this creature's toughness."        
        effect = new PutTopCardOfLibraryIntoGraveTargetEffect(SourcePermanentToughnessValue.getInstance());
        effect.setText("Target player puts the top X cards of his or her library into his or her graveyard, where X is this creature's toughness");
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, effect, new TapSourceCost());
        ability.addTarget(new TargetPlayer());
        effect = new GainAbilityControlledEffect(ability, Duration.WhileOnBattlefield, StaticFilters.FILTER_PERMANENT_CREATURES,false);
        effect.setText("Creatures you control have \"{T}: Target player puts the top X cards of his or her library into his or her graveyard, where X is this creature's toughness.\"");
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, effect));
    }

    public PhenaxGodOfDeception(final PhenaxGodOfDeception card) {
        super(card);
    }

    @Override
    public PhenaxGodOfDeception copy() {
        return new PhenaxGodOfDeception(this);
    }
}
