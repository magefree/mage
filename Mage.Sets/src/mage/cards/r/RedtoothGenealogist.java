package mage.cards.r;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
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
 * @author xenohedron
 */
public final class RedtoothGenealogist extends CardImpl {

    public RedtoothGenealogist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");
        
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.ADVISOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // When Redtooth Genealogist enters the battlefield, create a Royal Role token attached to another target creature you control.
        Ability ability = new EntersBattlefieldTriggeredAbility(new CreateRoleAttachedTargetEffect(RoleType.ROYAL));
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_ANOTHER_TARGET_CREATURE_YOU_CONTROL));
        this.addAbility(ability);
    }

    private RedtoothGenealogist(final RedtoothGenealogist card) {
        super(card);
    }

    @Override
    public RedtoothGenealogist copy() {
        return new RedtoothGenealogist(this);
    }
}
