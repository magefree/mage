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
package mage.abilities.keyword;

import java.util.UUID;
import mage.Mana;
import mage.abilities.SpellAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.ManaCost;
import mage.abilities.costs.mana.ManaCosts;
import mage.abilities.mana.ManaOptions;
import mage.cards.Card;
import mage.constants.Outcome;
import mage.constants.SpellAbilityType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.CardIdPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.util.CardUtil;

/**
 *
 * @author emerald000
 */
public class EmergeAbility extends SpellAbility {

    private final ManaCosts<ManaCost> emergeCost;

    public EmergeAbility(Card card, ManaCosts<ManaCost> emergeCost) {
        super(emergeCost, card.getName() + " with emerge", Zone.HAND, SpellAbilityType.BASE_ALTERNATE);
        this.getCosts().addAll(card.getSpellAbility().getCosts().copy());
        this.getEffects().addAll(card.getSpellAbility().getEffects().copy());
        this.getTargets().addAll(card.getSpellAbility().getTargets().copy());
        this.timing = card.getSpellAbility().getTiming();
        this.setRuleAtTheTop(true);
        this.emergeCost = emergeCost.copy();
    }

    public EmergeAbility(final EmergeAbility ability) {
        super(ability);
        this.emergeCost = ability.emergeCost.copy();
    }

    @Override
    public boolean canActivate(UUID playerId, Game game) {
        if (super.canActivate(playerId, game)) {
            Player controller = game.getPlayer(this.getControllerId());
            if (controller != null) {
                for (Permanent creature : game.getBattlefield().getActivePermanents(new FilterControlledCreaturePermanent(), this.getControllerId(), this.getSourceId(), game)) {
                    ManaCost costToPay = CardUtil.reduceCost(emergeCost.copy(), creature.getConvertedManaCost());
                    if (costToPay.canPay(this, this.getSourceId(), this.getControllerId(), game)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public ManaOptions getMinimumCostToActivate(UUID playerId, Game game) {
        int maxCMC = 0;
        for (Permanent creature : game.getBattlefield().getActivePermanents(new FilterControlledCreaturePermanent(), playerId, this.getSourceId(), game)) {
            int cmc = creature.getConvertedManaCost();
            if (cmc > maxCMC) {
                maxCMC = cmc;
            }
        }
        ManaOptions manaOptions = super.getMinimumCostToActivate(playerId, game);
        for (Mana mana : manaOptions) {
            if (mana.getGeneric() > maxCMC) {
                mana.setGeneric(mana.getGeneric() - maxCMC);
            } else {
                mana.setGeneric(0);
            }
        }
        return manaOptions;
    }

    @Override
    public boolean activate(Game game, boolean noMana) {
        Player controller = game.getPlayer(this.getControllerId());
        if (controller != null) {
            TargetPermanent target = new TargetControlledCreaturePermanent(new FilterControlledCreaturePermanent("creature to sacrifice for emerge"));
            if (controller.choose(Outcome.Sacrifice, target, this.getSourceId(), game)) {
                Permanent creature = game.getPermanent(target.getFirstTarget());
                CardUtil.reduceCost(this, creature.getConvertedManaCost());
                FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent(creature.getLogName());
                filter.add(new CardIdPredicate(creature.getId()));
                this.addCost(new SacrificeTargetCost(new TargetControlledCreaturePermanent(filter)));
                return super.activate(game, false);
            }
        }
        return false;
    }

    @Override
    public EmergeAbility copy() {
        return new EmergeAbility(this);
    }

    @Override
    public String getRule(boolean all) {
        return getRule();
    }

    @Override
    public String getRule() {
        return "Emerge " + emergeCost.getText() + " <i>(You may cast this spell by sacrificing a creature and paying the emerge cost reduced by that creature's converted mana cost.)</i>";
    }
}
