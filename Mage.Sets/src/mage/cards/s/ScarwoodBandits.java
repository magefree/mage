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
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.common.SourceOnBattlefieldCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.Cost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.ForestwalkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetArtifactPermanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author L_J
 */
public class ScarwoodBandits extends CardImpl {

    public ScarwoodBandits(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{G}{G}");
        this.subtype.add("Human");
        this.subtype.add("Rogue");
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Forestwalk
        this.addAbility(new ForestwalkAbility());
        
        // {2}{G}, {tap}: Unless an opponent pays {2}, gain control of target artifact for as long as Scarwood Bandits remains on the battlefield.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new ScarwoodBanditsEffect(new GenericManaCost(2)), new ManaCostsImpl("{2}{G}"));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetArtifactPermanent());
        this.addAbility(ability);
    }

    public ScarwoodBandits(final ScarwoodBandits card) {
        super(card);
    }

    @Override
    public ScarwoodBandits copy() {
        return new ScarwoodBandits(this);
    }
}

class ScarwoodBanditsEffect extends OneShotEffect {

    protected Cost cost;

    public ScarwoodBanditsEffect(Cost cost) {
        super(Outcome.Detriment);
        this.staticText = "Unless an opponent pays {2}, gain control of target artifact for as long as Scarwood Bandits remains on the battlefield";
        this.cost = cost;
    }

    public ScarwoodBanditsEffect(final ScarwoodBanditsEffect effect) {
        super(effect);
        this.cost = effect.cost.copy();
    }

    @Override
    public ScarwoodBanditsEffect copy() {
        return new ScarwoodBanditsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Permanent targetArtifact = game.getPermanent(getTargetPointer().getFirst(game, source));
            if (targetArtifact != null) {
                Player player = game.getPlayer(targetArtifact.getControllerId());
                if (player != null) {
                    cost.clearPaid();
                    if (player.chooseUse(Outcome.Benefit, "Pay {2}? (Otherwise Scarwood Bandits' controller gains control of" + targetArtifact.getLogName() + ")", source, game)) {
                        cost.pay(source, game, targetArtifact.getControllerId(), targetArtifact.getControllerId(), false, null);
                    }
                    if (!cost.isPaid()) {
                        ConditionalContinuousEffect effect = new ConditionalContinuousEffect(
                                new GainControlTargetEffect(Duration.Custom),
                                new SourceOnBattlefieldCondition(),
                                "Gain control of target artifact for as long as Scarwood Bandits remains on the battlefield.");
                        effect.setTargetPointer(new FixedTarget(targetArtifact.getId()));
                        game.addEffect(effect, source);
                    }
                }
            }            
            return true;
        }
        return false;
    }
}
