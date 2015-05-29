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
package mage.sets.khansoftarkir;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.HexproofAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.AsThoughEffectType;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;

/**
 *
 * @author LevelX2
 */
public class NarsetEnlightenedMaster extends CardImpl {

    public NarsetEnlightenedMaster(UUID ownerId) {
        super(ownerId, 190, "Narset, Enlightened Master", Rarity.MYTHIC, new CardType[]{CardType.CREATURE}, "{3}{U}{R}{W}");
        this.expansionSetCode = "KTK";
        this.supertype.add("Legendary");
        this.subtype.add("Human");
        this.subtype.add("Monk");

        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());
        // Hexproof
        this.addAbility(HexproofAbility.getInstance());
        // Whenever Narset, Enlightented Master attacks, exile the top four cards of your library. Until end of turn, you may cast noncreature cards exiled with Narset this turn without paying their mana costs.
        this.addAbility(new AttacksTriggeredAbility(new NarsetEnlightenedMasterExileEffect(), false));

    }

    public NarsetEnlightenedMaster(final NarsetEnlightenedMaster card) {
        super(card);
    }

    @Override
    public NarsetEnlightenedMaster copy() {
        return new NarsetEnlightenedMaster(this);
    }
}

class NarsetEnlightenedMasterExileEffect extends OneShotEffect {

    public NarsetEnlightenedMasterExileEffect() {
      super(Outcome.Discard);
      staticText = "exile the top four cards of your library. Until end of turn, you may cast noncreature cards exiled with {this} this turn without paying their mana costs";
    }

    public NarsetEnlightenedMasterExileEffect(final NarsetEnlightenedMasterExileEffect effect) {
      super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source.getSourceId());
        if (player != null && sourceObject != null) {
            for (int i = 0; i < 4; i++) {
                if (player.getLibrary().size() > 0) {
                    Card card = player.getLibrary().getFromTop(game);
                    if (card != null) {
                        player.moveCardToExileWithInfo(card, CardUtil.getCardExileZoneId(game, source), sourceObject.getIdName(), source.getSourceId(), game, Zone.LIBRARY, true);
                        if (!card.getCardType().contains(CardType.CREATURE) && !card.getCardType().contains(CardType.LAND)) {
                            ContinuousEffect effect = new NarsetEnlightenedMasterCastFromExileEffect();
                            effect.setTargetPointer(new FixedTarget(card.getId()));
                            game.addEffect(effect, source);
                        }
                    }
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public NarsetEnlightenedMasterExileEffect copy() {
      return new NarsetEnlightenedMasterExileEffect(this);
    }
}

class NarsetEnlightenedMasterCastFromExileEffect extends AsThoughEffectImpl {

    public NarsetEnlightenedMasterCastFromExileEffect() {
        super(AsThoughEffectType.PLAY_FROM_NON_HAND_ZONE, Duration.EndOfTurn, Outcome.Benefit);
        staticText = "Until end of turn, you may cast noncreature cards exiled with {this} this turn without paying their mana costs";
    }

    public NarsetEnlightenedMasterCastFromExileEffect(final NarsetEnlightenedMasterCastFromExileEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public NarsetEnlightenedMasterCastFromExileEffect copy() {
        return new NarsetEnlightenedMasterCastFromExileEffect(this);
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        if (objectId.equals(getTargetPointer().getFirst(game, source)) && affectedControllerId.equals(source.getControllerId())) {
            Card card = game.getCard(objectId);
            if (card != null && game.getState().getZone(objectId) == Zone.EXILED) {
                Player player = game.getPlayer(affectedControllerId);
                player.setCastSourceIdWithAlternateMana(objectId, null);
                return true;
            }
        }
        return false;
    }
}
