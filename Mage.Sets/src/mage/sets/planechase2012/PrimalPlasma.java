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
package mage.sets.planechase2012;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Layer;
import mage.Constants.Rarity;
import mage.Constants.SubLayer;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.EntersBattlefieldEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.choices.ChoiceImpl;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author LevelX2
 */
public class PrimalPlasma extends CardImpl<PrimalPlasma> {

    public PrimalPlasma(UUID ownerId) {
        super(ownerId, 23, "Primal Plasma", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{3}{U}");
        this.expansionSetCode = "PC2";
        this.subtype.add("Elemental");
        this.subtype.add("Shapeshifter");

        this.color.setBlue(true);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // As Primal Plasma enters the battlefield, it becomes your choice of a 3/3 creature, a 2/2 creature with flying, or a 1/6 creature with defender.
        Ability ability = new SimpleStaticAbility(Constants.Zone.BATTLEFIELD, new EntersBattlefieldEffect(new PrimalPlasmaEffect(), "As {this} enters the battlefield, it becomes your choice of a 3/3 creature, a 2/2 creature with flying, or a 1/6 creature with defender"));
        ability.addChoice(new PrimalPlasmaChoice());
        this.addAbility(ability);
    }

    public PrimalPlasma(final PrimalPlasma card) {
        super(card);
    }

    @Override
    public PrimalPlasma copy() {
        return new PrimalPlasma(this);
    }
}

class PrimalPlasmaEffect extends ContinuousEffectImpl<PrimalPlasmaEffect> {
    PrimalPlasmaEffect() {
        super(Constants.Duration.WhileOnBattlefield, Constants.Outcome.BecomeCreature);
    }

    PrimalPlasmaEffect(final PrimalPlasmaEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        PrimalPlasmaChoice choice = (PrimalPlasmaChoice) source.getChoices().get(0);
        if (permanent == null) {
            return false;
        }

        switch (layer) {
            case PTChangingEffects_7:
                if (sublayer.equals(SubLayer.SetPT_7b)) {
                    if (choice.getChoice().equals("a 3/3 creature")) {
                        permanent.getPower().setValue(3);
                        permanent.getToughness().setValue(3);
                    } else if (choice.getChoice().equals("a 2/2 creature with flying")) {
                        permanent.getPower().setValue(2);
                        permanent.getToughness().setValue(2);
                    } else if (choice.getChoice().equals("a 1/6 creature with defender")) {
                        permanent.getPower().setValue(1);
                        permanent.getToughness().setValue(6);
                    }
                }
                break;
            case AbilityAddingRemovingEffects_6:
                if (choice.getChoice().equals("a 2/2 creature with flying")) {
                    permanent.addAbility(FlyingAbility.getInstance(), game);
                } else if (choice.getChoice().equals("a 1/6 creature with defender")) {
                    permanent.addAbility(DefenderAbility.getInstance(), game);
                }
                break;
        }
        return true;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public boolean hasLayer(Layer layer) {
        return layer == Layer.PTChangingEffects_7 || layer == Layer.AbilityAddingRemovingEffects_6 || layer == Layer.TypeChangingEffects_4;
    }

    @Override
    public PrimalPlasmaEffect copy() {
        return new PrimalPlasmaEffect(this);
    }
}
class PrimalPlasmaChoice extends ChoiceImpl<PrimalPlasmaChoice> {
    PrimalPlasmaChoice() {
        super(true);
        this.choices.add("a 3/3 creature");
        this.choices.add("a 2/2 creature with flying");
        this.choices.add("a 1/6 creature with defender");
    }

    PrimalPlasmaChoice(final PrimalPlasmaChoice choice) {
        super(choice);
    }

    @Override
    public PrimalPlasmaChoice copy() {
        return new PrimalPlasmaChoice(this);
    }
}