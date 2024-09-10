package mage.cards.d;

import mage.MageInt;
import mage.abilities.keyword.BackupAbility;
import mage.abilities.keyword.DashAbility;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DeathGreetersChampion extends CardImpl {

    public DeathGreetersChampion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Dash {3}{R}
        this.addAbility(new DashAbility("{3}{R}"));

        // Backup 1
        BackupAbility backupAbility = new BackupAbility(this, 1);
        this.addAbility(backupAbility);

        // Double strike
        backupAbility.addAbility(DoubleStrikeAbility.getInstance());
    }

    private DeathGreetersChampion(final DeathGreetersChampion card) {
        super(card);
    }

    @Override
    public DeathGreetersChampion copy() {
        return new DeathGreetersChampion(this);
    }
}
