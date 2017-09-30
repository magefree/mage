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
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.SacrificeTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Library;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author TheElk801
 */
public class KillerInstinct extends CardImpl {

    public KillerInstinct(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{4}{R}{G}");

        // At the beginning of your upkeep, reveal the top card of your library. If it's a creature card, put it onto the battlefield. That creature gains haste until end of turn. Sacrifice it at the beginning of the next end step.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new KillerInstinctEffect(), TargetController.YOU, false));
    }

    public KillerInstinct(final KillerInstinct card) {
        super(card);
    }

    @Override
    public KillerInstinct copy() {
        return new KillerInstinct(this);
    }
}

class KillerInstinctEffect extends OneShotEffect {

    KillerInstinctEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "reveal the top card of your library. If it's a creature card, put it onto the battlefield. That creature gains haste until end of turn. Sacrifice it at the beginning of the next end step.";
    }

    KillerInstinctEffect(final KillerInstinctEffect effect) {
        super(effect);
    }

    @Override
    public KillerInstinctEffect copy() {
        return new KillerInstinctEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        MageObject sourceObject = source.getSourceObjectIfItStillExists(game);
        if (player == null || sourceObject == null) {
            return false;
        }
        Library library = player.getLibrary();
        if (library == null || !library.hasCards()) {
            return false;
        }
        Card card = library.getFromTop(game);
        if (card == null) {
            return false;
        }
        player.revealCards(sourceObject.getIdName(), new CardsImpl(card), game);
        if (card.isCreature() && player.moveCards(card, Zone.BATTLEFIELD, source, game)) {
            Permanent permanent = game.getPermanent(card.getId());
            if (permanent != null) {
                FixedTarget ft = new FixedTarget(permanent, game);
                ContinuousEffect effect = new GainAbilityTargetEffect(HasteAbility.getInstance(), Duration.EndOfTurn);
                effect.setTargetPointer(ft);
                game.addEffect(effect, source);
                Effect sacrificeEffect = new SacrificeTargetEffect("Sacrifice it at the beginning of the next end step", source.getControllerId());
                sacrificeEffect.setTargetPointer(ft);
                game.addDelayedTriggeredAbility(new AtTheBeginOfNextEndStepDelayedTriggeredAbility(sacrificeEffect), source);
            }
            return true;
        }
        return false;
    }
}
