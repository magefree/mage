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
package mage.sets.commander2013;

import java.util.UUID;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.condition.InvertCondition;
import mage.abilities.condition.common.CardsInControllerGraveCondition;
import mage.abilities.decorator.ConditionalManaEffect;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.BasicManaEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DestroyAllEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.token.SpiritWhiteToken;

/**
 *
 * @author LevelX2
 */
public class KirtarsWrath extends CardImpl<KirtarsWrath> {

    public KirtarsWrath(UUID ownerId) {
        super(ownerId, 15, "Kirtar's Wrath", Rarity.RARE, new CardType[]{CardType.SORCERY}, "{4}{W}{W}");
        this.expansionSetCode = "C13";

        this.color.setWhite(true);

        // Destroy all creatures. They can't be regenerated.
        // Threshold - If seven or more cards are in your graveyard, instead destroy all creatures, then put two 1/1 white Spirit creature tokens with flying onto the battlefield. Creatures destroyed this way can't be regenerated.
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new KirtarsWrathEffect(),
                new DestroyAllEffect(new FilterCreaturePermanent("all creatures"), true),
                new CardsInControllerGraveCondition(7),
                "Destroy all creatures. They can't be regenerated.<br/><br/><i>Threshold<i/> - If seven or more cards are in your graveyard, instead destroy all creatures, then put two 1/1 white Spirit creature tokens with flying onto the battlefield. Creatures destroyed this way can't be regenerated"));

    }

    public KirtarsWrath(final KirtarsWrath card) {
        super(card);
    }

    @Override
    public KirtarsWrath copy() {
        return new KirtarsWrath(this);
    }
}

class KirtarsWrathEffect extends OneShotEffect<KirtarsWrathEffect> {

    public KirtarsWrathEffect() {
        super(Outcome.DestroyPermanent);
        this.staticText = "destroy all creatures, then put two 1/1 white Spirit creature tokens with flying onto the battlefield. Creatures destroyed this way can't be regenerated";
    }

    public KirtarsWrathEffect(final KirtarsWrathEffect effect) {
        super(effect);
    }

    @Override
    public KirtarsWrathEffect copy() {
        return new KirtarsWrathEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        new DestroyAllEffect(new FilterCreaturePermanent("all creatures"), true).apply(game, source);
        return new CreateTokenEffect(new SpiritWhiteToken(), 2).apply(game, source);
    }
}
