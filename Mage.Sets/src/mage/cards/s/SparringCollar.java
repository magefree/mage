
package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 *
 * @author Plopman
 */
public final class SparringCollar extends CardImpl {

    public SparringCollar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{2}");
        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature has first strike.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityAttachedEffect(FirstStrikeAbility.getInstance(), AttachmentType.EQUIPMENT)));
        // {R}{R}: Attach Sparring Collar to target creature you control.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new AttachEffect(Outcome.BoostCreature, "Attach {this} to target creature you control"), new ManaCostsImpl<>("{R}{R}"));
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(ability);
        // Equip {1}
        this.addAbility(new EquipAbility(Outcome.BoostCreature, new GenericManaCost(1)));
    }

    private SparringCollar(final SparringCollar card) {
        super(card);
    }

    @Override
    public SparringCollar copy() {
        return new SparringCollar(this);
    }
}
