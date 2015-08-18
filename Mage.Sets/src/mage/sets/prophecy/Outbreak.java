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
package mage.sets.prophecy;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.costs.AlternativeCostSourceAbility;
import mage.abilities.costs.common.DiscardTargetCost;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.cards.CardImpl;
import mage.cards.repository.CardRepository;
import mage.choices.Choice;
import mage.choices.ChoiceImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInHand;

/**
 *
 * @author fireshoes
 */
public class Outbreak extends CardImpl {
    
    private static final FilterCard filterLand = new FilterCard("a Swamp card");
    
    static {
        filterLand.add(new SubtypePredicate("Swamp"));
    }

    public Outbreak(UUID ownerId) {
        super(ownerId, 71, "Outbreak", Rarity.UNCOMMON, new CardType[]{CardType.SORCERY}, "{3}{B}");
        this.expansionSetCode = "PCY";

        // You may discard a Swamp card rather than pay Outbreak's mana cost.
        this.addAbility(new AlternativeCostSourceAbility(new DiscardTargetCost(new TargetCardInHand(filterLand))));
        
        // Choose a creature type. All creatures of that type get -1/-1 until end of turn.
        this.getSpellAbility().addEffect(new OutbreakEffect());
    }

    public Outbreak(final Outbreak card) {
        super(card);
    }

    @Override
    public Outbreak copy() {
        return new Outbreak(this);
    }
}

class OutbreakEffect extends OneShotEffect {

    public OutbreakEffect() {
        super(Outcome.UnboostCreature);
        staticText = "Choose a creature type. All creatures of that type get -1/-1 until end of turn";
    }

    public OutbreakEffect(final OutbreakEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
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
                game.informPlayers(player.getLogName() + " has chosen " + typeChoice.getChoice());
            }
            FilterCreaturePermanent filter = new FilterCreaturePermanent("All creatures of the chosen type");
            filter.add(new SubtypePredicate(typeChoice.getChoice()));
            ContinuousEffect effect = new BoostAllEffect(-1, -1, Duration.WhileOnBattlefield, filter, false);
            game.addEffect(effect, source);
        }
        return false;
    }

    @Override
    public OutbreakEffect copy() {
        return new OutbreakEffect(this);
    }
}
