package mage.cards.s;

import mage.MageInt;
import mage.abilities.keyword.BackupAbility;
import mage.abilities.keyword.DeathtouchAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SerpentBladeAssailant extends CardImpl {

    public SerpentBladeAssailant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Backup 1
        BackupAbility backupAbility = new BackupAbility(this, 1);
        this.addAbility(backupAbility);

        // Deathtouch
        backupAbility.addAbility(DeathtouchAbility.getInstance());
    }

    private SerpentBladeAssailant(final SerpentBladeAssailant card) {
        super(card);
    }

    @Override
    public SerpentBladeAssailant copy() {
        return new SerpentBladeAssailant(this);
    }
}
