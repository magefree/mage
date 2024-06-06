package mage.cards.q;

import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.common.LandfallAbility;
import mage.abilities.costs.CostAdjuster;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.InfoEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.target.common.TargetCardInGraveyard;
import mage.util.CardUtil;

import java.util.Optional;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class QuestForTheNecropolis extends CardImpl {

    public QuestForTheNecropolis(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{B}");

        // Landfall -- Whenever a land enters the battlefield under your control, put a quest counter on Quest for the Necropolis.
        this.addAbility(new LandfallAbility(new AddCountersSourceEffect(CounterType.QUEST.createInstance())));

        // {5}{B}, Sacrifice Quest for the Necropolis: Put target creature card from a graveyard onto the battlefield under your control. This ability costs {1} less to activate for each quest counter on Quest for the Necropolis. Activate only as a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(
                new ReturnFromGraveyardToBattlefieldTargetEffect(), new ManaCostsImpl<>("{5}{B}")
        );
        ability.addCost(new SacrificeSourceCost());
        ability.addEffect(new InfoEffect("This ability costs {1} less to activate for each quest counter on {this}"));
        ability.addTarget(new TargetCardInGraveyard(StaticFilters.FILTER_CARD_CREATURE_A_GRAVEYARD));
        this.addAbility(ability.setCostAdjuster(QuestForTheNecropolisAdjuster.instance));
    }

    private QuestForTheNecropolis(final QuestForTheNecropolis card) {
        super(card);
    }

    @Override
    public QuestForTheNecropolis copy() {
        return new QuestForTheNecropolis(this);
    }
}

enum QuestForTheNecropolisAdjuster implements CostAdjuster {
    instance;

    @Override
    public void adjustCosts(Ability ability, Game game) {
        int amount = Optional
                .ofNullable(ability.getSourcePermanentIfItStillExists(game))
                .map(permanent -> permanent.getCounters(game).getCount(CounterType.QUEST))
                .orElse(0);
        if (amount > 0) {
            CardUtil.reduceCost(ability, amount);
        }
    }
}
