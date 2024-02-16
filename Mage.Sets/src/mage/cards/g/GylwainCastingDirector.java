package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.EntersBattlefieldThisOrAnotherTriggeredAbility;
import mage.abilities.effects.common.CreateRoleAttachedTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GylwainCastingDirector extends CardImpl {

    public GylwainCastingDirector(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.BARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Whenever Gylwain, Casting Director or another nontoken creature enters the battlefield under your control, choose one --
        // * Create a Royal Role token attached to that creature.
        Ability ability = new EntersBattlefieldThisOrAnotherTriggeredAbility(
                new CreateRoleAttachedTargetEffect(RoleType.ROYAL),
                StaticFilters.FILTER_CREATURE_NON_TOKEN,
                false, SetTargetPointer.PERMANENT, true
        );

        // * Create a Sorcerer Role token attached to that creature.
        ability.addMode(new Mode(new CreateRoleAttachedTargetEffect(RoleType.SORCERER)));

        // * Create a Monster Role token attached to that creature.
        ability.addMode(new Mode(new CreateRoleAttachedTargetEffect(RoleType.MONSTER)));
        this.addAbility(ability);
    }

    private GylwainCastingDirector(final GylwainCastingDirector card) {
        super(card);
    }

    @Override
    public GylwainCastingDirector copy() {
        return new GylwainCastingDirector(this);
    }
}
