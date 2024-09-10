package mage.cards.s;

import mage.MageInt;
import mage.abilities.keyword.BackupAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SigiledSentinel extends CardImpl {

    public SigiledSentinel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Backup 1
        BackupAbility backupAbility = new BackupAbility(this, 1);
        this.addAbility(backupAbility);

        // Vigilance
        backupAbility.addAbility(VigilanceAbility.getInstance());
    }

    private SigiledSentinel(final SigiledSentinel card) {
        super(card);
    }

    @Override
    public SigiledSentinel copy() {
        return new SigiledSentinel(this);
    }
}
