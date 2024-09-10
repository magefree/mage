
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.abilities.keyword.FlankingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.filter.predicate.permanent.AttackingPredicate;

/**
 *
 * @author TheElk801
 */
public final class TelimTor extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("all attacking creatures with flanking");

    static {
        filter.add(AttackingPredicate.instance);
        filter.add(new AbilityPredicate(FlankingAbility.class));
    }

    public TelimTor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flanking
        this.addAbility(new FlankingAbility());

        // Whenever Telim'Tor attacks, all attacking creatures with flanking get +1/+1 until end of turn.
        this.addAbility(new AttacksTriggeredAbility(new BoostAllEffect(1, 1, Duration.EndOfTurn, filter, false), false));
    }

    private TelimTor(final TelimTor card) {
        super(card);
    }

    @Override
    public TelimTor copy() {
        return new TelimTor(this);
    }
}
