package mage.cards.r;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.mana.GreenManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author muz
 */
public final class RoilingCanopy extends CardImpl {

    private static final FilterPermanent forestFilter = new FilterPermanent("a Forest");
    private static final FilterPermanent otherForestsFilter = new FilterPermanent("at least five other Forests");

    static {
        forestFilter.add(SubType.FOREST.getPredicate());
        otherForestsFilter.add(SubType.FOREST.getPredicate());
    }

    public RoilingCanopy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // This land enters tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());

        // Whenever a Forest you control enters, if you control at least five other Forests, target creature you control gets +3/+3 until end of turn.
        Ability ability = new EntersBattlefieldControlledTriggeredAbility(
            new BoostTargetEffect(3, 3), forestFilter
        ).withInterveningIf(new PermanentsOnTheBattlefieldCondition(
            otherForestsFilter, ComparisonType.OR_GREATER, 5
        ));
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(ability);

        // {T}: Add {G}.
        this.addAbility(new GreenManaAbility());
    }

    private RoilingCanopy(final RoilingCanopy card) {
        super(card);
    }

    @Override
    public RoilingCanopy copy() {
        return new RoilingCanopy(this);
    }
}
