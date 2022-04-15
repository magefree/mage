package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.PhaseOutTargetEffect;
import mage.constants.SubType;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author weirddan455
 */
public final class GuardianOfFaith extends CardImpl {

    private static final FilterControlledCreaturePermanent filter
            = new FilterControlledCreaturePermanent("other creatures you control");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public GuardianOfFaith(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}{W}");

        this.subtype.add(SubType.SPIRIT);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // When Guardian of Faith enters the battlefield, any number of other target creatures you control phase out.
        Ability ability = new EntersBattlefieldTriggeredAbility(new PhaseOutTargetEffect(
                "any number of other target creatures you control"
        ));
        ability.addTarget(new TargetControlledCreaturePermanent(0, Integer.MAX_VALUE, filter, false));
        this.addAbility(ability);
    }

    private GuardianOfFaith(final GuardianOfFaith card) {
        super(card);
    }

    @Override
    public GuardianOfFaith copy() {
        return new GuardianOfFaith(this);
    }
}
