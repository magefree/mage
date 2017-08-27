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
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilityControllerEffect;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.ChoiceColor;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public class SehtsTiger extends CardImpl {

    public SehtsTiger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}{W}");

        this.subtype.add(SubType.CAT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // When Seht's Tiger enters the battlefield, you gain protection from the color of your choice until end of turn.
        // (You can't be targeted, dealt damage, or enchanted by anything of the chosen color.)
        this.addAbility(new EntersBattlefieldTriggeredAbility(new SehtsTigerEffect(), false));

    }

    public SehtsTiger(final SehtsTiger card) {
        super(card);
    }

    @Override
    public SehtsTiger copy() {
        return new SehtsTiger(this);
    }
}

class SehtsTigerEffect extends OneShotEffect {

    public SehtsTigerEffect() {
        super(Outcome.Protect);
        staticText = "you gain protection from the color of your choice until end of turn <i>(You can't be targeted, dealt damage, or enchanted by anything of the chosen color.)</i>";
    }

    public SehtsTigerEffect(final SehtsTigerEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject mageObject = game.getObject(source.getSourceId());
        if (controller != null && mageObject != null) {
            ChoiceColor choice = new ChoiceColor();
            while (!choice.isChosen()) {
                controller.choose(Outcome.Protect, choice, game);
                if (!controller.canRespond()) {
                    return false;
                }
            }
            if (choice.getColor() != null) {
                game.informPlayers(mageObject.getLogName() + ": " + controller.getLogName() + " has chosen " + choice.getChoice());
                FilterCard filter = new FilterCard();
                filter.add(new ColorPredicate(choice.getColor()));
                filter.setMessage(choice.getChoice());
                Ability ability = new ProtectionAbility(filter);
                game.addEffect(new GainAbilityControllerEffect(ability, Duration.EndOfTurn), source);
                return true;
            }

        }
        return false;
    }

    @Override
    public SehtsTigerEffect copy() {
        return new SehtsTigerEffect(this);
    }

}
