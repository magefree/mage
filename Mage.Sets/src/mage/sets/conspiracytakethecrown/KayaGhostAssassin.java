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
package mage.sets.conspiracytakethecrown;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.PlanswalkerEntersWithLoyalityCountersAbility;
import mage.abilities.common.delayed.AtTheBeginOfYourNextUpkeepDelayedTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeOpponentsEffect;
import mage.abilities.effects.common.ReturnToBattlefieldUnderOwnerControlTargetEffect;
import mage.abilities.effects.common.discard.DiscardEachPlayerEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author fireshoes
 */
public class KayaGhostAssassin extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("target creature to exile. Choose no targets to exile Kaya.");

    public KayaGhostAssassin(UUID ownerId) {
        super(ownerId, 75, "Kaya, Ghost Assassin", Rarity.MYTHIC, new CardType[]{CardType.PLANESWALKER}, "{2}{W}{B}");
        this.expansionSetCode = "CN2";
        this.subtype.add("Kaya");

        this.addAbility(new PlanswalkerEntersWithLoyalityCountersAbility(5));

        // 0: Exile Kaya, Ghost Assassin or up to one target creature. Return that card to the battlefield under its owner's control at the beginning of your next upkeep.
        // You lose 2 life.
        Ability ability = new LoyaltyAbility(new KayaGhostAssassinEffect(), 0);
        ability.addTarget(new TargetCreaturePermanent(0, 1, filter, false));
        this.addAbility(ability);

        // -1: Each opponent loses 2 life and you gain 2 life.
        ability = new LoyaltyAbility(new LoseLifeOpponentsEffect(2), -1);
        Effect effect = new GainLifeEffect(2);
        effect.setText("and you gain 2 life");
        ability.addEffect(effect);
        this.addAbility(ability);

        // -2: Each opponent discards a card and you draw a card.
        ability = new LoyaltyAbility(new DiscardEachPlayerEffect(TargetController.OPPONENT), -2);
        effect = new DrawCardSourceControllerEffect(1);
        effect.setText("and you draw a card");
        ability.addEffect(effect);
        this.addAbility(ability);
    }

    public KayaGhostAssassin(final KayaGhostAssassin card) {
        super(card);
    }

    @Override
    public KayaGhostAssassin copy() {
        return new KayaGhostAssassin(this);
    }
}

class KayaGhostAssassinEffect extends OneShotEffect {

    public KayaGhostAssassinEffect() {
        super(Outcome.Benefit);
        this.staticText = "Exile {this} or up to one target creature. Return that card to the battlefield under its owner's control at the beginning of your next upkeep. "
                + "You lose 2 life";
    }

    public KayaGhostAssassinEffect(final KayaGhostAssassinEffect effect) {
        super(effect);
    }

    @Override
    public KayaGhostAssassinEffect copy() {
        return new KayaGhostAssassinEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent sourcePermanent = game.getPermanent(source.getSourceId());
        if (controller != null && sourcePermanent != null) {
            if (getTargetPointer().getFirst(game, source) != null) {
                Permanent targetCreature = game.getPermanent(getTargetPointer().getFirst(game, source));
                if (targetCreature != null) {
                    int zcc = targetCreature.getZoneChangeCounter(game);
                    if (controller.moveCards(targetCreature, Zone.EXILED, source, game)) {
                        Effect effect = new ReturnToBattlefieldUnderOwnerControlTargetEffect();
                        effect.setTargetPointer(new FixedTarget(targetCreature.getId(), zcc + 1));
                        AtTheBeginOfYourNextUpkeepDelayedTriggeredAbility delayedAbility
                                = new AtTheBeginOfYourNextUpkeepDelayedTriggeredAbility(effect);
                        game.addDelayedTriggeredAbility(delayedAbility, source);
                    }
                }
            } else {
                int zcc = sourcePermanent.getZoneChangeCounter(game);
                if (controller.moveCards(sourcePermanent, Zone.EXILED, source, game)) {
                    Effect effect = new ReturnToBattlefieldUnderOwnerControlTargetEffect();
                        effect.setTargetPointer(new FixedTarget(sourcePermanent.getId(), zcc + 1));
                        AtTheBeginOfYourNextUpkeepDelayedTriggeredAbility delayedAbility
                                = new AtTheBeginOfYourNextUpkeepDelayedTriggeredAbility(effect);
                        game.addDelayedTriggeredAbility(delayedAbility, source);
                    }
            }
            controller.loseLife(2, game);
            return true;
        }
        return false;
    }
}
