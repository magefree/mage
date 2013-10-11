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
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.DevotionCount;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DrawCardControllerEffect;
import mage.abilities.effects.common.continious.LoseCreatureTypeSourceEffect;
import mage.abilities.keyword.IndestructibleAbility;
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
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public class ErebosGodOfTheDead extends CardImpl<ErebosGodOfTheDead> {

    public ErebosGodOfTheDead(UUID ownerId) {
        super(ownerId, 85, "Erebos, God of the Dead", Rarity.MYTHIC, new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, "{3}{B}");
        this.expansionSetCode = "THS";
        this.supertype.add("Legendary");
        this.subtype.add("God");

        this.color.setBlack(true);
        this.power = new MageInt(5);
        this.toughness = new MageInt(7);

        // Indestructible
        this.addAbility(IndestructibleAbility.getInstance());
        // As long as your devotion to black is less than five, Erebos isn't a creature.
        Effect effect = new LoseCreatureTypeSourceEffect(new DevotionCount(ManaType.BLACK), 5);
        effect.setText("As long as your devotion to black is less than five, Erebos isn't a creature.<i>(Each {B} in the mana costs of permanents you control counts towards your devotion to black.)</i>");
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, effect));

        // Your opponents can't gain life.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new OpponentsCantGainLifeEffect()));

        // {1}{B}, Pay 2 life: Draw a card.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DrawCardControllerEffect(1), new ManaCostsImpl("{1}{B}"));
        ability.addCost(new PayLifeCost(2));
        this.addAbility(ability);

    }

    public ErebosGodOfTheDead(final ErebosGodOfTheDead card) {
        super(card);
    }

    @Override
    public ErebosGodOfTheDead copy() {
        return new ErebosGodOfTheDead(this);
    }
}

class OpponentsCantGainLifeEffect extends ContinuousEffectImpl<OpponentsCantGainLifeEffect> {

    public OpponentsCantGainLifeEffect() {
        super(Duration.WhileOnBattlefield, Layer.PlayerEffects, SubLayer.NA, Outcome.Benefit);
        staticText = "Your opponents can't gain life";
    }

    public OpponentsCantGainLifeEffect(final OpponentsCantGainLifeEffect effect) {
        super(effect);
    }

    @Override
    public OpponentsCantGainLifeEffect copy() {
        return new OpponentsCantGainLifeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (UUID playerId: game.getOpponents(source.getControllerId())) {
            Player player = game.getPlayer(playerId);
            if (player != null)
            {
                player.setCanGainLife(false);
            }
        }
        return true;
    }

}
