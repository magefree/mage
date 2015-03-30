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
package mage.sets.limitedalpha;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterLandPermanent;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetPermanent;
import mage.target.common.TargetLandPermanent;
import mage.target.common.TargetOpponent;

/**
 *
 * @author jeffwadsworth
 */
public class DemonicHordes extends CardImpl {

    public DemonicHordes(UUID ownerId) {
        super(ownerId, 12, "Demonic Hordes", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{3}{B}{B}{B}");
        this.expansionSetCode = "LEA";
        this.subtype.add("Demon");
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // {tap}: Destroy target land.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DestroyTargetEffect(), new TapSourceCost());
        ability.addTarget(new TargetPermanent(new FilterLandPermanent()));
        this.addAbility(ability);

        // At the beginning of your upkeep, unless you pay {B}{B}{B}, tap Demonic Hordes and sacrifice a land of an opponent's choice.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new DemonicHordesEffect(new ManaCostsImpl("{B}{B}{B}")), TargetController.YOU, false));
    }

    public DemonicHordes(final DemonicHordes card) {
        super(card);
    }

    @Override
    public DemonicHordes copy() {
        return new DemonicHordes(this);
    }
}

class DemonicHordesEffect extends OneShotEffect {

    protected Cost cost;

    public DemonicHordesEffect(Cost cost) {
        super(Outcome.Sacrifice);
        this.cost = cost;
        staticText = "unless you pay {B}{B}{B}, tap {this} and sacrifice a land of an opponent's choice";
    }

    public DemonicHordesEffect(final DemonicHordesEffect effect) {
        super(effect);
        this.cost = effect.cost.copy();
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent demonicHordes = game.getPermanentOrLKIBattlefield(source.getSourceId());
        if (controller != null && demonicHordes != null) {
            StringBuilder sb = new StringBuilder(cost.getText()).append("?");
            if (!sb.toString().toLowerCase().startsWith("exile ") && !sb.toString().toLowerCase().startsWith("return ")) {
                sb.insert(0, "Pay ");
            }
            if (controller.chooseUse(Outcome.Benefit, sb.toString(), game)) {
                cost.clearPaid();
                if (cost.pay(source, game, source.getSourceId(), source.getControllerId(), false)) {
                    return true;
                }
            }
            demonicHordes.tap(game);
            Target choiceOpponent = new TargetOpponent();
            choiceOpponent.setNotTarget(true);
            FilterLandPermanent filterLand = new FilterLandPermanent();
            filterLand.add(new ControllerIdPredicate(source.getControllerId()));
            if (controller.choose(Outcome.Neutral, choiceOpponent, source.getSourceId(), game)) {
                Player opponent = game.getPlayer(choiceOpponent.getFirstTarget());
                if (opponent != null) {
                    Target chosenLand = new TargetLandPermanent(filterLand);
                    chosenLand.setNotTarget(true);
                    if (opponent.chooseTarget(Outcome.Sacrifice, chosenLand, source, game)) {
                        Permanent land = game.getPermanent(chosenLand.getFirstTarget());
                        if (land != null) {
                            land.sacrifice(source.getSourceId(), game);
                        }
                    }
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public DemonicHordesEffect copy() {
        return new DemonicHordesEffect(this);
    }
}
