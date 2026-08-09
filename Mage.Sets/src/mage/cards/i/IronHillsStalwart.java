package mage.cards.i;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.AttachTargetToTargetEffect;
import mage.abilities.keyword.ReachAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class IronHillsStalwart extends CardImpl {

    public IronHillsStalwart(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R}");

        this.subtype.add(SubType.DWARF);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // When this creature enters, attach target Equipment you control to up to one target creature you control.
        Ability ability = new EntersBattlefieldTriggeredAbility(new AttachTargetToTargetEffect());
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_CONTROLLED_PERMANENT_EQUIPMENT));
        ability.addTarget(new TargetControlledCreaturePermanent(0, 1));
        this.addAbility(ability);
    }

    private IronHillsStalwart(final IronHillsStalwart card) {
        super(card);
    }

    @Override
    public IronHillsStalwart copy() {
        return new IronHillsStalwart(this);
    }
}
