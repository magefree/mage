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
package mage.sets.theros;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.AsThoughEffectType;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.common.FilterNonlandCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetOpponent;
import mage.util.CardUtil;

/**
 *
 * @author LevelX2
 */
public class PsychicIntrusion extends CardImpl<PsychicIntrusion> {

    public PsychicIntrusion(UUID ownerId) {
        super(ownerId, 200, "Psychic Intrusion", Rarity.RARE, new CardType[]{CardType.SORCERY}, "{3}{U}{B}");
        this.expansionSetCode = "THS";

        this.color.setBlue(true);
        this.color.setBlack(true);

        // Target opponent reveals his or her hand. You choose a nonland card from that player's graveyard or hand and exile it.
        // You may cast that card for as long as it remains exiled, and you may spend mana as though it were mana of any color
        // to cast that spell.
        this.getSpellAbility().addTarget(new TargetOpponent());
        this.getSpellAbility().addEffect(new PsychicIntrusionExileEffect());

    }

    public PsychicIntrusion(final PsychicIntrusion card) {
        super(card);
    }

    @Override
    public PsychicIntrusion copy() {
        return new PsychicIntrusion(this);
    }
}

class PsychicIntrusionExileEffect extends OneShotEffect<PsychicIntrusionExileEffect> {

    private static final FilterNonlandCard filter = new FilterNonlandCard();

    public PsychicIntrusionExileEffect() {
        super(Outcome.Benefit);
        this.staticText = "Target opponent reveals his or her hand. You choose a nonland card from that player's graveyard or hand and exile it. You may cast that card for as long as it remains exiled, and you may spend mana as though it were mana of any color to cast that spell";
    }

    public PsychicIntrusionExileEffect(final PsychicIntrusionExileEffect effect) {
        super(effect);
    }

    @Override
    public PsychicIntrusionExileEffect copy() {
        return new PsychicIntrusionExileEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player opponent = game.getPlayer(source.getFirstTarget());
        if (opponent != null) {
            opponent.revealCards("Psychic Intrusion", opponent.getHand(), game);
            Player you = game.getPlayer(source.getControllerId());
            if (you != null) {
                int cardsGraveyard = opponent.getGraveyard().count(filter, game);
                int cardsHand = opponent.getHand().count(filter, game);
                boolean fromHand = false;
                if (cardsGraveyard > 0 && cardsHand > 0) {
                    if (opponent.chooseUse(Outcome.Detriment, "Exile card from opponents Hand?", game)) {
                        fromHand = true;
                    }
                }
                Card card = null;
                if (cardsHand > 0 && fromHand) {
                    TargetCard target = new TargetCard(Zone.PICK, filter);
                    target.setRequired(true);
                    if (you.choose(Outcome.Benefit, opponent.getHand(), target, game)) {
                        card = opponent.getHand().get(target.getFirstTarget(), game);

                    }
                }
                if (cardsGraveyard > 0 && !fromHand) {
                    TargetCard target = new TargetCard(Zone.PICK, filter);
                    target.setRequired(true);
                    if (you.choose(Outcome.Benefit, opponent.getGraveyard(), target, game)) {
                        card = opponent.getGraveyard().get(target.getFirstTarget(), game);

                    }
                }
                if (card != null) {
                    // move card to exile
                    UUID exileId = CardUtil.getCardExileZoneId(game, source);
                    card.moveToExile(exileId, "Daxos of Meletis", source.getSourceId(), game);
                    // allow to cast the card
                    game.addEffect(new PsychicIntrusionCastFromExileEffect(card.getId(), exileId), source);
                    // and you may spend mana as though it were mana of any color to cast it
                    game.addEffect(new PsychicIntrusionSpendAnyManaEffect(card.getId()), source);
                }

                return true;
            }
        }
        return false;
    }
}

class PsychicIntrusionCastFromExileEffect extends AsThoughEffectImpl<PsychicIntrusionCastFromExileEffect> {

    private UUID cardId;
    private UUID exileId;

    public PsychicIntrusionCastFromExileEffect(UUID cardId, UUID exileId) {
        super(AsThoughEffectType.CAST, Duration.EndOfGame, Outcome.Benefit);
        staticText = "You may cast that card for as long as it remains exiled, and you may spend mana as though it were mana of any color to cast that spell";
        this.cardId = cardId;
        this.exileId = exileId;
    }

    public PsychicIntrusionCastFromExileEffect(final PsychicIntrusionCastFromExileEffect effect) {
        super(effect);
        this.cardId = effect.cardId;
        this.exileId = effect.exileId;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public PsychicIntrusionCastFromExileEffect copy() {
        return new PsychicIntrusionCastFromExileEffect(this);
    }

    @Override
    public boolean applies(UUID sourceId, Ability source, Game game) {
        if (sourceId.equals(this.cardId)) {
            Card card = game.getCard(this.cardId);
            if (card != null && game.getState().getExile().getExileZone(exileId).contains(cardId)) {
                if (card.getSpellAbility().spellCanBeActivatedRegularlyNow(source.getControllerId(), game)) {
                    return true;
                }
            }
        }
        return false;
    }
}

class PsychicIntrusionSpendAnyManaEffect extends AsThoughEffectImpl<PsychicIntrusionSpendAnyManaEffect> {

    private UUID cardId;

    public PsychicIntrusionSpendAnyManaEffect(UUID cardId) {
        super(AsThoughEffectType.SPEND_ANY_MANA, Duration.EndOfGame, Outcome.Benefit);
        staticText = "you may spend mana as though it were mana of any color to cast it";
        this.cardId = cardId;
    }

    public PsychicIntrusionSpendAnyManaEffect(final PsychicIntrusionSpendAnyManaEffect effect) {
        super(effect);
        this.cardId = effect.cardId;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public PsychicIntrusionSpendAnyManaEffect copy() {
        return new PsychicIntrusionSpendAnyManaEffect(this);
    }

    @Override
    public boolean applies(UUID sourceId, Ability source, Game game) {
        if (sourceId.equals(this.cardId)) {
            return true;
        }
        return false;
    }
}
