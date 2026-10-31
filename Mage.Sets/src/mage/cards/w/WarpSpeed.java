package mage.cards.w;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.CostAdjuster;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.StrangeNewWorldsValue;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.EnterUntappedAllEffect;
import mage.abilities.effects.common.InfoEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.abilities.hint.common.StrangeNewWorldsHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.target.common.TargetCardInLibrary;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author muz
 */
public final class WarpSpeed extends CardImpl {

    private static final FilterCard filter = new FilterCard("basic land card or Planet card");

    static {
        filter.add(Predicates.or(
            Predicates.and(
                CardType.LAND.getPredicate(),
                SuperType.BASIC.getPredicate()
            ),
            SubType.PLANET.getPredicate()
        ));
    }

    public WarpSpeed(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{G}");

        // Lands you control enter untapped.
        this.addAbility(new SimpleStaticAbility(new EnterUntappedAllEffect(StaticFilters.FILTER_CONTROLLED_PERMANENT_LANDS)));

        // When this enchantment enters, search your library for a basic land card or Planet card, reveal it, put it into your hand, then shuffle.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new SearchLibraryPutInHandEffect(new TargetCardInLibrary(filter), true)));

        // Strange new worlds -- {7}{U}{U}, Sacrifice this enchantment: Draw two cards. This ability costs {X} less to activate, where X is the number of differently named lands you control.
        Ability ability = new SimpleActivatedAbility(new DrawCardSourceControllerEffect(2), new ManaCostsImpl<>("{7}{U}{U}"));
        ability.addCost(new SacrificeSourceCost());
        ability.addEffect(new InfoEffect("This ability costs {X} less to activate, where X is the number of differently named lands you control"));
        ability.setCostAdjuster(WarpSpeedAdjuster.instance);
        ability.setAbilityWord(AbilityWord.STRANGE_NEW_WORLDS);
        this.addAbility(ability.addHint(StrangeNewWorldsHint.instance));
    }

    private WarpSpeed(final WarpSpeed card) {
        super(card);
    }

    @Override
    public WarpSpeed copy() {
        return new WarpSpeed(this);
    }
}

enum WarpSpeedAdjuster implements CostAdjuster {
    instance;

    @Override
    public void reduceCost(Ability ability, Game game) {
        CardUtil.reduceCost(ability, StrangeNewWorldsValue.instance.calculate(game, ability, null));
    }
}
