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
package mage.cards.d;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.LimitedTimesPerTurnActivatedAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.BecomesColorTargetEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.ChoiceColor;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author L_J
 */
public class DreamCoat extends CardImpl {

    public DreamCoat(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{U}");
        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.Neutral));
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);

        // {0}: Enchanted creature becomes the color or colors of your choice. Activate this ability only once each turn.
        this.addAbility(new LimitedTimesPerTurnActivatedAbility(Zone.BATTLEFIELD, new BecomesColorOrColorsEnchantedEffect(), new GenericManaCost(0), 1));
    }

    public DreamCoat(final DreamCoat card) {
        super(card);
    }

    @Override
    public DreamCoat copy() {
        return new DreamCoat(this);
    }
}

class BecomesColorOrColorsEnchantedEffect extends OneShotEffect {

    public BecomesColorOrColorsEnchantedEffect() {
        super(Outcome.Neutral);
        this.staticText = "Enchanted creature becomes the color or colors of your choice";
    }

    public BecomesColorOrColorsEnchantedEffect(final BecomesColorOrColorsEnchantedEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent enchantment = game.getPermanentOrLKIBattlefield(source.getSourceId());
        Permanent permanent = game.getPermanent(enchantment.getAttachedTo());
        StringBuilder sb = new StringBuilder();

        if (controller != null && enchantment != null && permanent != null) {
            for (int i = 0; i < 5; i++) {
                if (i > 0) {
                    if (!controller.chooseUse(Outcome.Neutral, "Do you wish to choose another color?", source, game)) {
                        break;
                    }
                }
                ChoiceColor choiceColor = new ChoiceColor();
                if (!controller.choose(Outcome.Benefit, choiceColor, game)) {
                    return false;
                }
                if (!game.isSimulation()) {
                    game.informPlayers(permanent.getName() + ": " + controller.getLogName() + " has chosen " + choiceColor.getChoice());
                }
                if (choiceColor.getColor().isBlack()) {
                    sb.append('B');
                } else if (choiceColor.getColor().isBlue()) {
                    sb.append('U');
                } else if (choiceColor.getColor().isRed()) {
                    sb.append('R');
                } else if (choiceColor.getColor().isGreen()) {
                    sb.append('G');
                } else if (choiceColor.getColor().isWhite()) {
                    sb.append('W');
                }
            }
            String colors = new String(sb);
            ObjectColor chosenColors = new ObjectColor(colors);
            ContinuousEffect effect = new BecomesColorTargetEffect(chosenColors, Duration.Custom);
            effect.setTargetPointer(new FixedTarget(permanent.getId()));
            game.addEffect(effect, source);

            return true;
        }
        return false;
    }

    @Override
    public BecomesColorOrColorsEnchantedEffect copy() {
        return new BecomesColorOrColorsEnchantedEffect(this);
    }
}
