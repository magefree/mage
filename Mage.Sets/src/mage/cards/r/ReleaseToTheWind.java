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
package mage.cards.r;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AsThoughEffectType;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetNonlandPermanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author LevelX2
 */
public class ReleaseToTheWind extends CardImpl {

    public ReleaseToTheWind(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{U}");

        // Exile target nonland permanent. For as long as that card remains exiled, its owner may cast it without paying its mana cost.
        getSpellAbility().addEffect(new ReleaseToTheWindEffect());
        getSpellAbility().addTarget(new TargetNonlandPermanent());

    }

    public ReleaseToTheWind(final ReleaseToTheWind card) {
        super(card);
    }

    @Override
    public ReleaseToTheWind copy() {
        return new ReleaseToTheWind(this);
    }
}

class ReleaseToTheWindEffect extends OneShotEffect {

    public ReleaseToTheWindEffect() {
        super(Outcome.Benefit);
        this.staticText = "Exile target nonland permanent. For as long as that card remains exiled, its owner may cast it without paying its mana cost";
    }

    public ReleaseToTheWindEffect(final ReleaseToTheWindEffect effect) {
        super(effect);
    }

    @Override
    public ReleaseToTheWindEffect copy() {
        return new ReleaseToTheWindEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Permanent targetPermanent = game.getPermanent(getTargetPointer().getFirst(game, source));
            if (targetPermanent != null) {
                if (controller.moveCards(targetPermanent, Zone.EXILED, source, game)) {
                    Card card = game.getCard(targetPermanent.getId());
                    if (card != null) {
                        ContinuousEffect effect = new ReleaseToTheWindEffectCastFromExileEffect();
                        effect.setTargetPointer(new FixedTarget(card.getId(), card.getZoneChangeCounter(game)));
                        game.addEffect(effect, source);
                    }
                }
            }
            return true;
        }
        return false;
    }
}

class ReleaseToTheWindEffectCastFromExileEffect extends AsThoughEffectImpl {

    public ReleaseToTheWindEffectCastFromExileEffect() {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, Duration.Custom, Outcome.Benefit);
        staticText = "For as long as that card remains exiled, its owner may cast it without paying its mana cost";
    }

    public ReleaseToTheWindEffectCastFromExileEffect(final ReleaseToTheWindEffectCastFromExileEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public ReleaseToTheWindEffectCastFromExileEffect copy() {
        return new ReleaseToTheWindEffectCastFromExileEffect(this);
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        UUID ownerId = game.getOwnerId(objectId);
        if (objectId.equals(getTargetPointer().getFirst(game, source))) {
            if (affectedControllerId.equals(ownerId)) {
                Card card = game.getCard(objectId);
                Player player = game.getPlayer(ownerId);
                if (player != null && card != null) {
                    player.setCastSourceIdWithAlternateMana(objectId, null, card.getSpellAbility().getCosts());
                    return true;
                }
            }
        } else {
            if (((FixedTarget) getTargetPointer()).getTarget().equals(objectId)) {
                // object has moved zone so effect can be discarted
                this.discard();
            }
        }
        return false;
    }
}
