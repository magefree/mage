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
package mage.sets.planeshift;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.EntersBattlefieldEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.choices.ChoiceColor;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author anonymous
 */
public class VoiceOfAll extends CardImpl {

    public VoiceOfAll(UUID ownerId) {
        super(ownerId, 19, "Voice of All", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{2}{W}{W}");
        this.expansionSetCode = "PLS";
        this.subtype.add("Angel");
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // As Voice of All enters the battlefield, choose a color.
        // Voice of All has protection from the chosen color.
        this.getSpellAbility().addEffect(new EntersBattlefieldEffect(new VoiceOfAllEffect()));
    }

    public VoiceOfAll(final VoiceOfAll card) {
        super(card);
    }

    @Override
    public VoiceOfAll copy() {
        return new VoiceOfAll(this);
    }
}

class VoiceOfAllEffect extends OneShotEffect {
    
    public VoiceOfAllEffect() {
        super(Outcome.Protect);
        this.staticText = "{this} gains protection from the color of your choice until end of turn";
    }
    
    public VoiceOfAllEffect(final VoiceOfAllEffect effect) {
        super(effect);
    }
    
    @Override
    public VoiceOfAllEffect copy() {
        return new VoiceOfAllEffect(this);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        ChoiceColor choice = new ChoiceColor();
        choice.setMessage("Choose color to get protection from");
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null && controller.choose(outcome, choice, game)) {
            FilterCard protectionFilter = new FilterCard();
            protectionFilter.add(new ColorPredicate(choice.getColor()));
            protectionFilter.setMessage(choice.getChoice().toLowerCase());
            ContinuousEffect effect = new GainAbilitySourceEffect(new ProtectionAbility(protectionFilter), Duration.EndOfTurn);
            game.addEffect(effect, source);
            return true;
        }
        return false;
    }
}
