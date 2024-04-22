package mage.cards.f;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.TurnedFaceUpSourceTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.search.SearchLibraryPutOneOntoBattlefieldTappedRestInHandEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.keyword.DisguiseAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterCard;
import mage.filter.common.FilterControlledPermanent;
import mage.target.common.TargetCardInLibrary;

/**
 * @author Cguy7777
 */
public final class FlourishingBloomKin extends CardImpl {

    private static final FilterControlledPermanent filterForests = new FilterControlledPermanent(SubType.FOREST);
    private static final FilterCard filterForestCards = new FilterCard("Forest cards");

    static {
        filterForestCards.add(SubType.FOREST.getPredicate());
    }

    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(filterForests);
    private static final Hint hint = new ValueHint("Forests you control", xValue);

    public FlourishingBloomKin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");

        this.subtype.add(SubType.PLANT);
        this.subtype.add(SubType.ELEMENTAL);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Flourishing Bloom-Kin gets +1/+1 for each Forest you control.
        Ability ability = new SimpleStaticAbility(new BoostSourceEffect(xValue, xValue, Duration.WhileOnBattlefield));
        this.addAbility(ability.addHint(hint));

        // Disguise {4}{G}
        this.addAbility(new DisguiseAbility(this, new ManaCostsImpl<>("{4}{G}")));

        // When Flourishing Bloom-Kin is turned face up, search your library for up to two Forest cards and reveal them.
        // Put one of them onto the battlefield tapped and the other into your hand, then shuffle.
        this.addAbility(new TurnedFaceUpSourceTriggeredAbility(
                new SearchLibraryPutOneOntoBattlefieldTappedRestInHandEffect(new TargetCardInLibrary(0, 2, filterForestCards))
                        .setText("search your library for up to two Forest cards and reveal them. Put one of them onto the battlefield tapped and the other into your hand, then shuffle")));
    }

    private FlourishingBloomKin(final FlourishingBloomKin card) {
        super(card);
    }

    @Override
    public FlourishingBloomKin copy() {
        return new FlourishingBloomKin(this);
    }
}
