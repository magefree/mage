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
package mage.sets.championsofkamigawa;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.delayed.AtEndOfTurnDelayedTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.SacrificeTargetEffect;
import mage.abilities.effects.common.continious.GainAbilityTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.SpliceOntoArcaneAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInHand;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author LevelX2
 */
public class ThroughTheBreach extends CardImpl<ThroughTheBreach> {

    public ThroughTheBreach(UUID ownerId) {
        super(ownerId, 193, "Through the Breach", Rarity.RARE, new CardType[]{CardType.INSTANT}, "{4}{R}");
        this.expansionSetCode = "CHK";
        this.subtype.add("Arcane");

        this.color.setRed(true);

        // You may put a creature card from your hand onto the battlefield. That creature gains haste. Sacrifice that creature at the beginning of the next end step.
        this.getSpellAbility().addEffect(new ThroughTheBreachEffect());
        // Splice onto Arcane {2}{R}{R}
        this.addAbility(new SpliceOntoArcaneAbility(new ManaCostsImpl("{2}{R}{R}")));
    }

    public ThroughTheBreach(final ThroughTheBreach card) {
        super(card);
    }

    @Override
    public ThroughTheBreach copy() {
        return new ThroughTheBreach(this);
    }
}

class ThroughTheBreachEffect extends OneShotEffect<ThroughTheBreachEffect> {
    
    private static final String choiceText = "Put a creature card from your hand onto the battlefield?";

    public ThroughTheBreachEffect() {
        super(Outcome.Benefit);
        this.staticText = "You may put a creature card from your hand onto the battlefield. That creature gains haste. Sacrifice that creature at the beginning of the next end step";
    }

    public ThroughTheBreachEffect(final ThroughTheBreachEffect effect) {
        super(effect);
    }

    @Override
    public ThroughTheBreachEffect copy() {
        return new ThroughTheBreachEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            if (player.chooseUse(Outcome.PutCreatureInPlay, choiceText, game)) {
                TargetCardInHand target = new TargetCardInHand(new FilterCreatureCard());
                if (player.choose(Outcome.PutCreatureInPlay, target, source.getSourceId(), game)) {
                    Card card = game.getCard(target.getFirstTarget());
                    if (card != null) {
                        if (card.putOntoBattlefield(game, Zone.HAND, source.getId(), source.getControllerId())) {
                            Permanent permanent = game.getPermanent(card.getId());
                            ContinuousEffect effect = new GainAbilityTargetEffect(HasteAbility.getInstance(), Duration.WhileOnBattlefield);
                            effect.setTargetPointer(new FixedTarget(permanent.getId()));
                            game.addEffect(effect, source);
                            SacrificeTargetEffect sacrificeEffect = new SacrificeTargetEffect("sacrifice boosted " + card.getName());
                            sacrificeEffect.setTargetPointer(new FixedTarget(card.getId()));
                            DelayedTriggeredAbility delayedAbility = new AtEndOfTurnDelayedTriggeredAbility(sacrificeEffect);
                            delayedAbility.setSourceId(source.getSourceId());
                            delayedAbility.setControllerId(source.getControllerId());
                            game.addDelayedTriggeredAbility(delayedAbility);
                        }
                        return true;
                    }
                }
            }
            return true;
        }
        return false;
    }
}
