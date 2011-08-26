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
import mage.Constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SpellCastTriggeredAbility;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.cards.CardImpl;
import mage.counters.CounterType;
import mage.filter.Filter;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.sets.alarareborn.FiligreeAngel;
import mage.target.Target;
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author Loki
 */
public class QuestForTheHolyRelic extends CardImpl<QuestForTheHolyRelic> {


    public QuestForTheHolyRelic(UUID ownerId) {
        super(ownerId, 33, "Quest for the Holy Relic", Rarity.UNCOMMON, new CardType[]{CardType.ENCHANTMENT}, "{W}");
        this.expansionSetCode = "ZEN";

        this.color.setWhite(true);

        // Whenever you cast a creature spell, you may put a quest counter on Quest for the Holy Relic.
        this.addAbility(new SpellCastTriggeredAbility(new AddCountersSourceEffect(CounterType.QUEST.createInstance()), new FilterCreatureCard(), true));
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

class QuestForTheHolyRelicEffect extends SearchLibraryPutInPlayEffect {
    private static final FilterCard filter = new FilterCard("Equipment");

    static {
        filter.getSubtype().add("Equipment");
        filter.setScopeSubtype(Filter.ComparisonScope.Any);
    }

    QuestForTheHolyRelicEffect() {
        super(new TargetCardInLibrary(filter));
        staticText = "Search your library for an Equipment card, put it onto the battlefield, and attach it to a creature you control. Then shuffle your library";
    }

    QuestForTheHolyRelicEffect(final QuestForTheHolyRelicEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null && super.apply(game, source)) {
            Target target = new TargetControlledCreaturePermanent();
            if (player.choose(Constants.Outcome.Benefit, target, game)) {
                if (target.getTargets().size() > 0) {
                    Permanent permanent = game.getPermanent(target.getTargets().get(0));
                    Permanent sourceEquipment = null;
                    if (super.getTargets().size() > 0) {
                        sourceEquipment = game.getPermanent(super.getTargets().get(0));
                    }
                    if (permanent != null && sourceEquipment != null) {
                        return permanent.addAttachment(sourceEquipment.getId(), game);
                    }
                }
            }
        }
        return false;
    }

    @Override
    public QuestForTheHolyRelicEffect copy() {
        return new QuestForTheHolyRelicEffect(this);
    }
}
