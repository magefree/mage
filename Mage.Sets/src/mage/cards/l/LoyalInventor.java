package mage.cards.l;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.abilities.effects.common.search.SearchLibraryPutOnLibraryEffect;
import mage.abilities.hint.ConditionHint;
import mage.abilities.hint.Hint;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledPermanent;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LoyalInventor extends CardImpl {

    private static final Condition condition
            = new PermanentsOnTheBattlefieldCondition(new FilterControlledPermanent(SubType.ASSASSIN));
    private static final Hint hint = new ConditionHint(condition, "You control an Assassin");

    public LoyalInventor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ARTIFICER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // When Loyal Inventor enters the battlefield, you may search your library for an artifact card, reveal it, then shuffle. Put that card into your hand if you control an Assassin. Otherwise, put that card on top of your library.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new ConditionalOneShotEffect(
                new SearchLibraryPutInHandEffect(new TargetCardInLibrary(StaticFilters.FILTER_CARD_ARTIFACT), true),
                new SearchLibraryPutOnLibraryEffect(new TargetCardInLibrary(StaticFilters.FILTER_CARD_ARTIFACT), true),
                condition, "search your library for an artifact card, reveal it, then shuffle. " +
                "Put that card into your hand if you control an Assassin. Otherwise, put that card on top of your library"
        ), true).addHint(hint));
    }

    private LoyalInventor(final LoyalInventor card) {
        super(card);
    }

    @Override
    public LoyalInventor copy() {
        return new LoyalInventor(this);
    }
}
