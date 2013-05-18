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

package mage.sets.dragonsmaze;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Duration;
import mage.Constants.Layer;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.SubLayer;
import mage.Constants.Zone;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */


public class BloodBaronOfVizkopa extends CardImpl<BloodBaronOfVizkopa> {

    private static final FilterCard filter = new FilterCard("white and from black");
    static {
        filter.add(Predicates.or(
                new ColorPredicate(ObjectColor.WHITE),
                new ColorPredicate(ObjectColor.BLACK)));
    }

    public BloodBaronOfVizkopa (UUID ownerId) {
        super(ownerId, 57, "Blood Baron of Vizkopa", Rarity.MYTHIC, new CardType[]{CardType.CREATURE}, "{3}{W}{B}");
        this.expansionSetCode = "DGM";
        this.subtype.add("Vampire");
        this.color.setWhite(true);
        this.color.setBlack(true);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Lifelink, protection from white and from black.
        this.addAbility(LifelinkAbility.getInstance());
        this.addAbility(new ProtectionAbility(filter));

        // As long as you have 30 or more life and an opponent has 10 or less life, Blood Baron of Vizkopa gets +6/+6 and has flying.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BloodBaronOfVizkopaEffect()));

    }

    public BloodBaronOfVizkopa (final BloodBaronOfVizkopa card) {
        super(card);
    }

    @Override
    public BloodBaronOfVizkopa copy() {
        return new BloodBaronOfVizkopa(this);
    }

}

class BloodBaronOfVizkopaEffect extends ContinuousEffectImpl<BloodBaronOfVizkopaEffect> {
     
    public BloodBaronOfVizkopaEffect() {
        super(Duration.WhileOnBattlefield, Outcome.BoostCreature);
        staticText = "As long as you have 30 or more life and an opponent has 10 or less life, {this} gets +6/+6 and has flying";
    }

    public BloodBaronOfVizkopaEffect(final BloodBaronOfVizkopaEffect effect) {
        super(effect);
    }

    @Override
    public BloodBaronOfVizkopaEffect copy() {
        return new BloodBaronOfVizkopaEffect(this);
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        if (conditionState(source, game)) {
            Permanent creature = game.getPermanent(source.getSourceId());
            if (creature != null) {
                    switch (layer) {
                        case PTChangingEffects_7:
                            if (sublayer == SubLayer.ModifyPT_7c) {
                                creature.addPower(6);
                                creature.addToughness(6);
                            }
                            break;
                        case AbilityAddingRemovingEffects_6:
                            if (sublayer == SubLayer.NA) {
                                creature.addAbility(FlyingAbility.getInstance(), game);
                            }
                            break;
                        default:
                    }
                    return true;
            }
        }
        return false;
    }

    protected boolean conditionState(Ability source, Game game) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null && player.getLife() >= 30) {
            for (UUID opponentId :player.getInRange()) {
                Player opponent = game.getPlayer(opponentId);
                if (opponent != null && opponent.getLife() < 11) {
                    return true;
                }
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
        return (layer.equals(Layer.AbilityAddingRemovingEffects_6) || layer.equals(layer.PTChangingEffects_7));

   }

}
