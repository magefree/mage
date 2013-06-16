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
package mage.sets.modernmasters;

import java.util.UUID;

import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.CostModificationEffectImpl;
import mage.abilities.effects.common.cost.SpellsCostReductionEffect;
import mage.cards.CardImpl;
import mage.constants.Duration;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.util.CardUtil;

/**
 *
 * @author LevelX2
 */
public class GrandArbiterAugustinIV extends CardImpl<GrandArbiterAugustinIV> {

    private static final FilterCard filterWhite = new FilterCard("White spells");
    private static final FilterCard filterBlue = new FilterCard("Blue spells");
    static {
        filterWhite.add(new ColorPredicate(ObjectColor.WHITE));
        filterBlue.add(new ColorPredicate(ObjectColor.BLUE));
    }

    public GrandArbiterAugustinIV(UUID ownerId) {
        super(ownerId, 176, "Grand Arbiter Augustin IV", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{2}{W}{U}");
        this.expansionSetCode = "MMA";
        this.supertype.add("Legendary");
        this.subtype.add("Human");
        this.subtype.add("Advisor");

        this.color.setBlue(true);
        this.color.setWhite(true);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // White spells you cast cost {1} less to cast.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new SpellsCostReductionEffect(filterWhite, 1)));
        // Blue spells you cast cost {1} less to cast.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new SpellsCostReductionEffect(filterBlue, 1)));
        // Spells your opponents cast cost {1} more to cast.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GrandArbiterAugustinIVCostIncreaseEffect()));
    }

    public GrandArbiterAugustinIV(final GrandArbiterAugustinIV card) {
        super(card);
    }

    @Override
    public GrandArbiterAugustinIV copy() {
        return new GrandArbiterAugustinIV(this);
    }
}

class GrandArbiterAugustinIVCostIncreaseEffect extends CostModificationEffectImpl<GrandArbiterAugustinIVCostIncreaseEffect> {

    private static final String effectText = "Spells your opponents cast cost {1} more to cast";

    GrandArbiterAugustinIVCostIncreaseEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = effectText;
    }

    GrandArbiterAugustinIVCostIncreaseEffect(GrandArbiterAugustinIVCostIncreaseEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        SpellAbility spellAbility = (SpellAbility) abilityToModify;
        CardUtil.adjustCost(spellAbility, -1);
        return true;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        if (abilityToModify instanceof SpellAbility) {
            if (game.getOpponents(source.getControllerId()).contains(abilityToModify.getControllerId())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public GrandArbiterAugustinIVCostIncreaseEffect copy() {
        return new GrandArbiterAugustinIVCostIncreaseEffect(this);
    }

}
