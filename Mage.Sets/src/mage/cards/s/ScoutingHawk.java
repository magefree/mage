package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.OpponentControlsMoreCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ScoutingHawk extends CardImpl {

    private static final FilterCard filter = new FilterCard("a basic Plains card");

    static {
        filter.add(SuperType.BASIC.getPredicate());
        filter.add(SubType.PLAINS.getPredicate());
    }

    private static final Condition condition = new OpponentControlsMoreCondition(StaticFilters.FILTER_LAND);

    public ScoutingHawk(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.subtype.add(SubType.BIRD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Keen Sight — When Scouting Hawk enters the battlefield, if an opponent controls more land than you, search your library for a basic Plains card, put it onto the battlefield tapped, then shuffle.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(new EntersBattlefieldTriggeredAbility(
                new SearchLibraryPutInPlayEffect(new TargetCardInLibrary(filter), true)),
                condition, "When {this} enters the battlefield, if an opponent controls more land than you, " +
                "search your library for a basic Plains card, put it onto the battlefield tapped, then shuffle."
        ).withFlavorWord("Keen Sight"));
    }

    private ScoutingHawk(final ScoutingHawk card) {
        super(card);
    }

    @Override
    public ScoutingHawk copy() {
        return new ScoutingHawk(this);
    }
}
