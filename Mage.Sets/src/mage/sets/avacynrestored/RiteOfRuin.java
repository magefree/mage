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
package mage.sets.avacynrestored;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.choices.ChoiceImpl;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author North
 */
public class RiteOfRuin extends CardImpl<RiteOfRuin> {

    public RiteOfRuin(UUID ownerId) {
        super(ownerId, 153, "Rite of Ruin", Rarity.RARE, new CardType[]{CardType.SORCERY}, "{5}{R}{R}");
        this.expansionSetCode = "AVR";

        this.color.setRed(true);

        // Choose an order for artifacts, creatures, and lands. Each player sacrifices one permanent of the first type, sacrifices two of the second type, then sacrifices three of the third type.
        this.getSpellAbility().addEffect(new RiteOfRuinEffect());
    }

    public RiteOfRuin(final RiteOfRuin card) {
        super(card);
    }

    @Override
    public RiteOfRuin copy() {
        return new RiteOfRuin(this);
    }
}

class RiteOfRuinEffect extends OneShotEffect<RiteOfRuinEffect> {

    public RiteOfRuinEffect() {
        super(Outcome.Sacrifice);
        this.staticText = "Choose an order for artifacts, creatures, and lands. Each player sacrifices one permanent of the first type, sacrifices two of the second type, then sacrifices three of the third type";
    }

    public RiteOfRuinEffect(final RiteOfRuinEffect effect) {
        super(effect);
    }

    @Override
    public RiteOfRuinEffect copy() {
        return new RiteOfRuinEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }

        HashSet<String> choices = new HashSet<String>();
        choices.add("Artifacts");
        choices.add("Creatures");
        choices.add("Lands");

        LinkedList<CardType> order = new LinkedList<CardType>();
        ChoiceImpl choice = new ChoiceImpl(true);
        choice.setChoices(choices);
        while (controller.choose(Outcome.Sacrifice, choice, game) && choices.size() > 1) {
            order.add(getCardType(choice.getChoice()));
            choices.remove(choice.getChoice());
            choice.clearChoice();
        }
        order.add(getCardType(choices.iterator().next()));

        LinkedList<UUID> sacrifices = new LinkedList<UUID>();
        int count = 1;
        for (CardType cardType : order) {
            FilterControlledPermanent filter = new FilterControlledPermanent(cardType + " permanent you control");
            filter.getCardType().add(cardType);

            for (UUID playerId : controller.getInRange()) {
                int amount = Math.min(count, game.getBattlefield().countAll(filter, playerId));
                TargetControlledPermanent target = new TargetControlledPermanent(amount, amount, filter, false);
                target.setRequired(true);
                Player player = game.getPlayer(playerId);
                if (player != null && player.choose(Outcome.Sacrifice, target, source.getSourceId(), game)) {
                    sacrifices.addAll(target.getTargets());
                }
            }

            for (UUID targetId : sacrifices) {
                Permanent permanent = game.getPermanent(targetId);
                if (permanent != null) {
                    permanent.sacrifice(source.getSourceId(), game);
                }
            }

            sacrifices.clear();
            count++;
        }

        return true;
    }

    private CardType getCardType(String type) {
        if ("Artifacts".equals(type)) {
            return CardType.ARTIFACT;
        }
        if ("Creatures".equals(type)) {
            return CardType.CREATURE;
        }
        if ("Lands".equals(type)) {
            return CardType.LAND;
        }
        return null;
    }
}
