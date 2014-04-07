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
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.choices.ChoiceColor;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.SubLayer;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;


/**
 *
 * @author LevelX2
 */
public class HallOfTriumph extends CardImpl<HallOfTriumph> {

    public HallOfTriumph(UUID ownerId) {
        super(ownerId, 162, "Hall of Triumph", Rarity.RARE, new CardType[]{CardType.ARTIFACT}, "{3}");
        this.expansionSetCode = "JOU";
        this.supertype.add("Legendary");

        // As Hall of Triumph enters the battlefield choose a color.
        this.addAbility(new AsEntersBattlefieldAbility(new HallOfTriumphEffect()));        
        // Creatures you control of the chosen color get +1/+1.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new HallOfTriumphBoostControlledEffect()));
    }

    public HallOfTriumph(final HallOfTriumph card) {
        super(card);
    }

    @Override
    public HallOfTriumph copy() {
        return new HallOfTriumph(this);
    }
}

class HallOfTriumphEffect extends OneShotEffect<HallOfTriumphEffect> {

    public HallOfTriumphEffect() {
        super(Outcome.BoostCreature);
        staticText = "choose a color";
    }

    public HallOfTriumphEffect(final HallOfTriumphEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (player != null && permanent != null) {
            ChoiceColor colorChoice = new ChoiceColor();
            colorChoice.setMessage("Choose color");
            while (!player.choose(Outcome.BoostCreature, colorChoice, game)) {
                if (!player.isInGame()) {
                    return false;
                }
            }
            if (colorChoice.getChoice() != null) {
                game.informPlayers(permanent.getName() + ": " + player.getName() + " has chosen " + colorChoice.getChoice());
                game.getState().setValue(permanent.getId() + "_color", colorChoice.getColor());
                permanent.addInfo("chosen color", "<i>Chosen color: " + colorChoice.getChoice().toString() + "</i>");
            }
        }
        return false;
    }

    @Override
    public HallOfTriumphEffect copy() {
        return new HallOfTriumphEffect(this);
    }

}

class HallOfTriumphBoostControlledEffect extends ContinuousEffectImpl<HallOfTriumphBoostControlledEffect> {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent();

    public HallOfTriumphBoostControlledEffect() {
        super(Duration.WhileOnBattlefield, Layer.PTChangingEffects_7, SubLayer.ModifyPT_7c, Outcome.BoostCreature);
        staticText = "Creatures you control of the chosen color get +1/+1";
    }

    public HallOfTriumphBoostControlledEffect(final HallOfTriumphBoostControlledEffect effect) {
        super(effect);
    }

    @Override
    public HallOfTriumphBoostControlledEffect copy() {
        return new HallOfTriumphBoostControlledEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        ObjectColor color = (ObjectColor) game.getState().getValue(source.getSourceId() + "_color");
        if (color != null) {
            for (Permanent perm: game.getBattlefield().getAllActivePermanents(filter, source.getControllerId(), game)) {
                if (perm.getColor().shares(color)) {
                    perm.addPower(1);
                    perm.addToughness(1);
                }
            }
            return true;
        }
        return false;
    }

}
