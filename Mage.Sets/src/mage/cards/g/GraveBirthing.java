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
package mage.sets.battleforzendikar;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.DevoidAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.token.EldraziScionToken;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.common.TargetOpponent;

/**
 *
 * @author LevelX2
 */
public class GraveBirthing extends CardImpl {

    public GraveBirthing(UUID ownerId) {
        super(ownerId, 93, "Grave Birthing", Rarity.COMMON, new CardType[]{CardType.INSTANT}, "{2}{B}");
        this.expansionSetCode = "BFZ";

        // Devoid
        Ability ability = new DevoidAbility(this.color);
        ability.setRuleAtTheTop(true);
        this.addAbility(ability);
        // Target opponent exiles a card from his or her graveyard. You put a 1/1 colorless Eldrazi Scion creature token onto the battlefield. It has "Sacrifice this creature: Add {C} to your mana pool."
        this.getSpellAbility().addEffect(new GraveBirthingEffect());
        this.getSpellAbility().addTarget(new TargetOpponent());
        Effect effect = new CreateTokenEffect(new EldraziScionToken());
        effect.setText("You put a 1/1 colorless Eldrazi Scion creature token onto the battlefield. It has \"Sacrifice this creature: Add {C} to your mana pool.\"<br>");
        this.getSpellAbility().addEffect(effect);        // Draw a card.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1));
    }

    public GraveBirthing(final GraveBirthing card) {
        super(card);
    }

    @Override
    public GraveBirthing copy() {
        return new GraveBirthing(this);
    }
}

class GraveBirthingEffect extends OneShotEffect {

    public GraveBirthingEffect() {
        super(Outcome.Benefit);
        this.staticText = "Target opponent exiles a card from his or her graveyard";
    }

    public GraveBirthingEffect(final GraveBirthingEffect effect) {
        super(effect);
    }

    @Override
    public GraveBirthingEffect copy() {
        return new GraveBirthingEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player opponent = game.getPlayer(getTargetPointer().getFirst(game, source));
        if (opponent != null) {
            Target target = new TargetCardInYourGraveyard();
            target.setNotTarget(true);
            opponent.chooseTarget(outcome, target, source, game);
            Card card = game.getCard(target.getFirstTarget());
            opponent.moveCards(card, Zone.EXILED, source, game);
            return true;
        }
        return false;
    }
}
