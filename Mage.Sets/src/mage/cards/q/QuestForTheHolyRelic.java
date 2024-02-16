package mage.cards.q;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.costs.CompositeCost;
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
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author Loki, North
 */
public final class QuestForTheHolyRelic extends CardImpl {

    public QuestForTheHolyRelic(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{W}");

        // Whenever you cast a creature spell, you may put a quest counter on Quest for the Holy Relic.
        this.addAbility(new SpellCastControllerTriggeredAbility(
                new AddCountersSourceEffect(CounterType.QUEST.createInstance()),
                StaticFilters.FILTER_SPELL_A_CREATURE, true
        ));

        // Remove five quest counters from Quest for the Holy Relic and sacrifice it: Search your library for an Equipment card, put it onto the battlefield, and attach it to a creature you control. Then shuffle your library.
        this.addAbility(new SimpleActivatedAbility(
                new QuestForTheHolyRelicEffect(),
                new CompositeCost(
                        new RemoveCountersSourceCost(CounterType.QUEST.createInstance(5)),
                        new SacrificeSourceCost(),
                        "Remove five quest counters from {this} and sacrifice it"
                ))
        );
    }

    private QuestForTheHolyRelic(final QuestForTheHolyRelic card) {
        super(card);
    }

    @Override
    public QuestForTheHolyRelic copy() {
        return new QuestForTheHolyRelic(this);
    }
}

class QuestForTheHolyRelicEffect extends OneShotEffect {

    QuestForTheHolyRelicEffect() {
        super(Outcome.PutCardInPlay);
        this.staticText = "Search your library for an Equipment card, put it onto the battlefield, attach it to a creature you control, then shuffle";
    }

    private QuestForTheHolyRelicEffect(final QuestForTheHolyRelicEffect effect) {
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
        filter.add(SubType.EQUIPMENT.getPredicate());
        TargetCardInLibrary target = new TargetCardInLibrary(filter);
        if (controller.searchLibrary(target, source, game)) {
            Card card = controller.getLibrary().getCard(target.getFirstTarget(), game);
            if (card != null && controller.moveCards(card, Zone.BATTLEFIELD, source, game)) {
                Permanent equipment = game.getPermanent(card.getId());
                Target targetCreature = new TargetControlledCreaturePermanent();
                if (equipment != null && controller.choose(Outcome.BoostCreature, targetCreature, source, game)) {
                    Permanent permanent = game.getPermanent(targetCreature.getFirstTarget());
                    permanent.addAttachment(equipment.getId(), source, game);
                }
            }
        }
        controller.shuffleLibrary(source, game);
        return true;
    }
}
