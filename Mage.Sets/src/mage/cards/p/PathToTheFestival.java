package mage.cards.p;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.DomainValue;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.abilities.hint.common.DomainHint;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TimingRule;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PathToTheFestival extends CardImpl {

    public PathToTheFestival(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{G}");

        // Search your library for a basic land card, put that card onto the battlefield tapped, then shuffle. Then if there are three or more basic land types among lands you control, scry 1.
        this.getSpellAbility().addEffect(new SearchLibraryPutInPlayEffect(
                new TargetCardInLibrary(StaticFilters.FILTER_CARD_BASIC_LAND), true
        ));
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new ScryEffect(1), PathToTheFestivalCondition.instance,
                "Then if there are three or more basic land types among lands you control, scry 1"
        ));
        this.getSpellAbility().addHint(DomainHint.instance);

        // Flashback {4}{G}
        this.addAbility(new FlashbackAbility(new ManaCostsImpl<>("{4}{G}"), TimingRule.SORCERY));
    }

    private PathToTheFestival(final PathToTheFestival card) {
        super(card);
    }

    @Override
    public PathToTheFestival copy() {
        return new PathToTheFestival(this);
    }
}

enum PathToTheFestivalCondition implements Condition {
    instance;
    private static final DynamicValue xValue = new DomainValue();

    @Override
    public boolean apply(Game game, Ability source) {
        return xValue.calculate(game, source, null) >= 3;
    }
}
