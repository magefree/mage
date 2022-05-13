package mage.cards.u;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.UntapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

import java.util.UUID;

/**
 *
 * @author Plopman
 */
public final class UmbralMantle extends CardImpl {

    public UmbralMantle(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");
        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature has "{3}, {untap}: This creature gets +2/+2 until end of turn."
        Ability ability = new SimpleActivatedAbility(new BoostSourceEffect(2, 2, Duration.EndOfTurn).setText("this creature gets +2/+2 until end of turn"), new GenericManaCost(3));
        ability.addCost(new UntapSourceCost());
        this.addAbility(new SimpleStaticAbility(new GainAbilityAttachedEffect(ability, AttachmentType.EQUIPMENT)));

        // Equip {0}
        this.addAbility(new EquipAbility(0, false));
    }

    private UmbralMantle(final UmbralMantle card) {
        super(card);
    }

    @Override
    public UmbralMantle copy() {
        return new UmbralMantle(this);
    }
}
