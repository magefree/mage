package mage.cards.l;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.CreateRoleAttachedTargetEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.RoleType;
import mage.constants.SubType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LivingLectern extends CardImpl {

    private static final FilterControlledCreaturePermanent filter =
            new FilterControlledCreaturePermanent("other target creature you control");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public LivingLectern(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{1}{U}");

        this.subtype.add(SubType.CONSTRUCT);
        this.power = new MageInt(0);
        this.toughness = new MageInt(4);

        // {1}, Sacrifice Living Lectern: Draw a card. Create a Sorcerer Role token attached to up to one other target creature you control. Activate only as a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(
                new DrawCardSourceControllerEffect(1), new GenericManaCost(1)
        );
        ability.addCost(new SacrificeSourceCost());
        ability.addEffect(new CreateRoleAttachedTargetEffect(RoleType.SORCERER));
        ability.addTarget(new TargetPermanent(
                0, 1, filter
        ));
        this.addAbility(ability);
    }

    private LivingLectern(final LivingLectern card) {
        super(card);
    }

    @Override
    public LivingLectern copy() {
        return new LivingLectern(this);
    }
}
