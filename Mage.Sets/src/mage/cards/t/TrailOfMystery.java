
package mage.cards.t;

import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.common.TurnedFaceUpAllTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.card.FaceDownPredicate;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class TrailOfMystery extends CardImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("a face-down creature");

    static {
        filter.add(FaceDownPredicate.instance);
    }

    public TrailOfMystery(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{G}");

        // Whenever a face-down creature enters the battlefield under your control, you may search your library for a basic land card, reveal it, put it into your hand, then shuffle your library.
        Effect effect = new SearchLibraryPutInHandEffect(new TargetCardInLibrary(StaticFilters.FILTER_CARD_BASIC_LAND), true);
        this.addAbility(new EntersBattlefieldControlledTriggeredAbility(Zone.BATTLEFIELD, effect, filter, true));

        // Whenever a permanent you control is turned face up, if it's a creature, it gets +2/+2 until end of turn.
        this.addAbility(new TrailOfMysteryTriggeredAbility());
    }

    private TrailOfMystery(final TrailOfMystery card) {
        super(card);
    }

    @Override
    public TrailOfMystery copy() {
        return new TrailOfMystery(this);
    }
}

class TrailOfMysteryTriggeredAbility extends TurnedFaceUpAllTriggeredAbility {

    public TrailOfMysteryTriggeredAbility() {
        super(new BoostTargetEffect(2, 2, Duration.EndOfTurn), new FilterControlledCreaturePermanent(), true);
    }

    public TrailOfMysteryTriggeredAbility(final TrailOfMysteryTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public TrailOfMysteryTriggeredAbility copy() {
        return new TrailOfMysteryTriggeredAbility(this);
    }

    @Override
    public String getRule() {
        return "Whenever a permanent you control is turned face up, if it's a creature, it gets +2/+2 until end of turn.";
    }
}
