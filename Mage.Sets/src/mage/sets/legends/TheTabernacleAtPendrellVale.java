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
package mage.sets.legends;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.BeginningOfUntapTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continious.GainAbilityAllEffect;
import mage.cards.CardImpl;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author Plopman
 */
public class TheTabernacleAtPendrellVale extends CardImpl<TheTabernacleAtPendrellVale> {

    public TheTabernacleAtPendrellVale(UUID ownerId) {
        super(ownerId, 252, "The Tabernacle at Pendrell Vale", Rarity.RARE, new CardType[]{CardType.LAND}, "");
        this.expansionSetCode = "LEG";
        this.supertype.add("Legendary");

        // All creatures have "At the beginning of your upkeep, destroy this creature unless you pay {1}."
        Ability ability = new BeginningOfUntapTriggeredAbility(new DestroySourceUnlessPaysEffect(new ManaCostsImpl("{1}")), Constants.TargetController.YOU, false);
        this.addAbility(new SimpleStaticAbility(Constants.Zone.BATTLEFIELD, new GainAbilityAllEffect(ability, Constants.Duration.WhileOnBattlefield, new FilterCreaturePermanent())));
    }

    public TheTabernacleAtPendrellVale(final TheTabernacleAtPendrellVale card) {
        super(card);
    }

    @Override
    public TheTabernacleAtPendrellVale copy() {
        return new TheTabernacleAtPendrellVale(this);
    }
}


class DestroySourceUnlessPaysEffect extends OneShotEffect<DestroySourceUnlessPaysEffect> {
    protected Cost cost;

    public DestroySourceUnlessPaysEffect(Cost cost) {
        super(Outcome.DestroyPermanent);
        this.cost = cost;
     }

    public DestroySourceUnlessPaysEffect(final DestroySourceUnlessPaysEffect effect) {
        super(effect);
        this.cost = effect.cost;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (player != null && permanent != null) { 
            if (player.chooseUse(Outcome.Benefit, "Pay " + cost.getText()  + "?", game)) {
                cost.clearPaid();
                if (cost.pay(source, game, source.getId(), source.getControllerId(), false))
                    return true;
            }
            permanent.destroy(source.getSourceId(), game, false);
            return true;
        }
        return false;
    }

    @Override
    public DestroySourceUnlessPaysEffect copy() {
        return new DestroySourceUnlessPaysEffect(this);
    }

        @Override
    public String getText(Mode mode) {
        return "destroy this creature unless you pay {1}";
    }
 }