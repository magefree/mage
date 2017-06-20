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
package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.constants.ComparisonType;
import mage.abilities.SpellAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CastSourceTriggeredAbility;
import mage.abilities.effects.common.EntersBattlefieldWithXCountersEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.FilterCard;
import mage.filter.common.FilterPermanentCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.mageobject.ConvertedManaCostPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;

import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public class GenesisHydra extends CardImpl {

    public GenesisHydra(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{X}{G}{G}");
        this.subtype.add("Plant");
        this.subtype.add("Hydra");

        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // When you cast Genesis Hydra, reveal the top X cards of your library. You may put a nonland permanent card with converted mana cost X or less from among them onto the battlefield. Then shuffle the rest into your library.
        this.addAbility(new CastSourceTriggeredAbility(new GenesisHydraPutOntoBattlefieldEffect(), false));

        // Genesis Hydra enters the battlefield with X +1/+1 counters on it.
        this.addAbility(new EntersBattlefieldAbility(new EntersBattlefieldWithXCountersEffect(CounterType.P1P1.createInstance())));
    }

    public GenesisHydra(final GenesisHydra card) {
        super(card);
    }

    @Override
    public GenesisHydra copy() {
        return new GenesisHydra(this);
    }
}

class GenesisHydraPutOntoBattlefieldEffect extends OneShotEffect {

    public GenesisHydraPutOntoBattlefieldEffect() {
        super(Outcome.PutCardInPlay);
        staticText = "reveal the top X cards of your library. You may put a nonland permanent card with converted mana cost X or less from among them onto the battlefield. Then shuffle the rest into your library";
    }

    public GenesisHydraPutOntoBattlefieldEffect(final GenesisHydraPutOntoBattlefieldEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        Cards cards = new CardsImpl();

        Object obj = getValue(CastSourceTriggeredAbility.SOURCE_CAST_SPELL_ABILITY);
        int count = 0;
        if (obj != null && obj instanceof SpellAbility) {
            count = ((SpellAbility) obj).getManaCostsToPay().getX();
            // using other var because of tooltip
            int size = Math.min(controller.getLibrary().size(), count);
            for (int i = 0; i < size; i++) {
                Card card = controller.getLibrary().removeFromTop(game);
                cards.add(card);
            }
        }

        if (!cards.isEmpty()) {
            controller.revealCards("Genesis Hydra", cards, game);
        }

        FilterCard filter = new FilterPermanentCard("a nonland permanent card with converted mana cost " + count + " or less to put onto the battlefield");
        filter.add(Predicates.not(new CardTypePredicate(CardType.LAND)));
        filter.add(new ConvertedManaCostPredicate(ComparisonType.FEWER_THAN, count + 1));
        TargetCard target1 = new TargetCard(Zone.LIBRARY, filter);
        target1.setRequired(false);
        if (cards.count(filter, controller.getId(), source.getSourceId(), game) > 0) {
            if (controller.choose(Outcome.PutCardInPlay, cards, target1, game)) {
                Card card = cards.get(target1.getFirstTarget(), game);
                if (card != null) {
                    cards.remove(card);
                    controller.moveCards(card, Zone.BATTLEFIELD, source, game);
                }
                target1.clearChosen();
            } else {
                game.informPlayers(controller.getLogName() + " didn't choose anything");
            }
        } else {
            game.informPlayers("No nonland permanent card with converted mana cost " + count + " or less to choose.");
        }
        controller.moveCards(cards, Zone.LIBRARY, source, game);
        controller.shuffleLibrary(source, game);
        return true;
    }

    @Override
    public GenesisHydraPutOntoBattlefieldEffect copy() {
        return new GenesisHydraPutOntoBattlefieldEffect(this);
    }

}
