package mage.cards.f;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.search.SearchLibraryGraveyardPutInHandEffect;
import mage.constants.SubType;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.mageobject.NoAbilityPredicate;

/**
 *
 * @author Jmlundeen
 */
public final class FangDruidSummoner extends CardImpl {
    private static final FilterCreatureCard filter = new FilterCreatureCard("a creature card with no abilities");

    static {
        filter.add(NoAbilityPredicate.instance);
    }
    public FangDruidSummoner(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}");
        
        this.subtype.add(SubType.APE);
        this.subtype.add(SubType.DRUID);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // When this creature enters, you may search your library and/or graveyard for a creature card with no abilities, reveal it, and put it into your hand. If you search your library this way, shuffle.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new SearchLibraryGraveyardPutInHandEffect(filter, false, true)
        ));
    }

    private FangDruidSummoner(final FangDruidSummoner card) {
        super(card);
    }

    @Override
    public FangDruidSummoner copy() {
        return new FangDruidSummoner(this);
    }
}
