package mage.cards.e;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.CreateRoleAttachedTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.RoleType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class EmberethVeteran extends CardImpl {

    public EmberethVeteran(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // {1}, Sacrifice Embereth Veteran: Create a Young Hero Role token attached to another target creature.
        Ability ability = new SimpleActivatedAbility(
                new CreateRoleAttachedTargetEffect(RoleType.YOUNG_HERO), new GenericManaCost(1)
        );
        ability.addCost(new SacrificeSourceCost());
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_ANOTHER_TARGET_CREATURE));
        this.addAbility(ability);
    }

    private EmberethVeteran(final EmberethVeteran card) {
        super(card);
    }

    @Override
    public EmberethVeteran copy() {
        return new EmberethVeteran(this);
    }
}
