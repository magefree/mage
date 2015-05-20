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
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.ManaTypeInManaPoolCount;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
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
public class OmnathLocusOfMana extends CardImpl {

    public OmnathLocusOfMana(UUID ownerId) {
        super(ownerId, 109, "Omnath, Locus of Mana", Rarity.MYTHIC, new CardType[]{CardType.CREATURE}, "{2}{G}");
        this.expansionSetCode = "WWK";
        this.supertype.add("Legendary");
        this.subtype.add("Elemental");

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Green mana doesn't empty from your mana pool as steps and phases end.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new OmnathRuleEffect()));

        // Omnath, Locus of Mana gets +1/+1 for each green mana in your mana pool
        DynamicValue boost = new ManaTypeInManaPoolCount(ManaType.GREEN);
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostSourceEffect(boost, boost, Duration.WhileOnBattlefield)));

    }

    public OmnathLocusOfMana(final OmnathLocusOfMana card) {
        super(card);
    }

    @Override
    public OmnathLocusOfMana copy() {
        return new OmnathLocusOfMana(this);
    }
}

class OmnathRuleEffect extends ContinuousEffectImpl {

    public OmnathRuleEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Detriment);
        staticText = "Green mana doesn't empty from your mana pool as steps and phases end";
    }

    public OmnathRuleEffect(final OmnathRuleEffect effect) {
        super(effect);
    }

    @Override
    public OmnathRuleEffect copy() {
        return new OmnathRuleEffect(this);
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null){
            player.getManaPool().addDoNotEmptyManaType(ManaType.GREEN);
        }
        return false;    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public boolean hasLayer(Layer layer) {
        return layer == Layer.RulesEffects;
    }
}
