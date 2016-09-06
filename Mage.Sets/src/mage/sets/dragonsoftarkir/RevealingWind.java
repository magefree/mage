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
package mage.sets.dragonsoftarkir;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.PreventAllDamageByAllPermanentsEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.filter.common.FilterAttackingOrBlockingCreature;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.other.FaceDownPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public class RevealingWind extends CardImpl {

    public RevealingWind(UUID ownerId) {
        super(ownerId, 197, "Revealing Wind", Rarity.COMMON, new CardType[]{CardType.INSTANT}, "{2}{G}");
        this.expansionSetCode = "DTK";

        // Prevent all combat damage that would be dealt this turn. You may look at each face-down creature that's attacking or blocking.
        this.getSpellAbility().addEffect(new PreventAllDamageByAllPermanentsEffect(Duration.EndOfTurn, true));
        this.getSpellAbility().addEffect(new RevealingWindEffect());
    }

    public RevealingWind(final RevealingWind card) {
        super(card);
    }

    @Override
    public RevealingWind copy() {
        return new RevealingWind(this);
    }
}

class RevealingWindEffect extends OneShotEffect {

    private static final FilterCreaturePermanent filter = new FilterAttackingOrBlockingCreature("face-down creature that's attacking or blocking");

    static {
        filter.add(new FaceDownPredicate());
    }

    public RevealingWindEffect() {
        super(Outcome.Benefit);
        this.staticText = "You may look at each face-down creature that's attacking or blocking";
    }

    public RevealingWindEffect(final RevealingWindEffect effect) {
        super(effect);
    }

    @Override
    public RevealingWindEffect copy() {
        return new RevealingWindEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = source.getSourceObject(game);
        if (controller != null && sourceObject != null) {
            while (game.getBattlefield().count(filter, source.getOriginalId(), source.getControllerId(), game) > 0 &&
                    controller.chooseUse(outcome, "Look at a face-down attacking creature?", source, game)) {
                if (!controller.canRespond()) {
                    return false;
                }
                Target target = new TargetCreaturePermanent(filter);
                if (controller.chooseTarget(outcome, target, source, game)) {
                    Card card = game.getCard(target.getFirstTarget());
                    if (card != null) {
                        Cards cards = new CardsImpl();
                        cards.add(card);
                        controller.lookAtCards(sourceObject.getName(), cards, game);
                        game.informPlayers(controller.getLogName() + " look at a face-down attacking creature");
                    }
                }
            }
        }        
        return true;
    }
}
