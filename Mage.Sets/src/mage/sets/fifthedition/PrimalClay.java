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

import java.util.Set;
import java.util.UUID;

import mage.constants.*;
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
import mage.players.Player;

/**
 *
 * @author Loki
 */
public class PrimalClay extends CardImpl {

    public PrimalClay(UUID ownerId) {
        super(ownerId, 395, "Primal Clay", Rarity.RARE, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{4}");
        this.expansionSetCode = "5ED";
        this.subtype.add("Shapeshifter");

        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // As Primal Clay enters the battlefield, it becomes your choice of a 3/3 artifact creature, a 2/2 artifact creature with flying, or a 1/6 Wall artifact creature with defender in addition to its other types.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new EntersBattlefieldEffect(new PrimalClayEffect(), "As {this} enters the battlefield, it becomes your choice of a 3/3 artifact creature, a 2/2 artifact creature with flying, or a 1/6 Wall artifact creature with defender in addition to its other types")));
    }

    public PrimalClay(final PrimalClay card) {
        super(card);
    }

    @Override
    public PrimalClay copy() {
        return new PrimalClay(this);
    }
}

class PrimalClayEffect extends ContinuousEffectImpl {

    private final static String choice1 = "a 3/3 artifact creature";
    private final static String choice2 = "a 2/2 artifact creature with flying";
    private final static String choice3 = "a 1/6 Wall artifact creature with defender";

    String choice;

    PrimalClayEffect() {
        super(Duration.WhileOnBattlefield, Outcome.BecomeCreature);
    }

    PrimalClayEffect(final PrimalClayEffect effect) {
        super(effect);
        this.choice = effect.choice;
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        Player controller = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (controller != null && permanent != null) {
            ChoiceImpl primalClayChoice = new ChoiceImpl();
            Set<String> choices =  primalClayChoice.getChoices();
            choices.add(choice1);
            choices.add(choice2);
            choices.add(choice3);
            primalClayChoice.setMessage("Choose for " + permanent.getLogName() + " to be");
            while (!primalClayChoice.isChosen()) {
                if (!controller.isInGame()) {
                    discard();
                    return;
                }
                controller.choose(outcome, primalClayChoice, game);
            }
            this.choice = primalClayChoice.getChoice();
            return;
        }
        discard();
    }


    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent == null) {
            discard();
            return false;
        }
        switch (layer) {
            case PTChangingEffects_7:
                if (sublayer.equals(SubLayer.SetPT_7b)) {
                    switch (choice) {
                        case choice1:
                            permanent.getPower().setValue(3);
                            permanent.getToughness().setValue(3);
                            break;
                        case choice2:
                            permanent.getPower().setValue(2);
                            permanent.getToughness().setValue(2);
                            break;
                        case choice3:
                            permanent.getPower().setValue(1);
                            permanent.getToughness().setValue(6);
                            break;
                    }
                }
                break;
            case AbilityAddingRemovingEffects_6:
                switch (choice) {
                    case choice2:
                        permanent.addAbility(FlyingAbility.getInstance(), source.getSourceId(), game);
                        break;
                    case choice3:
                        permanent.addAbility(DefenderAbility.getInstance(), source.getSourceId(),  game);
                        break;
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
    public PrimalClayEffect copy() {
        return new PrimalClayEffect(this);
    }
}
