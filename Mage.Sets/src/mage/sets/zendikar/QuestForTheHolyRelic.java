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
package mage.sets.zendikar;

import java.util.UUID;

import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SpellCastTriggeredAbility;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.counters.CounterType;
import mage.filter.FilterCard;
import mage.filter.FilterSpell;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author Loki, North
 */
public class QuestForTheHolyRelic extends CardImpl<QuestForTheHolyRelic> {

    private static final FilterSpell filter = new FilterSpell("a creature spell");
    static {
        filter.add(new CardTypePredicate(CardType.CREATURE));
    }

    public QuestForTheHolyRelic(UUID ownerId) {
        super(ownerId, 33, "Quest for the Holy Relic", Rarity.UNCOMMON, new CardType[]{CardType.ENCHANTMENT}, "{W}");
        this.expansionSetCode = "ZEN";

        this.color.setWhite(true);

        // Whenever you cast a creature spell, you may put a quest counter on Quest for the Holy Relic.
        this.addAbility(new SpellCastTriggeredAbility(new AddCountersSourceEffect(CounterType.QUEST.createInstance()), filter, true));
        // Remove five quest counters from Quest for the Holy Relic and sacrifice it: Search your library for an Equipment card, put it onto the battlefield, and attach it to a creature you control. Then shuffle your library.
        Ability ability = new SimpleActivatedAbility(Constants.Zone.BATTLEFIELD, new QuestForTheHolyRelicEffect(), new RemoveCountersSourceCost(CounterType.QUEST.createInstance(5)));
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
    }

    public QuestForTheHolyRelic(final QuestForTheHolyRelic card) {
        super(card);
    }

    @Override
    public QuestForTheHolyRelic copy() {
        return new QuestForTheHolyRelic(this);
    }
}

class QuestForTheHolyRelicEffect extends OneShotEffect<QuestForTheHolyRelicEffect> {

    public QuestForTheHolyRelicEffect() {
        super(Outcome.PutCardInPlay);
        this.staticText = "Search your library for an Equipment card, put it onto the battlefield, and attach it to a creature you control. Then shuffle your library";
    }

    public QuestForTheHolyRelicEffect(final QuestForTheHolyRelicEffect effect) {
        super(effect);
    }

    @Override
    public QuestForTheHolyRelicEffect copy() {
        return new QuestForTheHolyRelicEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }

        FilterCard filter = new FilterCard("Equipment");
        filter.add(new SubtypePredicate("Equipment"));
        TargetCardInLibrary target = new TargetCardInLibrary(filter);
        if (player.searchLibrary(target, game)) {
            Card card = player.getLibrary().getCard(target.getFirstTarget(), game);
            if (card != null) {
                card.putOntoBattlefield(game, Zone.LIBRARY, source.getId(), source.getControllerId());
                Permanent equipment = game.getPermanent(card.getId());

                Target targetCreature = new TargetControlledCreaturePermanent();
                if (equipment != null && player.choose(Outcome.BoostCreature, targetCreature, source.getSourceId(), game)) {
                    Permanent permanent = game.getPermanent(targetCreature.getFirstTarget());
                    permanent.addAttachment(equipment.getId(), game);
                }
            }
        }
        player.shuffleLibrary(game);
        return true;
    }
}
