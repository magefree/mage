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
package mage.sets.fifthedition;

import java.util.UUID;

import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
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
 * @author Loki
 */
public class PrimalClay extends CardImpl<PrimalClay> {

    public PrimalClay(UUID ownerId) {
        super(ownerId, 395, "Primal Clay", Rarity.RARE, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{4}");
        this.expansionSetCode = "5ED";
        this.subtype.add("Shapeshifter");

        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // As Primal Clay enters the battlefield, it becomes your choice of a 3/3 artifact creature, a 2/2 artifact creature with flying, or a 1/6 Wall artifact creature with defender in addition to its other types.
        Ability ability = new SimpleStaticAbility(Constants.Zone.BATTLEFIELD, new EntersBattlefieldEffect(new PrimalClayEffect(), "As {this} enters the battlefield, it becomes your choice of a 3/3 artifact creature, a 2/2 artifact creature with flying, or a 1/6 Wall artifact creature with defender in addition to its other types"));
        ability.addChoice(new PrimalClayChoice());
        this.addAbility(ability);
    }

    public PrimalClay(final PrimalClay card) {
        super(card);
    }

    @Override
    public PrimalClay copy() {
        return new PrimalClay(this);
    }
}

class PrimalClayEffect extends ContinuousEffectImpl<PrimalClayEffect> {
    PrimalClayEffect() {
        super(Constants.Duration.WhileOnBattlefield, Constants.Outcome.BecomeCreature);
    }

    PrimalClayEffect(final PrimalClayEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Constants.Layer layer, Constants.SubLayer sublayer, Ability source, Game game) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        PrimalClayChoice choice = (PrimalClayChoice) source.getChoices().get(0);
        if (permanent == null)
            return false;

        switch (layer) {
            case TypeChangingEffects_4:
                permanent.getCardType().add(CardType.ARTIFACT);
                permanent.getCardType().add(CardType.CREATURE);
                break;
            case PTChangingEffects_7:
                if (choice.getChoice().equals("a 3/3 artifact creature")) {
                    permanent.getPower().setValue(3);
                    permanent.getToughness().setValue(3);
                } else if (choice.getChoice().equals("a 2/2 artifact creature with flying")) {
                    permanent.getPower().setValue(2);
                    permanent.getToughness().setValue(2);
                } else if (choice.getChoice().equals("a 1/6 Wall artifact creature with defender")) {
                    permanent.getPower().setValue(1);
                    permanent.getToughness().setValue(6);
                }
                break;
            case AbilityAddingRemovingEffects_6:
                if (choice.getChoice().equals("a 2/2 artifact creature with flying")) {
                    permanent.addAbility(FlyingAbility.getInstance(), game);
                } else if (choice.getChoice().equals("a 1/6 Wall artifact creature with defender")) {
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
    public boolean hasLayer(Constants.Layer layer) {
        return layer == Constants.Layer.PTChangingEffects_7 || layer == Constants.Layer.AbilityAddingRemovingEffects_6 || layer == Constants.Layer.TypeChangingEffects_4;
    }

    @Override
    public PrimalClayEffect copy() {
        return new PrimalClayEffect(this);
    }
}

class PrimalClayChoice extends ChoiceImpl<PrimalClayChoice> {
    PrimalClayChoice() {
        super(true);
        this.choices.add("a 3/3 artifact creature");
        this.choices.add("a 2/2 artifact creature with flying");
        this.choices.add("a 1/6 Wall artifact creature with defender");
    }

    PrimalClayChoice(final PrimalClayChoice choice) {
        super(choice);
    }

    @Override
    public PrimalClayChoice copy() {
        return new PrimalClayChoice(this);
    }
}