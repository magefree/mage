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
package mage.sets.invasion;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.repository.CardRepository;
import mage.choices.Choice;
import mage.choices.ChoiceImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPlayer;

/**
 *
 * @author fireshoes
 */
public class TsabosDecree extends CardImpl {

    public TsabosDecree(UUID ownerId) {
        super(ownerId, 129, "Tsabo's Decree", Rarity.RARE, new CardType[]{CardType.INSTANT}, "{5}{B}");
        this.expansionSetCode = "INV";

        // Choose a creature type. Target player reveals his or her hand and discards all creature cards of that type. Then destroy all creatures of that type that player controls. They can't be regenerated.
        this.getSpellAbility().addEffect(new TsabosDecreeEffect());
        this.getSpellAbility().addTarget(new TargetPlayer());
    }

    public TsabosDecree(final TsabosDecree card) {
        super(card);
    }

    @Override
    public TsabosDecree copy() {
        return new TsabosDecree(this);
    }
}

class TsabosDecreeEffect extends OneShotEffect {

    public TsabosDecreeEffect() {
        super(Outcome.UnboostCreature);
        staticText = "Choose a creature type. Target player reveals his or her hand and discards all creature cards of that type. Then destroy all creatures of that type that player controls. They can't be regenerated";
    }

    public TsabosDecreeEffect(final TsabosDecreeEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Player targetPlayer = game.getPlayer(targetPointer.getFirst(game, source));
        MageObject sourceObject = game.getObject(source.getSourceId());
        if (player != null) {
            Choice typeChoice = new ChoiceImpl(true);
            typeChoice.setMessage("Choose a creature type:");
            typeChoice.setChoices(CardRepository.instance.getCreatureTypes());
            while (!player.choose(outcome, typeChoice, game)) {
                if (!player.canRespond()) {
                    return false;
                }
            }
            if (typeChoice.getChoice() != null) {
                game.informPlayers(sourceObject.getLogName() + " chosen type: " + typeChoice.getChoice());
            }
            targetPlayer.revealCards("hand of " + targetPlayer.getName(), targetPlayer.getHand(), game);
            FilterCard filterCard = new FilterCard();
            filterCard.add(new SubtypePredicate(typeChoice.getChoice()));
            List<Card> toDiscard = new ArrayList<>();
            for (Card card : targetPlayer.getHand().getCards(game)) {
                if(filterCard.match(card, game)) {
                        toDiscard.add(card);
                }
            }
            for(Card card: toDiscard) {
                    targetPlayer.discard(card, source, game);
                }
            FilterCreaturePermanent filterCreaturePermanent = new FilterCreaturePermanent();
            filterCreaturePermanent.add(new SubtypePredicate(typeChoice.getChoice()));
            for (Permanent creature : game.getBattlefield().getActivePermanents(filterCreaturePermanent, source.getSourceId(), game)) {
                if (creature.getControllerId().equals(targetPlayer.getId())) {
                        creature.destroy(source.getSourceId(), game, true);
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public TsabosDecreeEffect copy() {
        return new TsabosDecreeEffect(this);
    }
}
