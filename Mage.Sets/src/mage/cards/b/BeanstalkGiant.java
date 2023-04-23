package mage.cards.b;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessSourceEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.cards.AdventureCard;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BeanstalkGiant extends AdventureCard {

    private static final DynamicValue xValue
            = new PermanentsOnBattlefieldCount(StaticFilters.FILTER_CONTROLLED_PERMANENT_LANDS);

    public BeanstalkGiant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, new CardType[]{CardType.SORCERY}, "{6}{G}", "Fertile Footsteps", "{2}{G}");

        this.subtype.add(SubType.GIANT);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Beanstalk Giant's power and toughness are each equal to the number of lands you control.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new SetBasePowerToughnessSourceEffect(xValue)));

        // Fertile Footsteps
        // Search your library for a basic land card, put it onto the battlefield, then shuffle your library.
        this.getSpellCard().getSpellAbility().addEffect(
                new SearchLibraryPutInPlayEffect(new TargetCardInLibrary(StaticFilters.FILTER_CARD_BASIC_LAND))
        );
    }

    private BeanstalkGiant(final BeanstalkGiant card) {
        super(card);
    }

    @Override
    public BeanstalkGiant copy() {
        return new BeanstalkGiant(this);
    }
}
