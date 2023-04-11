package mage.cards.v;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.SourcePermanentPowerCount;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.BackupAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class VoldarenThrillseeker extends CardImpl {

    private static final DynamicValue xValue = new SourcePermanentPowerCount(false);

    public VoldarenThrillseeker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.subtype.add(SubType.VAMPIRE);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Backup 2
        BackupAbility backupAbility = new BackupAbility(this, 2);
        this.addAbility(backupAbility);

        // {1}, Sacrifice this creature: It deals damage equal to its power to any target.
        Ability ability = new SimpleActivatedAbility(
                new DamageTargetEffect(xValue)
                        .setText("it deals damage equal to its power to any target"),
                new GenericManaCost(1)
        );
        ability.addCost(new SacrificeSourceCost().setText("sacrifice this creature"));
        ability.addTarget(new TargetAnyTarget());
        backupAbility.addAbility(ability);
    }

    private VoldarenThrillseeker(final VoldarenThrillseeker card) {
        super(card);
    }

    @Override
    public VoldarenThrillseeker copy() {
        return new VoldarenThrillseeker(this);
    }
}
