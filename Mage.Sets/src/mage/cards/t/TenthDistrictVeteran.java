package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.AnotherPredicate;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TenthDistrictVeteran extends CardImpl {

    private static final FilterPermanent filter
            = new FilterCreaturePermanent("another target creature you control");

    static {
        filter.add(new AnotherPredicate());
    }

    public TenthDistrictVeteran(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Whenever Tenth District Veteran attacks, untap another target creature you control.
        Ability ability = new AttacksTriggeredAbility(new UntapTargetEffect(), false);
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private TenthDistrictVeteran(final TenthDistrictVeteran card) {
        super(card);
    }

    @Override
    public TenthDistrictVeteran copy() {
        return new TenthDistrictVeteran(this);
    }
}
