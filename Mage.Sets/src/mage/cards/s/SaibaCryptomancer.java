package mage.cards.s;

import mage.MageInt;
import mage.abilities.keyword.BackupAbility;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.keyword.HexproofAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SaibaCryptomancer extends CardImpl {

    public SaibaCryptomancer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");

        this.subtype.add(SubType.MOONFOLK);
        this.subtype.add(SubType.NINJA);
        this.power = new MageInt(0);
        this.toughness = new MageInt(1);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // Backup 1
        BackupAbility backupAbility = new BackupAbility(this, 1);
        this.addAbility(backupAbility);

        // Hexproof
        backupAbility.addAbility(HexproofAbility.getInstance());
    }

    private SaibaCryptomancer(final SaibaCryptomancer card) {
        super(card);
    }

    @Override
    public SaibaCryptomancer copy() {
        return new SaibaCryptomancer(this);
    }
}
