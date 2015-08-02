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
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.effects.common.continuous.SetPowerToughnessSourceEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.choices.Choice;
import mage.choices.ChoiceImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public class PrimalPlasma extends CardImpl {

    public PrimalPlasma(UUID ownerId) {
        super(ownerId, 23, "Primal Plasma", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{3}{U}");
        this.expansionSetCode = "PC2";
        this.subtype.add("Elemental");
        this.subtype.add("Shapeshifter");

        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // As Primal Plasma enters the battlefield, it becomes your choice of a 3/3 creature, a 2/2 creature with flying, or a 1/6 creature with defender.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new PrimalPlasmaReplacementEffect()));
    }

    public PrimalPlasma(final PrimalPlasma card) {
        super(card);
    }

    @Override
    public PrimalPlasma copy() {
        return new PrimalPlasma(this);
    }
}

class PrimalPlasmaReplacementEffect extends ReplacementEffectImpl {

    private final String choice33 = "a 3/3 creature";
    private final String choice22 = "a 2/2 creature with flying";
    private final String choice16 = "a 1/6 creature with defender";

    public PrimalPlasmaReplacementEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "As {this} enters the battlefield, it becomes your choice of a 3/3 creature, a 2/2 creature with flying, or a 1/6 creature with defender";
    }

    public PrimalPlasmaReplacementEffect(PrimalPlasmaReplacementEffect effect) {
        super(effect);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType().equals(EventType.ENTERS_THE_BATTLEFIELD);
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getTargetId().equals(source.getSourceId())) {
            Permanent sourcePermanent = game.getPermanent(source.getSourceId());
            if (sourcePermanent != null && !sourcePermanent.isFaceDown(game)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent != null) {
            Choice choice = new ChoiceImpl(true);
            choice.setMessage("Choose what " + permanent.getIdName() + " becomes to");
            choice.getChoices().add(choice33);
            choice.getChoices().add(choice22);
            choice.getChoices().add(choice16);
            Player controller = game.getPlayer(source.getControllerId());
            if (controller != null) {
                while (!choice.isChosen()) {
                    controller.choose(Outcome.Neutral, choice, game);
                    if (!controller.isInGame()) {
                        return false;
                    }
                }
            }
            int power = 0;
            int toughness = 0;
            switch (choice.getChoice()) {
                case choice33:
                    power = 3;
                    toughness = 3;
                    break;
                case choice22:
                    power = 2;
                    toughness = 2;
                    game.addEffect(new GainAbilitySourceEffect(FlyingAbility.getInstance(), Duration.Custom), source);
                    break;
                case choice16:
                    power = 1;
                    toughness = 6;
                    game.addEffect(new GainAbilitySourceEffect(DefenderAbility.getInstance(), Duration.Custom), source);
                    break;
            }
            game.addEffect(new SetPowerToughnessSourceEffect(power, toughness, Duration.Custom), source);

        }
        return false;

    }

    @Override
    public PrimalPlasmaReplacementEffect copy() {
        return new PrimalPlasmaReplacementEffect(this);
    }

}
