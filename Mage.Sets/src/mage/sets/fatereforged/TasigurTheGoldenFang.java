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
package mage.sets.fatereforged;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.PutTopCardOfLibraryIntoGraveControllerEffect;
import mage.abilities.keyword.DelveAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.common.FilterNonlandCard;
import mage.filter.predicate.other.OwnerIdPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetCardInGraveyard;
import mage.target.common.TargetOpponent;

/**
 *
 * @author LevelX2
 */
public class TasigurTheGoldenFang extends CardImpl {

    public TasigurTheGoldenFang(UUID ownerId) {
        super(ownerId, 87, "Tasigur, the Golden Fang", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{5}{B}");
        this.expansionSetCode = "FRF";
        this.supertype.add("Legendary");
        this.subtype.add("Human");
        this.subtype.add("Shaman");
        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // Delve
        this.addAbility(new DelveAbility());
        // {2}{G/U}{G/U}: Put the top two cards of your library into your graveyard, then return a nonland card of an opponent's choice from your graveyard to your hand.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new PutTopCardOfLibraryIntoGraveControllerEffect(2), new ManaCostsImpl("{2}{G/U}{G/U}"));
        ability.addEffect(new TasigurTheGoldenFangEffect());
        this.addAbility(ability);
    }

    public TasigurTheGoldenFang(final TasigurTheGoldenFang card) {
        super(card);
    }

    @Override
    public TasigurTheGoldenFang copy() {
        return new TasigurTheGoldenFang(this);
    }
}

class TasigurTheGoldenFangEffect extends OneShotEffect {

    public TasigurTheGoldenFangEffect() {
        super(Outcome.ReturnToHand);
        this.staticText = ", then return a nonland card of an opponent's choice from your graveyard to your hand";
    }

    public TasigurTheGoldenFangEffect(final TasigurTheGoldenFangEffect effect) {
        super(effect);
    }

    @Override
    public TasigurTheGoldenFangEffect copy() {
        return new TasigurTheGoldenFangEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            UUID opponentId = null;
            if (game.getOpponents(controller.getId()).size() > 1) {
                Target target = new TargetOpponent(true);
                if (controller.chooseTarget(outcome, target, source, game)) {
                    opponentId = target.getFirstTarget();
                }
            } else {
                opponentId = game.getOpponents(controller.getId()).iterator().next();
            }
            if (opponentId != null) {
                Player opponent = game.getPlayer(opponentId);
                if (opponent != null) {
                    FilterNonlandCard filter = new FilterNonlandCard("nonland card from " + controller.getLogName() + " graveyard");
                    filter.add(new OwnerIdPredicate(controller.getId()));
                    Target target = new TargetCardInGraveyard(filter);
                    opponent.chooseTarget(outcome, target, source, game);
                    Card card = game.getCard(target.getFirstTarget());
                    if (card != null) {
                        controller.moveCardToHandWithInfo(card, source.getSourceId(), game, Zone.GRAVEYARD);
                    }
                }
            }
            return true;
        }
        return false;
    }
}
