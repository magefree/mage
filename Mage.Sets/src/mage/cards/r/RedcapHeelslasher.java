package mage.cards.r;

import mage.MageInt;
import mage.abilities.keyword.BackupAbility;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RedcapHeelslasher extends CardImpl {

    public RedcapHeelslasher(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");

        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Backup 1
        BackupAbility backupAbility = new BackupAbility(this, 1);
        this.addAbility(backupAbility);

        // First strike
        backupAbility.addAbility(FirstStrikeAbility.getInstance());
    }

    private RedcapHeelslasher(final RedcapHeelslasher card) {
        super(card);
    }

    @Override
    public RedcapHeelslasher copy() {
        return new RedcapHeelslasher(this);
    }
}
