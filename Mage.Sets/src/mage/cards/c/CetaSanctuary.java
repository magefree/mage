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
package mage.cards.c;

import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.UUID;

/**
 *
 * @author Pete Rossi
 */
public class CetaSanctuary extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("a red or green permanent");

    static {
        filter.add(Predicates.or(new ColorPredicate(ObjectColor.RED), new ColorPredicate(ObjectColor.GREEN)));
    }

    public CetaSanctuary(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{U}");

        // At the beginning of your upkeep, if you control a red or green permanent, draw a card, then discard a card. If you control a red permanent and a green permanent, instead draw two cards, then discard a card.
        TriggeredAbility ability = new BeginningOfUpkeepTriggeredAbility(Zone.BATTLEFIELD, new CetaSanctuaryEffect(), TargetController.YOU, true);
        this.addAbility(new ConditionalTriggeredAbility(ability, new PermanentsOnTheBattlefieldCondition(filter),
                "At the beginning of your upkeep, if you control a red or green permanent, draw a card, then discard a card. If you control a red permanent and a green permanent, instead draw two cards, then discard a card."));

    }

    public CetaSanctuary(final CetaSanctuary card) {
        super(card);
    }

    @Override
    public CetaSanctuary copy() {
        return new CetaSanctuary(this);
    }
}

class CetaSanctuaryEffect extends OneShotEffect {

    public CetaSanctuaryEffect() {
        super(Outcome.DrawCard);
    }

    public CetaSanctuaryEffect(final CetaSanctuaryEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            int red = 0;
            int green = 0;
            for (Permanent permanent : game.getBattlefield().getAllActivePermanents(controller.getId())) {
                ObjectColor color = permanent.getColor(game);
                if (color.isRed()) {
                    red = 1;
                }
                if (color.isGreen()) {
                    green = 1;
                }

                if (red == 1 && green == 1) {
                    break;
                }
            }

            if (red != 0 || green != 0) {
                controller.drawCards((red + green), game);
                controller.discard(1, false, source, game);
                return true;
            }
        }
        return false;

    }

    @Override
    public CetaSanctuaryEffect copy() {
        return new CetaSanctuaryEffect(this);
    }
}
