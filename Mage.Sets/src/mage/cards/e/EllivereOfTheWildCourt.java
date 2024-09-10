package mage.cards.e;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsDamageToAPlayerAllTriggeredAbility;
import mage.abilities.common.EntersBattlefieldOrAttacksSourceTriggeredAbility;
import mage.abilities.effects.common.CreateRoleAttachedTargetEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.permanent.EnchantedPredicate;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class EllivereOfTheWildCourt extends CardImpl {

    private static final FilterPermanent filter
            = new FilterControlledCreaturePermanent("an enchanted creature you control");

    static {
        filter.add(EnchantedPredicate.instance);
    }

    public EllivereOfTheWildCourt(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Whenever Ellivere of the Wild Court enters the battlefield or attacks, create a Virtuous Role token attached to another target creature you control.
        Ability ability = new EntersBattlefieldOrAttacksSourceTriggeredAbility(new CreateRoleAttachedTargetEffect(RoleType.VIRTUOUS));
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_ANOTHER_TARGET_CREATURE_YOU_CONTROL));
        this.addAbility(ability);

        // Whenever an enchanted creature you control deals combat damage to a player, draw a card.
        this.addAbility(new DealsDamageToAPlayerAllTriggeredAbility(
                new DrawCardSourceControllerEffect(1), filter,
                false, SetTargetPointer.NONE, true
        ));
    }

    private EllivereOfTheWildCourt(final EllivereOfTheWildCourt card) {
        super(card);
    }

    @Override
    public EllivereOfTheWildCourt copy() {
        return new EllivereOfTheWildCourt(this);
    }
}
