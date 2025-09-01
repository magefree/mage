package mage.cards.b;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.combat.CantBeBlockedAttachedEffect;
import mage.abilities.effects.common.continuous.AddCardSubtypeAttachedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.WardAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author PurpleCrowbar
 */
public final class BrotherhoodRegalia extends CardImpl {

    private static final FilterControlledCreaturePermanent filter
            = new FilterControlledCreaturePermanent("legendary creature");

    static {
        filter.add(SuperType.LEGENDARY.getPredicate());
    }

    public BrotherhoodRegalia(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");
        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature has ward {2}, is an Assassin in addition to its other types, and can't be blocked.
        Ability ability = new SimpleStaticAbility(new GainAbilityAttachedEffect(
                new WardAbility(new GenericManaCost(2), false), AttachmentType.EQUIPMENT
        ).setText("Equipped creature has ward {2}"));
        ability.addEffect(new AddCardSubtypeAttachedEffect(SubType.ASSASSIN, AttachmentType.EQUIPMENT)
                .setText(", is an Assassin in addition to its other types")
        );
        ability.addEffect(new CantBeBlockedAttachedEffect(AttachmentType.EQUIPMENT)
                .setText(", and can't be blocked")
        );
        this.addAbility(ability);

        // Equip legendary creature {1}
        this.addAbility(new EquipAbility(Outcome.AddAbility, new GenericManaCost(1), new TargetPermanent(filter), false));

        // Equip {3}
        this.addAbility(new EquipAbility(Outcome.AddAbility, new GenericManaCost(3), true));
    }

    private BrotherhoodRegalia(final BrotherhoodRegalia card) {
        super(card);
    }

    @Override
    public BrotherhoodRegalia copy() {
        return new BrotherhoodRegalia(this);
    }
}
