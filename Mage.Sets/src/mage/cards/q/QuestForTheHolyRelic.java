
package mage.cards.q;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
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
public final class QuestForTheHolyRelic extends CardImpl {

    public QuestForTheHolyRelic(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{W}");

        // Whenever you cast a creature spell, you may put a quest counter on Quest for the Holy Relic.
        this.addAbility(new SpellCastControllerTriggeredAbility(new AddCountersSourceEffect(CounterType.QUEST.createInstance()), StaticFilters.FILTER_SPELL_A_CREATURE, true));
        // Remove five quest counters from Quest for the Holy Relic and sacrifice it: Search your library for an Equipment card, put it onto the battlefield, and attach it to a creature you control. Then shuffle your library.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new QuestForTheHolyRelicEffect(), new RemoveCountersSourceCost(CounterType.QUEST.createInstance(5)));
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

class QuestForTheHolyRelicEffect extends OneShotEffect {

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
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }

        FilterCard filter = new FilterCard("Equipment");
        filter.add(new SubtypePredicate(SubType.EQUIPMENT));
        TargetCardInLibrary target = new TargetCardInLibrary(filter);
        if (controller.searchLibrary(target, source, game)) {
            Card card = controller.getLibrary().getCard(target.getFirstTarget(), game);
            if (card != null && controller.moveCards(card, Zone.BATTLEFIELD, source, game)) {
                Permanent equipment = game.getPermanent(card.getId());
                Target targetCreature = new TargetControlledCreaturePermanent();
                if (equipment != null && controller.choose(Outcome.BoostCreature, targetCreature, source.getSourceId(), game)) {
                    Permanent permanent = game.getPermanent(targetCreature.getFirstTarget());
                    permanent.addAttachment(equipment.getId(), game);
                }
            }
        }
        controller.shuffleLibrary(source, game);
        return true;
    }
}
