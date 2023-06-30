package mage.cards.c;

import mage.MageInt;
import mage.abilities.keyword.BackupAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ConsumingAetherborn extends CardImpl {

    public ConsumingAetherborn(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");

        this.subtype.add(SubType.AETHERBORN);
        this.subtype.add(SubType.VAMPIRE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Backup 1
        BackupAbility backupAbility = new BackupAbility(this, 1);
        this.addAbility(backupAbility);

        // Lifelink
        backupAbility.addAbility(LifelinkAbility.getInstance());
    }

    private ConsumingAetherborn(final ConsumingAetherborn card) {
        super(card);
    }

    @Override
    public ConsumingAetherborn copy() {
        return new ConsumingAetherborn(this);
    }
}
