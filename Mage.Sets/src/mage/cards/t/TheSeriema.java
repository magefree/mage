package mage.cards.t;

import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.IndestructibleAbility;
import mage.abilities.keyword.StationAbility;
import mage.abilities.keyword.StationLevelAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreatureCard;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TheSeriema extends CardImpl {

    private static final FilterCard filter = new FilterCreatureCard("legendary creature card");
    private static final FilterPermanent filter2 = new FilterCreaturePermanent("tapped legendary creatures");

    static {
        filter.add(SuperType.LEGENDARY.getPredicate());
        filter2.add(TappedPredicate.TAPPED);
        filter2.add(SuperType.LEGENDARY.getPredicate());
    }

    public TheSeriema(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}{W}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.SPACECRAFT);

        // When The Seriema enters, search your library for a legendary creature card, reveal it, put it into your hand, then shuffle.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new SearchLibraryPutInHandEffect(new TargetCardInLibrary(filter), true)
        ));

        // Station
        this.addAbility(new StationAbility());

        // STATION 7+
        // Flying
        // Other tapped legendary creatures you control have indestructible.
        // 5/5
        this.addAbility(new StationLevelAbility(7)
                .withLevelAbility(FlyingAbility.getInstance())
                .withLevelAbility(new SimpleStaticAbility(new GainAbilityControlledEffect(
                        IndestructibleAbility.getInstance(), Duration.WhileOnBattlefield, filter2, true
                )))
                .withPT(5, 5));
    }

    private TheSeriema(final TheSeriema card) {
        super(card);
    }

    @Override
    public TheSeriema copy() {
        return new TheSeriema(this);
    }
}
