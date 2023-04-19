package mage.cards.g;

import mage.MageInt;
import mage.abilities.keyword.BackupAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GoldenScaleAeronaut extends CardImpl {

    public GoldenScaleAeronaut(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{W}");

        this.subtype.add(SubType.DWARF);
        this.subtype.add(SubType.PILOT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Backup 1
        BackupAbility backupAbility = new BackupAbility(this, 1);
        this.addAbility(backupAbility);

        // Flying
        backupAbility.addAbility(FlyingAbility.getInstance());
    }

    private GoldenScaleAeronaut(final GoldenScaleAeronaut card) {
        super(card);
    }

    @Override
    public GoldenScaleAeronaut copy() {
        return new GoldenScaleAeronaut(this);
    }
}
