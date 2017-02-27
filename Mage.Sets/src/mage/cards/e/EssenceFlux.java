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
package mage.cards.e;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileTargetForSourceEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.cards.CardSetInfo;
import mage.cards.MeldCard;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.ExileZone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;

/**
 *
 * @author LevelX2
 */
public class EssenceFlux extends CardImpl {

    public EssenceFlux(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{U}");

        // Exile target creature you control, then return that card to the battlefield under its owner's control. If it's a Spirit, put a +1/+1 counter on it.
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());
        Effect effect = new ExileTargetForSourceEffect();
        effect.setApplyEffectsAfter();
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addEffect(new EssenceFluxEffect());
    }

    public EssenceFlux(final EssenceFlux card) {
        super(card);
    }

    @Override
    public EssenceFlux copy() {
        return new EssenceFlux(this);
    }
}

class EssenceFluxEffect extends OneShotEffect {

    EssenceFluxEffect() {
        super(Outcome.Benefit);
        staticText = "return that card to the battlefield under its owner's control.  If it's a Spirit, put a +1/+1 counter on it";
    }

    EssenceFluxEffect(final EssenceFluxEffect effect) {
        super(effect);
    }

    @Override
    public EssenceFluxEffect copy() {
        return new EssenceFluxEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Cards cardsToBattlefield = new CardsImpl();
            UUID exileZoneId = CardUtil.getExileZoneId(game, source.getSourceId(), source.getSourceObjectZoneChangeCounter());
            if (exileZoneId != null) {
                ExileZone exileZone = game.getExile().getExileZone(exileZoneId);
                if (exileZone != null) {
                    for (UUID targetId : this.getTargetPointer().getTargets(game, source)) {
                        if (exileZone.contains(targetId)) {
                            cardsToBattlefield.add(targetId);
                        }
                        else {
                            Card card = game.getCard(targetId);
                            if (card != null && card instanceof MeldCard) {
                                MeldCard meldCard = (MeldCard) card;
                                Card topCard = meldCard.getTopHalfCard();
                                Card bottomCard = meldCard.getBottomHalfCard();
                                if (topCard.getZoneChangeCounter(game) == meldCard.getTopLastZoneChangeCounter() && exileZone.contains(topCard.getId())) {
                                    cardsToBattlefield.add(topCard);
                                }
                                if (bottomCard.getZoneChangeCounter(game) == meldCard.getBottomLastZoneChangeCounter() && exileZone.contains(bottomCard.getId())) {
                                    cardsToBattlefield.add(bottomCard);
                                }
                            }
                        }
                    }
                }
            }
            if (!cardsToBattlefield.isEmpty()) {
                controller.moveCards(cardsToBattlefield.getCards(game), Zone.BATTLEFIELD, source, game, false, false, true, null);
                for (UUID cardId : cardsToBattlefield) {
                    Permanent permanent = game.getPermanent(cardId);
                    if (permanent != null && permanent.getSubtype(game).contains("Spirit")) {
                        Effect effect = new AddCountersTargetEffect(CounterType.P1P1.createInstance());
                        effect.setTargetPointer(new FixedTarget(permanent, game));
                        return effect.apply(game, source);
                    }
                }
            }
            return true;
        }
        return false;
    }
}
