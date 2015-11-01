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
package mage.sets.futuresight;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Abilities;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.SubLayer;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author emerald000
 */
public class YixlidJailer extends CardImpl {

    public YixlidJailer(UUID ownerId) {
        super(ownerId, 93, "Yixlid Jailer", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{1}{B}");
        this.expansionSetCode = "FUT";
        this.subtype.add("Zombie");
        this.subtype.add("Wizard");

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Cards in graveyards lose all abilities.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new YixlidJailerEffect()));
    }

    public YixlidJailer(final YixlidJailer card) {
        super(card);
    }

    @Override
    public YixlidJailer copy() {
        return new YixlidJailer(this);
    }

    class YixlidJailerEffect extends ContinuousEffectImpl {

        YixlidJailerEffect() {
            super(Duration.WhileOnBattlefield, Outcome.LoseAbility);
            staticText = "Cards in graveyards lose all abilities.";
        }

        YixlidJailerEffect(final YixlidJailerEffect effect) {
            super(effect);
        }

        @Override
        public YixlidJailerEffect copy() {
            return new YixlidJailerEffect(this);
        }

        @Override
        public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
            if (layer == Layer.AbilityAddingRemovingEffects_6) {
                Player controller = game.getPlayer(source.getControllerId());
                if (controller != null) {
                    for (UUID playerId : controller.getInRange()) {
                        Player player = game.getPlayer(playerId);
                        if (player != null) {
                            for (Card card : player.getGraveyard().getCards(game)) {
                                if (card != null) {
                                    card.getAbilities(game).clear(); // Will the abilities ever come back????
                                    // TODO: Fix that (LevelX2)
                                    // game.getContinuousEffects().removeGainedEffectsForSource(card.getId());
                                    // game.getState().resetTriggersForSourceId(card.getId());
                                    Abilities abilities = game.getState().getAllOtherAbilities(card.getId());
                                    if (abilities != null) {
                                        abilities.clear();
                                    }
                                }
                            }
                        }
                    }
                    return true;
                }
            }
            return false;
        }

        @Override
        public boolean apply(Game game, Ability source) {
            return false;
        }

        @Override
        public boolean hasLayer(Layer layer) {
            return layer == Layer.AbilityAddingRemovingEffects_6;
        }
    }
}
