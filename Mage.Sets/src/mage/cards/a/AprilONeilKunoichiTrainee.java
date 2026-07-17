package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleEvasionAbility;
import mage.abilities.effects.common.combat.CantBeBlockedByCreaturesSourceEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.PowerPredicate;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Duration;

/**
 *
 * @author muz
 */
public final class AprilONeilKunoichiTrainee extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creatures with power 3 or greater");

    static {
        filter.add(new PowerPredicate(ComparisonType.MORE_THAN, 2));
    }

    public AprilONeilKunoichiTrainee(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.NINJA);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When April O'Neil enters, scry 2.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new ScryEffect(2)));

        // April O'Neil can't be blocked by creatures with power 3 or greater.
        this.addAbility(new SimpleEvasionAbility(new CantBeBlockedByCreaturesSourceEffect(filter, Duration.WhileOnBattlefield)));
    }

    private AprilONeilKunoichiTrainee(final AprilONeilKunoichiTrainee card) {
        super(card);
    }

    @Override
    public AprilONeilKunoichiTrainee copy() {
        return new AprilONeilKunoichiTrainee(this);
    }
}
