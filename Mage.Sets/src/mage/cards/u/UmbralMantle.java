
package mage.cards.u;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.UntapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;

/**
 *
 * @author Plopman
 */
public final class UmbralMantle extends CardImpl {

    public UmbralMantle(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{3}");
        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature has "{3}, {untap}: This creature gets +2/+2 until end of turn."
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new BoostSourceEffect(2, 2, Duration.EndOfTurn), new ManaCostsImpl("{3}"));
        ability.addCost(new UntapSourceCost());
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityAttachedEffect(ability, AttachmentType.EQUIPMENT)));
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
