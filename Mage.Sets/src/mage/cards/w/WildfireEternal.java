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
package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksAndIsNotBlockedTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.AfflictAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.FilterCard;
import mage.filter.common.FilterInstantOrSorceryCard;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInHand;

/**
 *
 * @author spjspj
 */
public class WildfireEternal extends CardImpl {

    public WildfireEternal(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");

        this.subtype.add("Zombie");
        this.subtype.add("Jackal");
        this.subtype.add("Cleric");
        this.power = new MageInt(1);
        this.toughness = new MageInt(4);

        // Afflict 4
        addAbility(new AfflictAbility(4));

        // Whenever Wildfire Eternal attacks and isn't blocked, you may cast an instant or sorcery card from your hand without paying its mana cost.
        this.addAbility(new AttacksAndIsNotBlockedTriggeredAbility(new WildfireEternalCastEffect(), false, true));
    }

    public WildfireEternal(final WildfireEternal card) {
        super(card);
    }

    @Override
    public WildfireEternal copy() {
        return new WildfireEternal(this);
    }
}

class WildfireEternalCastEffect extends OneShotEffect {

    public WildfireEternalCastEffect() {
        super(Outcome.Benefit);
        this.staticText = "you may cast an instant or sorcery card from your hand without paying its mana cost";
    }

    public WildfireEternalCastEffect(final WildfireEternalCastEffect effect) {
        super(effect);
    }

    @Override
    public WildfireEternalCastEffect copy() {
        return new WildfireEternalCastEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent sourceObject = game.getPermanentOrLKIBattlefield(source.getSourceId());
        if (controller != null && sourceObject != null) {
            FilterCard filter = new FilterInstantOrSorceryCard();
            int cardsToCast = controller.getHand().count(filter, source.getControllerId(), source.getSourceId(), game);
            if (cardsToCast > 0 && controller.chooseUse(outcome, "Cast an instant or sorcery card from your hand without paying its mana cost?", source, game)) {
                TargetCardInHand target = new TargetCardInHand(filter);
                controller.chooseTarget(outcome, target, source, game);
                Card card = game.getCard(target.getFirstTarget());
                if (card != null) {
                    controller.cast(card.getSpellAbility(), game, true);
                }
            }
            return true;
        }
        return false;
    }
}
