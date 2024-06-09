package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.dynamicvalue.common.SavedDamageValue;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.BackupAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ConclaveSledgeCaptain extends CardImpl {

    public ConclaveSledgeCaptain(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{G}");

        this.subtype.add(SubType.ELEPHANT);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Backup 1
        BackupAbility backupAbility1 = new BackupAbility(this, 1);
        this.addAbility(backupAbility1);

        // Backup 1
        BackupAbility backupAbility2 = new BackupAbility(this, 1);
        this.addAbility(backupAbility2);

        // Backup 1
        BackupAbility backupAbility3 = new BackupAbility(this, 1);
        this.addAbility(backupAbility3);

        // Trample
        backupAbility1.addAbility(TrampleAbility.getInstance());
        backupAbility2.addAbility(TrampleAbility.getInstance(), true);
        backupAbility3.addAbility(TrampleAbility.getInstance(), true);

        // Whenever this creature deals combat damage to a player, put that many +1/+1 counters on it.
        Ability ability = new DealsCombatDamageToAPlayerTriggeredAbility(
                new AddCountersSourceEffect(
                        CounterType.P1P1.createInstance(),
                        SavedDamageValue.MANY, false
                ).setText("put that many +1/+1 counters on it"), false
        ).setTriggerPhrase("Whenever this creature deals combat damage to a player, ");
        backupAbility1.addAbility(ability);
        backupAbility2.addAbility(ability, true);
        backupAbility3.addAbility(ability, true);
    }

    private ConclaveSledgeCaptain(final ConclaveSledgeCaptain card) {
        super(card);
    }

    @Override
    public ConclaveSledgeCaptain copy() {
        return new ConclaveSledgeCaptain(this);
    }
}
