package mage.cards.r;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.combat.CantBeBlockedByMoreThanOneAttachedEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.ReachAbility;
import mage.abilities.token.ClueAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;

import java.util.UUID;

/**
 * @author Cguy7777
 */
public final class Rope extends CardImpl {

    public Rope(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{G}");

        this.subtype.add(SubType.CLUE);
        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature gets +1/+2, has reach, and can't be blocked by more than one creature.
        Ability boostAbility = new SimpleStaticAbility(new BoostEquippedEffect(1, 2));
        boostAbility.addEffect(new GainAbilityAttachedEffect(ReachAbility.getInstance(), AttachmentType.EQUIPMENT)
                .setText(", has reach"));
        boostAbility.addEffect(new CantBeBlockedByMoreThanOneAttachedEffect(AttachmentType.EQUIPMENT)
                .setText(", and can't be blocked by more than one creature"));
        this.addAbility(boostAbility);

        // {2}, Sacrifice Rope: Draw a card.
        this.addAbility(new ClueAbility(true));

        // Equip {3}
        this.addAbility(new EquipAbility(Outcome.BoostCreature, new GenericManaCost(3), false));
    }

    private Rope(final Rope card) {
        super(card);
    }

    @Override
    public Rope copy() {
        return new Rope(this);
    }
}
