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
package mage.sets.planechase2012;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.CostModificationEffectImpl;
import mage.abilities.keyword.LifelinkAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.target.Target;
import mage.util.CardUtil;

/**
 *
 * @author LevelX2
 */
public class ElderwoodScion extends CardImpl<ElderwoodScion> {

    public ElderwoodScion(UUID ownerId) {
        super(ownerId, 88, "Elderwood Scion", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{3}{G}{W}");
        this.expansionSetCode = "PC2";
        this.subtype.add("Elemental");

        this.color.setGreen(true);
        this.color.setWhite(true);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Trample
        this.addAbility(TrampleAbility.getInstance());
        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());
        // Spells you cast that target Elderwood Scion cost {2} less to cast.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new ElderwoodScionCostReductionEffect()));
        // Spells your opponents cast that target Elderwood Scion cost {2} more to cast.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new ElderwoodScionCostReductionEffect2()));
    }

    public ElderwoodScion(final ElderwoodScion card) {
        super(card);
    }

    @Override
    public ElderwoodScion copy() {
        return new ElderwoodScion(this);
    }
}

class ElderwoodScionCostReductionEffect extends CostModificationEffectImpl<ElderwoodScionCostReductionEffect> {

    private static final String effectText = "Spells your opponents cast that target Elderwood Scion cost {2} more to cast";

    ElderwoodScionCostReductionEffect() {
        super(Constants.Duration.WhileOnBattlefield, Constants.Outcome.Benefit);
        staticText = effectText;
    }

    ElderwoodScionCostReductionEffect(ElderwoodScionCostReductionEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        SpellAbility spellAbility = (SpellAbility) abilityToModify;
        CardUtil.adjustCost(spellAbility, 2);
        return true;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        if (abilityToModify instanceof SpellAbility) {
            if (abilityToModify.getControllerId().equals(source.getControllerId())) {
                for (Target target :abilityToModify.getTargets()) {
                    for (UUID targetUUID :target.getTargets()) {
                        if (targetUUID.equals(source.getSourceId())) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    @Override
    public ElderwoodScionCostReductionEffect copy() {
        return new ElderwoodScionCostReductionEffect(this);
    }

}

class ElderwoodScionCostReductionEffect2 extends CostModificationEffectImpl<ElderwoodScionCostReductionEffect2> {

    private static final String effectText = "Spells you cast that target {this} cost {2} less to cast";

    ElderwoodScionCostReductionEffect2() {
        super(Constants.Duration.WhileOnBattlefield, Constants.Outcome.Benefit);
        staticText = effectText;
    }

    ElderwoodScionCostReductionEffect2(ElderwoodScionCostReductionEffect2 effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        SpellAbility spellAbility = (SpellAbility) abilityToModify;
        CardUtil.adjustCost(spellAbility, -2);
        return true;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        if (abilityToModify instanceof SpellAbility) {
            if (game.getOpponents(source.getControllerId()).contains(abilityToModify.getControllerId())) {
                for (Target target :abilityToModify.getTargets()) {
                    for (UUID targetUUID :target.getTargets()) {
                        if (targetUUID.equals(source.getSourceId())) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    @Override
    public ElderwoodScionCostReductionEffect2 copy() {
        return new ElderwoodScionCostReductionEffect2(this);
    }

}
