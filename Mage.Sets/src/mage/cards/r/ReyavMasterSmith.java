package mage.cards.r;

import mage.MageInt;
import mage.abilities.common.AttacksCreatureYouControlTriggeredAbility;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.EnchantedPredicate;
import mage.filter.predicate.permanent.EquippedPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ReyavMasterSmith extends CardImpl {

    private static final FilterControlledCreaturePermanent filter
            = new FilterControlledCreaturePermanent("enchanted or equipped creature you control");

    static {
        filter.add(Predicates.or(
                EnchantedPredicate.instance,
                EquippedPredicate.instance
        ));
    }

    public ReyavMasterSmith(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.DWARF);
        this.subtype.add(SubType.ARTIFICER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever an enchanted or equipped creature you control attacks, that creature gains double strike until end of turn
        this.addAbility(new AttacksCreatureYouControlTriggeredAbility(
                new GainAbilityTargetEffect(
                        DoubleStrikeAbility.getInstance(), Duration.EndOfTurn,
                        "that creature gains double strike until end of turn"
                ), false, filter, true
        ));
    }

    private ReyavMasterSmith(final ReyavMasterSmith card) {
        super(card);
    }

    @Override
    public ReyavMasterSmith copy() {
        return new ReyavMasterSmith(this);
    }
}
