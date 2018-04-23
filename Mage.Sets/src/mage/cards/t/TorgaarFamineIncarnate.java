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
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.SacrificeXTargetCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.cost.CostModificationEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.CostModificationType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.util.CardUtil;

/**
 *
 * @author LevelX2
 */
public class TorgaarFamineIncarnate extends CardImpl {

    public TorgaarFamineIncarnate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{6}{B}{B}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.AVATAR);
        this.power = new MageInt(7);
        this.toughness = new MageInt(6);

        // As an additional cost to cast this spell, you may sacrifice any number of creatures.
        Cost cost = new SacrificeXTargetCost(StaticFilters.FILTER_CONTROLLED_CREATURE_SHORT_TEXT);
        cost.setText("As an additional cost to cast this spell, you may sacrifice any number of creatures.");
        this.getSpellAbility().addCost(cost);
        // This spell costs {2} less to cast for each creature sacrificed this way.
        this.addAbility(new SimpleStaticAbility(Zone.STACK, new TorgaarFamineIncarnateEffectCostReductionEffect()));

        // When Torgaar, Famine Incarnate enters the battlefield, up to one target player's life total becomes half their starting life total, rounded down.
        Ability ability = new EntersBattlefieldTriggeredAbility(new TorgaarFamineIncarnateEffect(), false);
        ability.addTarget(new TargetPlayer(0, 1, false));
        this.addAbility(ability);

    }

    public TorgaarFamineIncarnate(final TorgaarFamineIncarnate card) {
        super(card);
    }

    @Override
    public TorgaarFamineIncarnate copy() {
        return new TorgaarFamineIncarnate(this);
    }
}

//class TorgaarFamineIncarnateSacrificeCost extends CostImpl {
//
//    int numbSacrificed = 0;
//
//    public TorgaarFamineIncarnateSacrificeCost() {
//        this.text = "sacrifice any number of creatures";
//
//    }
//
//    public TorgaarFamineIncarnateSacrificeCost(final TorgaarFamineIncarnateSacrificeCost cost) {
//        super(cost);
//        this.numbSacrificed = cost.numbSacrificed;
//    }
//
//    @Override
//    public boolean canPay(Ability ability, UUID sourceId, UUID controllerId, Game game) {
//        return true;
//    }
//
//    @Override
//    public boolean pay(Ability ability, Game game, UUID sourceId, UUID controllerId, boolean noMana, Cost costToPay) {
//        TargetControlledCreaturePermanent target
//                = new TargetControlledCreaturePermanent(0, Integer.MAX_VALUE,
//                        new FilterControlledCreaturePermanent("select any number of creatures to sacrifice. "
//                                + "This spell costs {2} less to cast for each creature sacrificed this way"), true);
//        Player player = game.getPlayer(controllerId);
//        if (player != null) {
//            player.chooseTarget(Outcome.Benefit, target, ability, game);
//            for (UUID creatureId : target.getTargets()) {
//                Permanent creature = game.getPermanent(creatureId);
//                if (creature != null) {
//                    if (creature.sacrifice(sourceId, game)) {
//                        numbSacrificed++;
//                    }
//                }
//            }
//        }
//        this.paid = true;
//        return paid;
//    }
//
//    public int getNumbSacrificed() {
//        return numbSacrificed;
//    }
//
//    @Override
//    public TorgaarFamineIncarnateSacrificeCost copy() {
//        return new TorgaarFamineIncarnateSacrificeCost(this);
//    }
//}
class TorgaarFamineIncarnateEffect extends OneShotEffect {

    public TorgaarFamineIncarnateEffect() {
        super(Outcome.Benefit);
        this.staticText = "up to one target player's life total becomes half their starting life total, rounded down";
    }

    public TorgaarFamineIncarnateEffect(final TorgaarFamineIncarnateEffect effect) {
        super(effect);
    }

    @Override
    public TorgaarFamineIncarnateEffect copy() {
        return new TorgaarFamineIncarnateEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player targetPlayer = game.getPlayer(getTargetPointer().getFirst(game, source));
        if (targetPlayer != null) {
            int startingLifeTotal = game.getLife();
            targetPlayer.setLife(startingLifeTotal / 2, game, source);
        }
        return true;
    }
}

class TorgaarFamineIncarnateEffectCostReductionEffect extends CostModificationEffectImpl {

    public TorgaarFamineIncarnateEffectCostReductionEffect() {
        super(Duration.WhileOnStack, Outcome.Benefit, CostModificationType.REDUCE_COST);
        staticText = "This spell costs {2} less to cast for each creature sacrificed this way";
    }

    public TorgaarFamineIncarnateEffectCostReductionEffect(final TorgaarFamineIncarnateEffectCostReductionEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        SpellAbility spellAbility = (SpellAbility) abilityToModify;
        for (Cost cost : spellAbility.getCosts()) {
            if (cost instanceof SacrificeXTargetCost) {
                int reduction = ((SacrificeXTargetCost) cost).getAmount();
                CardUtil.adjustCost(spellAbility, reduction * 2);
                break;
            }
        }
        return true;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        return abilityToModify instanceof SpellAbility && abilityToModify.getSourceId().equals(source.getSourceId());
    }

    @Override
    public TorgaarFamineIncarnateEffectCostReductionEffect copy() {
        return new TorgaarFamineIncarnateEffectCostReductionEffect(this);
    }
}
