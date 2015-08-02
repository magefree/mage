/*
* Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
*
* Redistribution and use in source and binary forms, with or without modification, are
* permitted provided that the following conditions are met:
*
*    1. Redistributions of source code must retain the above copyright notice, this list of
*       conditions and the following disclaimer.
*
*    2. Redistributions in binary form must reproduce the above copyright notice, this list
*       of conditions and the following disclaimer in the documentation and/or other materials
*       provided with the distribution.
*
* THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
* WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
* FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
* CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
* CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
* SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
* ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
* NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
* ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*
* The views and conclusions contained in the software and documentation are those of the
* authors and should not be interpreted as representing official policies, either expressed
* or implied, of BetaSteward_at_googlemail.com.
*/

package mage.sets.zendikar;

import java.util.UUID;
import mage.MageObject;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.choices.ChoiceColor;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.filter.FilterCard;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class BraveTheElements extends CardImpl {

    public BraveTheElements(UUID ownerId) {
        super(ownerId, 4, "Brave the Elements", Rarity.UNCOMMON, new CardType[]{CardType.INSTANT}, "{W}");
        this.expansionSetCode = "ZEN";


        // Choose a color. White creatures you control gain protection from the chosen color until end of turn.
        this.getSpellAbility().addEffect(new BraveTheElementsChooseColorEffect());
    }

    public BraveTheElements(final BraveTheElements card) {
        super(card);
    }

    @Override
    public BraveTheElements copy() {
        return new BraveTheElements(this);
    }

}


class BraveTheElementsChooseColorEffect extends OneShotEffect {

    private static final FilterControlledCreaturePermanent filter1 = new FilterControlledCreaturePermanent();

    static {
        filter1.add(new ColorPredicate(ObjectColor.WHITE));
    }

    public BraveTheElementsChooseColorEffect() {
        super(Outcome.Benefit);
        this.staticText = "Choose a color. White creatures you control gain protection from the chosen color until end of turn";
    }

    public BraveTheElementsChooseColorEffect(final BraveTheElementsChooseColorEffect effect) {
        super(effect);
    }

    @Override
    public BraveTheElementsChooseColorEffect copy() {
        return new BraveTheElementsChooseColorEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source.getSourceId());
        if (sourceObject != null && controller != null) {
            ChoiceColor choice = new ChoiceColor();
            while (!choice.isChosen()) {
                controller.choose(outcome, choice, game);
                if (!controller.canRespond()) {
                    return false;
                }
            }
            if (choice.getColor() == null) {
                return false;
            }
            game.informPlayers(sourceObject.getName() + ": " + controller.getLogName() + " has chosen " + choice.getChoice());
            FilterCard filterColor = new FilterCard();
            filterColor.add(new ColorPredicate(choice.getColor()));
            filterColor.setMessage(choice.getChoice());
            ContinuousEffect effect = new GainAbilityAllEffect(new ProtectionAbility(new FilterCard(filterColor)), Duration.EndOfTurn, filter1);
            game.addEffect(effect, source);
            return true;
        }
        return false;
    }
}
