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
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CastSourceTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.other.OwnerPredicate;
import mage.game.Game;
import mage.game.permanent.token.EldraziScionToken;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetCardInExile;

/**
 *
 * @author LevelX2
 */
public class BlightHerder extends CardImpl {

    public BlightHerder(UUID ownerId) {
        super(ownerId, 2, "Blight Herder", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{5}");
        this.expansionSetCode = "BFZ";
        this.subtype.add("Eldrazi");
        this.subtype.add("Processor");
        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // When you cast Blight Herder, you may put two cards your opponents own from exile into their owners' graveyards. If you do, put three 1/1 colorless Eldrazi Scion creature tokens onto the battlefield. They have "Sacrifice this creature: Add {1} to your mana pool."
        this.addAbility(new CastSourceTriggeredAbility(new BlightHerderEffect(), true));
    }

    public BlightHerder(final BlightHerder card) {
        super(card);
    }

    @Override
    public BlightHerder copy() {
        return new BlightHerder(this);
    }
}

class BlightHerderEffect extends OneShotEffect {

    private final static FilterCard filter = new FilterCard("cards your opponents own from exile");

    static {
        filter.add(new OwnerPredicate(TargetController.OPPONENT));
    }

    public BlightHerderEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "you may put two cards your opponents own from exile into their owners' graveyards. If you do, put three 1/1 colorless Eldrazi Scion creature tokens onto the battlefield. They have \"Sacrifice this creature: Add {1} to your mana pool.";
    }

    public BlightHerderEffect(final BlightHerderEffect effect) {
        super(effect);
    }

    @Override
    public BlightHerderEffect copy() {
        return new BlightHerderEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Target target = new TargetCardInExile(2, 2, filter, null);
            if (target.canChoose(source.getSourceId(), source.getControllerId(), game)) {
                if (controller.chooseTarget(outcome, target, source, game)) {
                    Cards cardsToGraveyard = new CardsImpl(target.getTargets());
                    controller.moveCards(cardsToGraveyard, null, Zone.GRAVEYARD, source, game);
                    return new CreateTokenEffect(new EldraziScionToken(), 3).apply(game, source);
                }
            }
            return true;
        }
        return false;
    }
}
