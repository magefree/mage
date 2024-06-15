package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.PutCardIntoGraveFromAnywhereAllTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.OpponentControlsMoreCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.game.permanent.token.SandWarriorToken;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class SandScout extends CardImpl {

    private static final FilterCard filter = new FilterCard("a Desert card");

    static {
        filter.add(SubType.DESERT.getPredicate());
    }

    private static final Condition condition = new OpponentControlsMoreCondition(StaticFilters.FILTER_LAND);

    public SandScout(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SCOUT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Sand Scout enters the battlefield, if an opponent controls more lands than you, search your library for a Desert card, put it onto the battlefield tapped, then shuffle.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(new EntersBattlefieldTriggeredAbility(
                new SearchLibraryPutInPlayEffect(new TargetCardInLibrary(filter), true)),
                condition, "When {this} enters the battlefield, if an opponent controls more lands than you, " +
                "search your library for a Desert card, put it onto the battlefield tapped, then shuffle."
        ));

        // Whenever one or more land cards are put into your graveyard from anywhere, create a 1/1 red, green, and white Sand Warrior creature token. This ability triggers only once each turn.
        this.addAbility(new PutCardIntoGraveFromAnywhereAllTriggeredAbility(
                new CreateTokenEffect(new SandWarriorToken()), false, StaticFilters.FILTER_CARD_LAND, TargetController.YOU
        ).setTriggerPhrase("Whenever one or more land cards are put into your graveyard from anywhere, ").setTriggersLimitEachTurn(1));
    }

    private SandScout(final SandScout card) {
        super(card);
    }

    @Override
    public SandScout copy() {
        return new SandScout(this);
    }
}
