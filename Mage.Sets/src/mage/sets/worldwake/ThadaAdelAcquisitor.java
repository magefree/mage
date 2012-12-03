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
package mage.sets.worldwake;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.IslandwalkAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.filter.common.FilterArtifactCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author jeffwadsworth
 */
public class ThadaAdelAcquisitor extends CardImpl<ThadaAdelAcquisitor> {

    public ThadaAdelAcquisitor(UUID ownerId) {
        super(ownerId, 40, "Thada Adel, Acquisitor", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{1}{U}{U}");
        this.expansionSetCode = "WWK";
        this.supertype.add("Legendary");
        this.subtype.add("Merfolk");
        this.subtype.add("Rogue");

        this.color.setBlue(true);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Islandwalk
        this.addAbility(new IslandwalkAbility());

        // Whenever Thada Adel, Acquisitor deals combat damage to a player, search that player's library for an artifact card and exile it. Then that player shuffles his or her library. Until end of turn, you may play that card.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new ThadaAdelAcquisitorEffect(), false, true));
    }

    public ThadaAdelAcquisitor(final ThadaAdelAcquisitor card) {
        super(card);
    }

    @Override
    public ThadaAdelAcquisitor copy() {
        return new ThadaAdelAcquisitor(this);
    }
}

class ThadaAdelAcquisitorEffect extends OneShotEffect<ThadaAdelAcquisitorEffect> {

    ThadaAdelAcquisitorEffect() {
        super(Constants.Outcome.Exile);
        staticText = "search that player's library for an artifact card and exile it.  Then that player shuffles his or her library.  Until end of turn, you may play that card";
    }

    ThadaAdelAcquisitorEffect(final ThadaAdelAcquisitorEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player you = game.getPlayer(source.getControllerId());
        Player damagedPlayer = game.getPlayer(targetPointer.getFirst(game, source));
        if (you == null || damagedPlayer == null) {
            return false;
        }
        TargetCardInLibrary target = new TargetCardInLibrary(new FilterArtifactCard());
        you.searchLibrary(target, game, damagedPlayer.getId());
        if (you.searchLibrary(target, game)) {
            if (target.getTargets().size() > 0) {
                UUID cardId = target.getTargets().get(0);
                Card card = damagedPlayer.getLibrary().remove(cardId, game);
                if (card != null) {
                    card.moveToExile(source.getSourceId(), "Thada Adel", source.getId(), game);
                    game.addEffect(new ThadaAdelPlayFromExileEffect(card.getId()), source);
                }
            }
        }
        damagedPlayer.shuffleLibrary(game);
        return true;
    }

    @Override
    public ThadaAdelAcquisitorEffect copy() {
        return new ThadaAdelAcquisitorEffect(this);
    }
}

class ThadaAdelPlayFromExileEffect extends AsThoughEffectImpl<ThadaAdelPlayFromExileEffect> {

    private UUID cardId;

    public ThadaAdelPlayFromExileEffect(UUID cardId) {
        super(Constants.AsThoughEffectType.CAST, Constants.Duration.EndOfTurn, Constants.Outcome.Benefit);
        staticText = "You may play this card from exile";
        this.cardId = cardId;
    }

    public ThadaAdelPlayFromExileEffect(final ThadaAdelPlayFromExileEffect effect) {
        super(effect);
        cardId = effect.cardId;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public ThadaAdelPlayFromExileEffect copy() {
        return new ThadaAdelPlayFromExileEffect(this);
    }

    @Override
    public boolean applies(UUID sourceId, Ability source, Game game) {
        if (sourceId.equals(this.cardId)) {
            Card card = game.getCard(this.cardId);
            if (card != null && game.getState().getZone(this.cardId) == Constants.Zone.EXILED) {
                Player you = game.getPlayer(source.getControllerId());
                if (you != null && you.chooseUse(Constants.Outcome.Benefit, "Play the card?", game)) {
                    you.cast(card.getSpellAbility(), game, false);
                }
                return false;
            }
        }
        return false;
    }
}
