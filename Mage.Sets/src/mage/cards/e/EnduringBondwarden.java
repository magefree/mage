package mage.cards.e;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.common.PutSourceCountersOnTargetEffect;
import mage.abilities.keyword.BackupAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class EnduringBondwarden extends CardImpl {

    public EnduringBondwarden(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SCOUT);
        this.power = new MageInt(0);
        this.toughness = new MageInt(1);

        // Backup 1
        BackupAbility backupAbility = new BackupAbility(this, 1);
        this.addAbility(backupAbility);

        // When this creature dies, put its counters on target creature you control.
        Ability ability = new DiesSourceTriggeredAbility(new PutSourceCountersOnTargetEffect()).setTriggerPhrase("When this creature dies, ");
        ability.addTarget(new TargetControlledCreaturePermanent());
        backupAbility.addAbility(ability);
    }

    private EnduringBondwarden(final EnduringBondwarden card) {
        super(card);
    }

    @Override
    public EnduringBondwarden copy() {
        return new EnduringBondwarden(this);
    }
}
