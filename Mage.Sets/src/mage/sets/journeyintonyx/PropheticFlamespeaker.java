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
package mage.sets.journeyintonyx;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.AsThoughEffectType;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.PhaseStep;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Library;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public class PropheticFlamespeaker extends CardImpl<PropheticFlamespeaker> {

    public PropheticFlamespeaker(UUID ownerId) {
        super(ownerId, 106, "Prophetic Flamespeaker", Rarity.MYTHIC, new CardType[]{CardType.CREATURE}, "{1}{R}{R}");
        this.expansionSetCode = "JOU";
        this.subtype.add("Human");
        this.subtype.add("Shaman");

        this.color.setRed(true);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Double strike
        this.addAbility(DoubleStrikeAbility.getInstance());
        // Trample
        this.addAbility(TrampleAbility.getInstance());
        // Whenever Prophetic Flamespeaker deals combat damage to a player, exile the top card of your library. You may play it this turn.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new PropheticFlamespeakerExileEffect(), false));
    }

    public PropheticFlamespeaker(final PropheticFlamespeaker card) {
        super(card);
    }

    @Override
    public PropheticFlamespeaker copy() {
        return new PropheticFlamespeaker(this);
    }
}

class PropheticFlamespeakerExileEffect extends OneShotEffect<PropheticFlamespeakerExileEffect> {

    public PropheticFlamespeakerExileEffect() {
        super(Outcome.Detriment);
        this.staticText = "Exile the top card of your library. You may play it this turn";
    }

    public PropheticFlamespeakerExileEffect(final PropheticFlamespeakerExileEffect effect) {
        super(effect);
    }

    @Override
    public PropheticFlamespeakerExileEffect copy() {
        return new PropheticFlamespeakerExileEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent sourcePermanent = game.getPermanentOrLKIBattlefield(source.getSourceId());
        if (sourcePermanent != null && controller != null && controller.getLibrary().size() > 0) {
            Library library = controller.getLibrary();
            Card card = library.removeFromTop(game);
            if (card != null) {
                String exileName = new StringBuilder(sourcePermanent.getName()).append(" <this card may be played the turn it was exiled>").toString();
                controller.moveCardToExileWithInfo(card, source.getSourceId(), exileName, source.getSourceId(), game, Zone.LIBRARY);
                game.addEffect(new PropheticFlamespeakerCastFromExileEffect(card.getId()), source);
            }
            return true;
        }
        return false;
    }
}

class PropheticFlamespeakerCastFromExileEffect extends AsThoughEffectImpl<PropheticFlamespeakerCastFromExileEffect> {

    private final UUID cardId;

    public PropheticFlamespeakerCastFromExileEffect(UUID cardId) {
        super(AsThoughEffectType.CAST, Duration.EndOfTurn, Outcome.Benefit);
        staticText = "You may play card from exile";
        this.cardId = cardId;
    }

    public PropheticFlamespeakerCastFromExileEffect(final PropheticFlamespeakerCastFromExileEffect effect) {
        super(effect);
        cardId = effect.cardId;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public PropheticFlamespeakerCastFromExileEffect copy() {
        return new PropheticFlamespeakerCastFromExileEffect(this);
    }

    @Override
    public boolean applies(UUID sourceId, Ability source, Game game) {
        if (sourceId.equals(this.cardId)) {
            Card card = game.getCard(this.cardId);
            if (card != null && game.getState().getZone(this.cardId) == Zone.EXILED) {
                Player player = game.getPlayer(source.getControllerId());
                if (player != null ) {
                    if (card.getCardType().contains(CardType.LAND)) {
                        // If the revealed card is a land, you can play it only if it's your turn and you can play still a land this turn.
                        if (player.canPlayLand() 
                                && game.getActivePlayerId().equals(player.getId())
                                && game.getStack().isEmpty() 
                                && (game.getStep().getType().equals(PhaseStep.PRECOMBAT_MAIN) || game.getStep().getType().equals(PhaseStep.POSTCOMBAT_MAIN))
                                && player.chooseUse(Outcome.Benefit, "Play this card?", game)) {
                            return player.playLand(card, game);
                        }
                    } else {
                        Ability ability = card.getSpellAbility();
                        if (ability != null && ability instanceof SpellAbility 
                                && ((SpellAbility)ability).spellCanBeActivatedRegularlyNow(player.getId(), game) 
                                && player.chooseUse(Outcome.Benefit, "Play this card?", game)) {
                            return player.cast((SpellAbility) ability, game, false);
                        }
                    }
                }
            }
        }
        return false;
    }
}
