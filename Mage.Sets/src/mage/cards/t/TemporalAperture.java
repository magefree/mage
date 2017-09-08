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
package mage.cards.t;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.AsThoughEffectType;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author jeffwadsworth
 */
public class TemporalAperture extends CardImpl {

    public TemporalAperture(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        // {5}, {tap}: Shuffle your library, then reveal the top card. Until end of turn, for as long as that card remains on top of your library, play with the top card of your library revealed and you may play that card without paying its mana cost.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new TemporalApertureEffect(), new ManaCostsImpl("{5}"));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);

    }

    public TemporalAperture(final TemporalAperture card) {
        super(card);
    }

    @Override
    public TemporalAperture copy() {
        return new TemporalAperture(this);
    }
}

class TemporalApertureEffect extends OneShotEffect {

    public TemporalApertureEffect() {
        super(Outcome.Neutral);
        staticText = "Shuffle your library, then reveal the top card. Until end of turn, for as long as that card remains on top of your library, play with the top card of your library revealed and you may play that card without paying its mana cost";
    }

    public TemporalApertureEffect(final TemporalApertureEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            controller.shuffleLibrary(source, game);
            Card topCard = controller.getLibrary().getFromTop(game);
            Cards cards = new CardsImpl();
            if (topCard != null) {
                cards.add(topCard);
                controller.revealCards("Top card of " + controller.getName() + "'s library revealed", cards, game);
                ContinuousEffect effect = new TemporalApertureTopCardCastEffect(topCard);
                game.addEffect(effect, source);
                return true;
            }
        }
        return false;
    }

    @Override
    public TemporalApertureEffect copy() {
        return new TemporalApertureEffect(this);
    }
}

class TemporalApertureTopCardCastEffect extends AsThoughEffectImpl {

    private final Card card;

    public TemporalApertureTopCardCastEffect(Card card) {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, Duration.EndOfTurn, Outcome.Benefit);
        this.card = card;
        staticText = "Until end of turn, for as long as that card is on top of your library, you may cast it without paying its mana costs";
    }

    public TemporalApertureTopCardCastEffect(final TemporalApertureTopCardCastEffect effect) {
        super(effect);
        this.card = effect.card;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public TemporalApertureTopCardCastEffect copy() {
        return new TemporalApertureTopCardCastEffect(this);
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        if (affectedControllerId.equals(source.getControllerId())) {
            Card objectCard = game.getCard(objectId);
            if (objectCard != null) {
                Player controller = game.getPlayer(affectedControllerId);
                if (controller != null
                        && game.getState().getZone(objectId) == Zone.LIBRARY) {
                    if (controller.getLibrary().getFromTop(game).equals(card)) {
                        if (objectCard == card
                                && objectCard.getSpellAbility() != null
                                && objectCard.getSpellAbility().spellCanBeActivatedRegularlyNow(controller.getId(), game)) {
                            controller.setCastSourceIdWithAlternateMana(objectId, null, null);
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
}
