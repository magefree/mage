package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.ruleModifying.CombatDamageByToughnessSourceEffect;
import mage.abilities.keyword.BackupAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class StreetwiseNegotiator extends CardImpl {

    public StreetwiseNegotiator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");

        this.subtype.add(SubType.CAT);
        this.subtype.add(SubType.CITIZEN);
        this.power = new MageInt(0);
        this.toughness = new MageInt(2);

        // Backup 1
        BackupAbility backupAbility = new BackupAbility(this, 1);

        // This creature assigns combat damage equal to its toughness rather than its power.
        backupAbility.addAbility(new SimpleStaticAbility(new CombatDamageByToughnessSourceEffect(Duration.WhileOnBattlefield)
                .setText("this creature assigns combat damage equal to its toughness rather than its power")));

        this.addAbility(backupAbility);
    }

    private StreetwiseNegotiator(final StreetwiseNegotiator card) {
        super(card);
    }

    @Override
    public StreetwiseNegotiator copy() {
        return new StreetwiseNegotiator(this);
    }
}
