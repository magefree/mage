package mage.cards.b;

import mage.MageInt;
import mage.abilities.keyword.BackupAbility;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BoonBringerValkyrie extends CardImpl {

    public BoonBringerValkyrie(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}{W}");

        this.subtype.add(SubType.ANGEL);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Backup 1
        BackupAbility backupAbility = new BackupAbility(this, 1);
        this.addAbility(backupAbility);

        // Flying
        backupAbility.addAbility(FlyingAbility.getInstance());

        // First strike
        backupAbility.addAbility(FirstStrikeAbility.getInstance());

        // Lifelink
        backupAbility.addAbility(LifelinkAbility.getInstance());
    }

    private BoonBringerValkyrie(final BoonBringerValkyrie card) {
        super(card);
    }

    @Override
    public BoonBringerValkyrie copy() {
        return new BoonBringerValkyrie(this);
    }
}
