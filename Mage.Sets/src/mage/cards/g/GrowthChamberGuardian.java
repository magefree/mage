package mage.cards.g;

import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.abilities.keyword.AdaptAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GrowthChamberGuardian extends CardImpl {

    public GrowthChamberGuardian(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");

        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.CRAB);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {2}{G}: Adapt 2.
        this.addAbility(new AdaptAbility(2, "{2}{G}"));

        // Whenever one or more +1/+1 counters are put on Growth-Chamber Guardian, you may search your library for a card named Growth-Chamber Guardian, reveal it, put it into your hand, then shuffle your library.
        this.addAbility(new GrowthChamberGuardianTriggeredAbility());
    }

    private GrowthChamberGuardian(final GrowthChamberGuardian card) {
        super(card);
    }

    @Override
    public GrowthChamberGuardian copy() {
        return new GrowthChamberGuardian(this);
    }
}

class GrowthChamberGuardianTriggeredAbility extends TriggeredAbilityImpl {

    private static final FilterCard filter
            = new FilterCard("a card named Growth-Chamber Guardian");

    static {
        filter.add(new NamePredicate("Growth-Chamber Guardian"));
    }

    GrowthChamberGuardianTriggeredAbility() {
        super(Zone.BATTLEFIELD, new SearchLibraryPutInHandEffect(new TargetCardInLibrary(filter), true), true);
    }

    private GrowthChamberGuardianTriggeredAbility(final GrowthChamberGuardianTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public GrowthChamberGuardianTriggeredAbility copy() {
        return new GrowthChamberGuardianTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.COUNTERS_ADDED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return event.getData().equals(CounterType.P1P1.getName())
                && event.getAmount() > 0
                && event.getTargetId().equals(this.getSourceId());
    }

    @Override
    public String getRule() {
        return "Whenever one or more +1/+1 counters are put on {this}, " +
                "you may search your library for a card named Growth-Chamber Guardian, " +
                "reveal it, put it into your hand, then shuffle your library.";
    }
}
