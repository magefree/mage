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
package mage.sets.commander;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.Outcome;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInHand;

/**
 *
 * @author Backfir3
 */
public class KaaliaOfTheVast extends CardImpl<KaaliaOfTheVast> {

    public KaaliaOfTheVast(UUID ownerId) {
        super(ownerId, 206, "Kaalia of the Vast", Rarity.MYTHIC, new CardType[]{CardType.CREATURE}, "{1}{W}{B}{R}");
        this.expansionSetCode = "CMD";
        this.supertype.add("Legendary");
        this.subtype.add("Human");
        this.subtype.add("Cleric");
        this.color.setWhite(true);
        this.color.setBlack(true);
        this.color.setRed(true);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever Kaalia of the Vast attacks an opponent, you may put an Angel, Demon, or Dragon creature card
        // from your hand onto the battlefield tapped and attacking that opponent.
        this.addAbility(new KaaliaOfTheVastAttacksAbility());
    }

    public KaaliaOfTheVast(final KaaliaOfTheVast card) {
        super(card);
    }

    @Override
    public KaaliaOfTheVast copy() {
        return new KaaliaOfTheVast(this);
    }

}

class KaaliaOfTheVastAttacksAbility extends TriggeredAbilityImpl<KaaliaOfTheVastAttacksAbility> {

    public KaaliaOfTheVastAttacksAbility() {
        super(Zone.BATTLEFIELD, new KaaliaOfTheVastEffect(), false);
    }

    public KaaliaOfTheVastAttacksAbility(final KaaliaOfTheVastAttacksAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == EventType.ATTACKER_DECLARED && event.getSourceId().equals(this.getSourceId())) {
            Player opponent = game.getPlayer(event.getTargetId());
            if (opponent != null) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever {this} attacks an opponent, you may put an Angel, Demon, or Dragon creature card from your hand onto the battlefield tapped and attacking that opponent.";
    }

    @Override
    public KaaliaOfTheVastAttacksAbility copy() {
        return new KaaliaOfTheVastAttacksAbility(this);
    }
}

class KaaliaOfTheVastEffect extends OneShotEffect<KaaliaOfTheVastEffect> {

    private static final FilterCreatureCard filter = new FilterCreatureCard("an Angel, Demon, or Dragon creature card");

    static {
        filter.add(Predicates.or(
                new SubtypePredicate("Angel"),
                new SubtypePredicate("Demon"),
                new SubtypePredicate("Dragon")));
    }

    public KaaliaOfTheVastEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "put an Angel, Demon, or Dragon creature card from your hand onto the battlefield tapped and attacking that opponent.";
    }

    public KaaliaOfTheVastEffect(final KaaliaOfTheVastEffect effect) {
        super(effect);
    }

    @Override
    public KaaliaOfTheVastEffect copy() {
        return new KaaliaOfTheVastEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null || !player.chooseUse(Outcome.PutCreatureInPlay, "Put an Angel, Demon, or Dragon creature card from your hand onto the battlefield tapped and attacking?", game)) {
            return false;
        }
        TargetCardInHand target = new TargetCardInHand(filter);
        if (target.canChoose(player.getId(), game) && target.choose(getOutcome(), player.getId(), source.getSourceId(), game)) {
            if (target.getTargets().size() > 0) {
                UUID cardId = target.getFirstTarget();
                Card card = game.getCard(cardId);
                if (card != null && game.getCombat() != null) {
                    UUID defenderId = game.getCombat().getDefendingPlayerId(source.getSourceId(), game);
                    if (defenderId != null) {
                        player.getHand().remove(card);
                        player.putOntoBattlefieldWithInfo(card, game, Zone.HAND, source.getSourceId());
                        Permanent creature = game.getPermanent(cardId);
                        if (creature != null) {
                            game.getCombat().declareAttacker(card.getId(), defenderId, game);
                            return true;
                        }

                    }
                }
            }
        }
        return false;
    }
}
