package mage.cards.f;

import mage.MageInt;
import mage.abilities.keyword.BackupAbility;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FearlessSkald extends CardImpl {

    public FearlessSkald(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R}");

        this.subtype.add(SubType.DWARF);
        this.subtype.add(SubType.BERSERKER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Backup 1
        BackupAbility backupAbility = new BackupAbility(this, 1);
        this.addAbility(backupAbility);

        // Double strike
        backupAbility.addAbility(DoubleStrikeAbility.getInstance());
    }

    private FearlessSkald(final FearlessSkald card) {
        super(card);
    }

    @Override
    public FearlessSkald copy() {
        return new FearlessSkald(this);
    }
}
