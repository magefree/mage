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
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.AsThoughEffectType;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Library;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author LevelX2
 */
public class PropheticFlamespeaker extends CardImpl {

    public PropheticFlamespeaker(UUID ownerId) {
        super(ownerId, 106, "Prophetic Flamespeaker", Rarity.MYTHIC, new CardType[]{CardType.CREATURE}, "{1}{R}{R}");
        this.expansionSetCode = "JOU";
        this.subtype.add("Human");
        this.subtype.add("Shaman");

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

class PropheticFlamespeakerExileEffect extends OneShotEffect {

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
                String exileName = new StringBuilder(sourcePermanent.getIdName()).append(" <this card may be played the turn it was exiled>").toString();
                controller.moveCardToExileWithInfo(card, source.getSourceId(), exileName, source.getSourceId(), game, Zone.LIBRARY, true);
                ContinuousEffect effect = new PropheticFlamespeakerCastFromExileEffect();
                effect.setTargetPointer(new FixedTarget(card.getId()));
                game.addEffect(effect, source);
            }
            return true;
        }
        return false;
    }
}

class PropheticFlamespeakerCastFromExileEffect extends AsThoughEffectImpl {

    public PropheticFlamespeakerCastFromExileEffect() {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, Duration.EndOfTurn, Outcome.Benefit);
        staticText = "You may play the card from exile";
    }

    public PropheticFlamespeakerCastFromExileEffect(final PropheticFlamespeakerCastFromExileEffect effect) {
        super(effect);
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
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        return source.getControllerId().equals(affectedControllerId) &&
                objectId.equals(getTargetPointer().getFirst(game, source));
    }
}
