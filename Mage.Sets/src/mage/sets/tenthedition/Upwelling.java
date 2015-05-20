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
package mage.sets.tenthedition;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.ManaType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.SubLayer;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.ManaPool;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public class Upwelling extends CardImpl {

    public Upwelling(UUID ownerId) {
        super(ownerId, 306, "Upwelling", Rarity.RARE, new CardType[]{CardType.ENCHANTMENT}, "{3}{G}");
        this.expansionSetCode = "10E";


        // Mana pools don't empty as steps and phases end.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new UpwellingRuleEffect()));

    }

    public Upwelling(final Upwelling card) {
        super(card);
    }

    @Override
    public Upwelling copy() {
        return new Upwelling(this);
    }
}

class UpwellingRuleEffect extends ContinuousEffectImpl {

    public UpwellingRuleEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Detriment);
        staticText = "Mana pools don't empty as steps and phases end";
    }

    public UpwellingRuleEffect(final UpwellingRuleEffect effect) {
        super(effect);
    }

    @Override
    public UpwellingRuleEffect copy() {
        return new UpwellingRuleEffect(this);
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            for (UUID playerId: controller.getInRange()) {
                Player player = game.getPlayer(playerId);
                if (player != null){
                    ManaPool pool = player.getManaPool();
                    pool.addDoNotEmptyManaType(ManaType.WHITE);
                    pool.addDoNotEmptyManaType(ManaType.GREEN);
                    pool.addDoNotEmptyManaType(ManaType.BLUE);
                    pool.addDoNotEmptyManaType(ManaType.RED);
                    pool.addDoNotEmptyManaType(ManaType.BLACK);
                    pool.addDoNotEmptyManaType(ManaType.COLORLESS);
                }                
            }
            return true;
        }
        return false;    
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public boolean hasLayer(Layer layer) {
        return layer == Layer.RulesEffects;
    }
}
