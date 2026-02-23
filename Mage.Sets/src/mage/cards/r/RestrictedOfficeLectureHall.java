package mage.cards.r;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.UnlockThisDoorTriggeredAbility;
import mage.abilities.effects.common.DestroyAllEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.HexproofAbility;
import mage.cards.CardSetInfo;
import mage.cards.RoomCard;
import mage.constants.ComparisonType;
import mage.constants.Duration;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.PowerPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RestrictedOfficeLectureHall extends RoomCard {

    private static final FilterPermanent filter = new FilterCreaturePermanent("creatures with power 3 or greater");

    static {
        filter.add(new PowerPredicate(ComparisonType.MORE_THAN, 2));
    }

    public RestrictedOfficeLectureHall(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, "{2}{W}{W}", "{5}{U}{U}");

        // Restricted Office
        // When you unlock this door, destroy all creatures with power 3 or greater.
        this.getLeftHalfCard().addAbility(new UnlockThisDoorTriggeredAbility(new DestroyAllEffect(filter), false, true));

        // Lecture Hall
        // Other permanents you control have hexproof.
        this.getRightHalfCard().addAbility(new SimpleStaticAbility(new GainAbilityControlledEffect(
                HexproofAbility.getInstance(), Duration.WhileOnBattlefield,
                StaticFilters.FILTER_PERMANENTS, true
        )));
    }

    private RestrictedOfficeLectureHall(final RestrictedOfficeLectureHall card) {
        super(card);
    }

    @Override
    public RestrictedOfficeLectureHall copy() {
        return new RestrictedOfficeLectureHall(this);
    }
}
