package mage.cards.d;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.costs.CostAdjuster;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.InfoEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.util.CardUtil;

import java.util.Optional;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DiaryOfDreams extends CardImpl {

    public DiaryOfDreams(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        this.subtype.add(SubType.BOOK);

        // Whenever you cast an instant or sorcery spell, put a page counter on this artifact.
        this.addAbility(new SpellCastControllerTriggeredAbility(
                new AddCountersSourceEffect(CounterType.PAGE.createInstance()),
                StaticFilters.FILTER_SPELL_AN_INSTANT_OR_SORCERY, false
        ));

        // {5}, {T}: Draw a card. This ability costs {1} less to activate for each page counter on this artifact.
        Ability ability = new SimpleActivatedAbility(new DrawCardSourceControllerEffect(1), new GenericManaCost(5));
        ability.addCost(new TapSourceCost());
        ability.addEffect(new InfoEffect("This ability costs {1} less to activate for each page counter on {this}"));
        this.addAbility(ability.setCostAdjuster(DiaryOfDreamsAdjuster.instance));
    }

    private DiaryOfDreams(final DiaryOfDreams card) {
        super(card);
    }

    @Override
    public DiaryOfDreams copy() {
        return new DiaryOfDreams(this);
    }
}

enum DiaryOfDreamsAdjuster implements CostAdjuster {
    instance;

    @Override
    public void reduceCost(Ability ability, Game game) {
        Optional.ofNullable(ability.getSourcePermanentIfItStillExists(game))
                .map(permanent -> permanent.getCounters(game).getCount(CounterType.PAGE))
                .filter(count -> count > 0)
                .ifPresent(count -> CardUtil.reduceCost(ability, count));
    }
}
