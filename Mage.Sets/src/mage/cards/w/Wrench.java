package mage.cards.w;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.abilities.token.ClueAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author Cguy7777
 */
public final class Wrench extends CardImpl {

    public Wrench(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{W}");

        this.subtype.add(SubType.CLUE);
        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature gets +1/+1 and has vigilance and "{3}, {T}: Tap target creature."
        Ability boostAbility = new SimpleStaticAbility(new BoostEquippedEffect(1, 1));
        boostAbility.addEffect(new GainAbilityAttachedEffect(VigilanceAbility.getInstance(), AttachmentType.EQUIPMENT)
                .setText("and has vigilance"));

        SimpleActivatedAbility tapAbility = new SimpleActivatedAbility(new TapTargetEffect(), new GenericManaCost(3));
        tapAbility.addCost(new TapSourceCost());
        tapAbility.addTarget(new TargetCreaturePermanent());
        boostAbility.addEffect(new GainAbilityAttachedEffect(tapAbility, AttachmentType.EQUIPMENT)
                .setText("and \"{3}, {T}: Tap target creature.\""));

        this.addAbility(boostAbility);

        // {2}, Sacrifice Wrench: Draw a card.
        this.addAbility(new ClueAbility(true));

        // Equip {2}
        this.addAbility(new EquipAbility(Outcome.BoostCreature, new GenericManaCost(2), new TargetControlledCreaturePermanent(), false));
    }

    private Wrench(final Wrench card) {
        super(card);
    }

    @Override
    public Wrench copy() {
        return new Wrench(this);
    }
}
