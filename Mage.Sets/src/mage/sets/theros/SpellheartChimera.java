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
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.SubLayer;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.common.FilterInstantOrSorceryCard;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public class SpellheartChimera extends CardImpl<SpellheartChimera> {

    public SpellheartChimera(UUID ownerId) {
        super(ownerId, 204, "Spellheart Chimera", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{1}{U}{R}");
        this.expansionSetCode = "THS";
        this.subtype.add("Chimera");

        this.color.setRed(true);
        this.color.setBlue(true);
        this.power = new MageInt(0);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Trample
        this.addAbility(TrampleAbility.getInstance());
        // Spellheart Chimera's power is equal to the number of instant and sorcery cards in your graveyard.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new SpellheartChimeraEffect()));
    }

    public SpellheartChimera(final SpellheartChimera card) {
        super(card);
    }

    @Override
    public SpellheartChimera copy() {
        return new SpellheartChimera(this);
    }
}

class SpellheartChimeraEffect extends ContinuousEffectImpl<SpellheartChimeraEffect> {

    private static final FilterCard filter = new FilterInstantOrSorceryCard();

    public SpellheartChimeraEffect() {
        super(Duration.WhileOnBattlefield, Layer.PTChangingEffects_7, SubLayer.SetPT_7b, Outcome.BoostCreature);
        staticText = "{this}'s power is equal to the number of instant and sorcery cards in your graveyard";
    }


    public SpellheartChimeraEffect(final SpellheartChimeraEffect effect) {
        super(effect);
    }

    @Override
    public SpellheartChimeraEffect copy() {
        return new SpellheartChimeraEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent target = game.getPermanent(source.getSourceId());
        if (target != null) {
            int number = 0;

            Player player = game.getPlayer(source.getControllerId());
            if (player != null) {
                number =  player.getGraveyard().count(filter, game);
            }

            target.getPower().setValue(number);
            return true;

        }
        return false;
    }

}
