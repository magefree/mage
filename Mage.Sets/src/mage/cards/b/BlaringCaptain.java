
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.abilities.keyword.PartnerWithAbility;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.AttackingPredicate;

/**
 *
 * @author TheElk801
 */
public final class BlaringCaptain extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent(SubType.WARRIOR, "attacking Warriors");

    static {
        filter.add(AttackingPredicate.instance);
    }

    public BlaringCaptain(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");

        this.subtype.add(SubType.AZRA);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Partner with Blaring Recruiter (When this creature enters the battlefield, target player may put Blaring Recruiter into their hand from their library, then shuffle.)
        this.addAbility(new PartnerWithAbility("Blaring Recruiter"));

        // Whenever Blaring Captain attacks, attacking Warriors get +1/+1 until end of turn.
        this.addAbility(new AttacksTriggeredAbility(new BoostAllEffect(1, 1, Duration.EndOfTurn, filter, false), false));
    }

    private BlaringCaptain(final BlaringCaptain card) {
        super(card);
    }

    @Override
    public BlaringCaptain copy() {
        return new BlaringCaptain(this);
    }
}
