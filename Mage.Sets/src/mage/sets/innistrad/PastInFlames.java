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
package mage.sets.innistrad;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Duration;
import mage.Constants.Layer;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.SubLayer;
import mage.Constants.TimingRule;
import mage.abilities.Ability;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author BetaSteward
 */
public class PastInFlames extends CardImpl<PastInFlames> {

    public PastInFlames(UUID ownerId) {
        super(ownerId, 155, "Past in Flames", Rarity.MYTHIC, new CardType[]{CardType.SORCERY}, "{3}{R}");
        this.expansionSetCode = "ISD";

        this.color.setRed(true);

        // Each instant and sorcery card in your graveyard gains flashback until end of turn. The flashback cost is equal to its mana cost.
        this.getSpellAbility().addEffect(new PastInFlamesEffect());
        
        // Flashback {4}{R}
        this.addAbility(new FlashbackAbility(new ManaCostsImpl("{4}{R}"), TimingRule.SORCERY));

    }

    public PastInFlames(final PastInFlames card) {
        super(card);
    }

    @Override
    public PastInFlames copy() {
        return new PastInFlames(this);
    }
}

class PastInFlamesEffect extends ContinuousEffectImpl<PastInFlamesEffect> {

	public PastInFlamesEffect() {
		super(Duration.EndOfTurn, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.AddAbility);
        this.staticText = "Each instant and sorcery card in your graveyard gains flashback until end of turn. The flashback cost is equal to its mana cost";
	}

	public PastInFlamesEffect(final PastInFlamesEffect effect) {
		super(effect);
	}

	@Override
	public PastInFlamesEffect copy() {
		return new PastInFlamesEffect(this);
	}
    
    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        if (this.affectedObjectsSet) {
            Player player = game.getPlayer(source.getControllerId());
            if (player != null) {
                for (UUID cardId: player.getGraveyard()) {
                    Card card = game.getCard(cardId);
                    if (card.getCardType().contains(CardType.INSTANT) || card.getCardType().contains(CardType.SORCERY)) {
                        objects.add(cardId);
                    }
                }
             }
        }
    }

	@Override
	public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            for (UUID cardId: player.getGraveyard()) {
                if (objects.contains(cardId)) {
                    Card card = game.getCard(cardId);
                    FlashbackAbility ability = null;
                    if (card.getCardType().contains(CardType.INSTANT))
                        ability = new FlashbackAbility(card.getManaCost(), TimingRule.INSTANT);
                    else if (card.getCardType().contains(CardType.SORCERY))
                        ability = new FlashbackAbility(card.getManaCost(), TimingRule.SORCERY);
                    if (ability != null) {
                        ability.setSourceId(cardId);
                        ability.setControllerId(card.getOwnerId());
                        game.getState().addOtherAbility(cardId, ability);
                    }
                }
            }
            return true;
        }
		return false;
	}
}