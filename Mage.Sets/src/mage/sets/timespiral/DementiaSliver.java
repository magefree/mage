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
package mage.sets.timespiral;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.MyTurnCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.NameACardEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetOpponent;

/**
 *
 * @author fireshoes
 */
public class DementiaSliver extends CardImpl {
    
    private static final FilterPermanent filter = new FilterPermanent("All Slivers");

    static {
        filter.add(new SubtypePredicate("Sliver"));
    }

    public DementiaSliver(UUID ownerId) {
        super(ownerId, 236, "Dementia Sliver", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{3}{U}{B}");
        this.expansionSetCode = "TSP";
        this.subtype.add("Sliver");
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // All Slivers have "{tap}: Name a card. Target opponent reveals a card at random from his or her hand. If it's the named card, that player discards it. Activate this ability only during your turn."
        Ability gainedAbility = new ActivateIfConditionActivatedAbility(Zone.BATTLEFIELD, new NameACardEffect(NameACardEffect.TypeOfName.ALL), new TapSourceCost(), MyTurnCondition.getInstance());
        gainedAbility.addEffect(new DementiaSliverEffect());
        gainedAbility.addTarget(new TargetOpponent());
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD,
                new GainAbilityAllEffect(gainedAbility, Duration.WhileOnBattlefield, filter,
                        "All Slivers have \"{tap}: Name a card. Target opponent reveals a card at random from his or her hand. If it's the named card, that player discards it\"")));
    }

    public DementiaSliver(final DementiaSliver card) {
        super(card);
    }

    @Override
    public DementiaSliver copy() {
        return new DementiaSliver(this);
    }
}

class DementiaSliverEffect extends OneShotEffect {

    public DementiaSliverEffect() {
        super(Outcome.Damage);
        staticText = "Target opponent reveals a card at random from his or her hand. If it's the named card, that player discards it";
    }

    public DementiaSliverEffect(final DementiaSliverEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player opponent = game.getPlayer(targetPointer.getFirst(game, source));
        MageObject sourceObject = game.getObject(source.getSourceId());
        String cardName = (String) game.getState().getValue(source.getSourceId().toString() + NameACardEffect.INFO_KEY);        
        if (opponent != null && sourceObject != null && !cardName.isEmpty()) {
            if (opponent.getHand().size() > 0) {
                Cards revealed = new CardsImpl();
                Card card = opponent.getHand().getRandom(game);
                if (card != null) {
                    revealed.add(card);
                    opponent.revealCards(sourceObject.getName(), revealed, game);
                    if (card.getName().equals(cardName)) {
                        opponent.discard(card, source, game);
                    }
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public DementiaSliverEffect copy() {
        return new DementiaSliverEffect(this);
    }

}