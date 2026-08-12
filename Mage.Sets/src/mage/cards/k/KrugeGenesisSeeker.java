package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.AttackingPredicate;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

/**
 *
 * @author muz
 */
public final class KrugeGenesisSeeker extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent(SubType.KLINGON, "attacking Klingons you control");

    static {
        filter.add(AttackingPredicate.instance);
        filter.add(TargetController.YOU.getOwnerPredicate());
    }

    public KrugeGenesisSeeker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.KLINGON);
        this.subtype.add(SubType.OFFICER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever Kruge attacks, attacking Klingons you control get +1/+1 until end of turn.
        this.addAbility(new AttacksTriggeredAbility(new BoostAllEffect(1, 1, Duration.EndOfTurn, filter, false), false));
    }

    private KrugeGenesisSeeker(final KrugeGenesisSeeker card) {
        super(card);
    }

    @Override
    public KrugeGenesisSeeker copy() {
        return new KrugeGenesisSeeker(this);
    }
}
