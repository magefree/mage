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
package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.cost.CostModificationEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityType;
import mage.constants.CardType;
import mage.constants.CostModificationType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.Target;
import mage.util.CardUtil;

/**
 *
 * @author TheElk801
 */
public class KopalaWardenOfWaves extends CardImpl {

    public KopalaWardenOfWaves(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}{U}");

        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Spells your opponents cast that target a Merfolk you control cost {2} more to cast.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new KopalaWardenOfWavesCostReductionEffect()));

        // Abilities your opponents activate that target a Merfolk you control cost {2} more to activate.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new KopalaWardenOfWavesCostReductionEffect2()));
    }

    public KopalaWardenOfWaves(final KopalaWardenOfWaves card) {
        super(card);
    }

    @Override
    public KopalaWardenOfWaves copy() {
        return new KopalaWardenOfWaves(this);
    }
}

class KopalaWardenOfWavesCostReductionEffect extends CostModificationEffectImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("a Merfolk you control");
    private static final String effectText = "Spells your opponents cast that target a Merfolk you control cost {2} more to cast";

    static {
        filter.add(new SubtypePredicate(SubType.MERFOLK));
    }

    KopalaWardenOfWavesCostReductionEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit, CostModificationType.INCREASE_COST);
        staticText = effectText;
    }

    KopalaWardenOfWavesCostReductionEffect(KopalaWardenOfWavesCostReductionEffect effect) {
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
        if (abilityToModify.getAbilityType() == AbilityType.SPELL) {
            if (game.getOpponents(source.getControllerId()).contains(abilityToModify.getControllerId())) {
                for (Target target : abilityToModify.getTargets()) {
                    for (UUID targetUUID : target.getTargets()) {
                        Permanent creature = game.getPermanent(targetUUID);
                        if (creature != null
                                && filter.match(creature, game)
                                && creature.getControllerId().equals(source.getControllerId())) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    @Override
    public KopalaWardenOfWavesCostReductionEffect copy() {
        return new KopalaWardenOfWavesCostReductionEffect(this);
    }

}

class KopalaWardenOfWavesCostReductionEffect2 extends CostModificationEffectImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("a Merfolk you control");
    private static final String effectText = "Abilities your opponents activate that target a Merfolk you control cost {2} more to activate";

    static {
        filter.add(new SubtypePredicate(SubType.MERFOLK));
    }

    KopalaWardenOfWavesCostReductionEffect2() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit, CostModificationType.INCREASE_COST);
        staticText = effectText;
    }

    KopalaWardenOfWavesCostReductionEffect2(KopalaWardenOfWavesCostReductionEffect2 effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        CardUtil.increaseCost(abilityToModify, 2);
        return true;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        if (abilityToModify.getAbilityType() == AbilityType.ACTIVATED) {
            if (game.getOpponents(source.getControllerId()).contains(abilityToModify.getControllerId())) {
                for (Target target : abilityToModify.getTargets()) {
                    for (UUID targetUUID : target.getTargets()) {
                        Permanent creature = game.getPermanent(targetUUID);
                        if (creature != null
                                && filter.match(creature, game)
                                && creature.getControllerId().equals(source.getControllerId())) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    @Override
    public KopalaWardenOfWavesCostReductionEffect2 copy() {
        return new KopalaWardenOfWavesCostReductionEffect2(this);
    }

}
