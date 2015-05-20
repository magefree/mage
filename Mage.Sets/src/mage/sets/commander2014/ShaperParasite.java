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
package mage.sets.commander2014;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.TurnedFaceUpSourceTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.keyword.MorphAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.SubLayer;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public class ShaperParasite extends CardImpl {

    public ShaperParasite(UUID ownerId) {
        super(ownerId, 125, "Shaper Parasite", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{1}{U}{U}");
        this.expansionSetCode = "C14";
        this.subtype.add("Illusion");

        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Morph {2}{U}
        this.addAbility(new MorphAbility(this, new ManaCostsImpl("{2}{U}")));
        // When Shaper Parasite is turned face up, target creature gets +2/-2 or -2/+2 until end of turn.
        Ability ability = new TurnedFaceUpSourceTriggeredAbility(new ShaperParasiteEffect());
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    public ShaperParasite(final ShaperParasite card) {
        super(card);
    }

    @Override
    public ShaperParasite copy() {
        return new ShaperParasite(this);
    }
}

class ShaperParasiteEffect extends ContinuousEffectImpl {

    private int power;
    private int toughness;

    public ShaperParasiteEffect() {
        super(Duration.EndOfTurn, Layer.PTChangingEffects_7, SubLayer.ModifyPT_7c, Outcome.BoostCreature);
        this.power = 2;
        this.toughness = -2;
        this.staticText = "target creature gets +2/-2 or -2/+2 until end of turn";
    }

    public ShaperParasiteEffect(final ShaperParasiteEffect effect) {
        super(effect);
        this.power = effect.power;
        this.toughness = effect.toughness;
    }

    @Override
    public ShaperParasiteEffect copy() {
        return new ShaperParasiteEffect(this);
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        Player player = game.getPlayer(source.getControllerId());
        String message = "Should the target creature get -2/+2 instead of +2/-2?";
        if (player != null && player.chooseUse(Outcome.Neutral, message, game)) {
            this.power *= -1;
            this.toughness *= -1;
        }
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent target = game.getPermanent(source.getFirstTarget());
        if (target != null) {
            target.addPower(power);
            target.addToughness(toughness);
            return true;
        }
        return false;
    }
}
