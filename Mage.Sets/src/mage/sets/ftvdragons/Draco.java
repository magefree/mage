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
package mage.sets.ftvdragons;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.dynamicvalue.common.DomainValue;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.cost.CostModificationEffectImpl;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.util.CardUtil;

/**
 *
 * @author Simown
 */
public class Draco extends CardImpl {

    public Draco(UUID ownerId) {
        super(ownerId, 3, "Draco", Rarity.RARE, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{16}");
        this.expansionSetCode = "FVD";
        this.subtype.add("Dragon");
        this.power = new MageInt(9);
        this.toughness = new MageInt(9);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Domain - Draco costs {2} less to cast for each basic land type among lands you control.
        this.addAbility(new SimpleStaticAbility(Zone.STACK, new DracoCostReductionEffect()));

        // Domain - At the beginning of your upkeep, sacrifice Draco unless you pay {10}. This cost is reduced by {2} for each basic land type among lands you control.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new DracoSacrificeUnlessPaysEffect(), TargetController.YOU, false));
    }

    public Draco(final Draco card) {
        super(card);
    }

    @Override
    public Draco copy() {
        return new Draco(this);
    }
}

class DracoCostReductionEffect extends CostModificationEffectImpl {

    public DracoCostReductionEffect() {
        super(Duration.WhileOnStack, Outcome.Benefit, CostModificationType.REDUCE_COST);
        staticText = "<i>Domain</i> - {this} costs {2} less to cast for each basic land type among lands you control.";
    }

    protected DracoCostReductionEffect(final DracoCostReductionEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        CardUtil.reduceCost(abilityToModify, new DomainValue(2).calculate(game, source, this));
        return true;
    }

    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        return abilityToModify.getSourceId().equals(source.getSourceId());
    }

    @Override
    public DracoCostReductionEffect copy() {
        return new DracoCostReductionEffect(this);
    }
}

class DracoSacrificeUnlessPaysEffect extends OneShotEffect {

    public static final int MAX_DOMAIN_VALUE = 10;

    public DracoSacrificeUnlessPaysEffect () {
        super(Outcome.Sacrifice);
        staticText = "sacrifice {this} unless you pay {10}. This cost is reduced by {2} for each basic land type among lands you control.";
    }

    public DracoSacrificeUnlessPaysEffect (final DracoSacrificeUnlessPaysEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (player != null && permanent != null) {
            // The cost is reduced by {2} for each basic land type.
            int domainValueReduction = new DomainValue(2).calculate(game, source, this);
            // Nothing to pay
            if (domainValueReduction >= MAX_DOMAIN_VALUE) {
                return true;
            }
            int count = (MAX_DOMAIN_VALUE-domainValueReduction );
            if (player.chooseUse(Outcome.Benefit, "Pay {" + count + "}? Or " + permanent.getName() + " will be sacrificed.", source, game)) {
                GenericManaCost cost = new GenericManaCost(count);
                if (cost.pay(source, game, source.getSourceId(), source.getControllerId(), false)) {
                    return true;
                }
            }
            permanent.sacrifice(source.getSourceId(), game);
            return true;
        }
        return false;
    }

    @Override
    public DracoSacrificeUnlessPaysEffect copy() {
        return new DracoSacrificeUnlessPaysEffect (this);
    }

}


